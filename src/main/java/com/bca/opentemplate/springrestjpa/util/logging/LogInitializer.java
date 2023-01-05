package com.bca.opentemplate.springrestjpa.util.logging;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.bca.opentemplate.springrestjpa.util.constant.ApplicationProperties;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.util.FileSize;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@DependsOn("applicationProperties")
public class LogInitializer {
	@Autowired
	ApplicationProperties applicationProperties;
	private static Logger transLog;
	private static Logger verboseLog;

	@PostConstruct
	public void initLog() {
		log.info("Initiating logger...");
		log.info("Log Location: {}", defineLogLocation(applicationProperties.getActivatedProfile()));
		transLog = transLogging();
		verboseLog = verboseLogging();

		transLog.info("Logger is ready...");
	}

	public static Logger getTransLog() {
		return transLog;
	}

	public static Logger getVerboseLog() {
		return verboseLog;
	}

	public Logger transLogging() {
		return initiateTransLogger(applicationProperties.getFileLogName(),
				defineLogLocation(applicationProperties.getActivatedProfile()));
	}

	public Logger verboseLogging() {
		return initiateVerboseLogger(applicationProperties.getFileLogName(),
				defineLogLocation(applicationProperties.getActivatedProfile()));
	}

	public Logger initiateTransLogger(String logName, String logLocation) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();

		AsyncAppender asyncAppender = new AsyncAppender();

		rollingFileAppender.setFile(logLocation + "/" + logName + "-trans.log");
		rollingFileAppender.setContext(lc);
		rollingFileAppender.setName("ROLLINGFILE");

		if (1 == Integer.parseInt(applicationProperties.getLogTransRollingPolicy())) {
			SizeAndTimeBasedRollingPolicy sizeAndTimeBasedRollingPolicy = new SizeAndTimeBasedRollingPolicy();
			sizeAndTimeBasedRollingPolicy.setContext(lc);
			sizeAndTimeBasedRollingPolicy.setParent(rollingFileAppender);
			sizeAndTimeBasedRollingPolicy.setFileNamePattern(logLocation + "/archived/" + logName
					+ "-trans.%d{yyyy-MM-dd}.%i" + ".log");
			sizeAndTimeBasedRollingPolicy
					.setMaxHistory(Integer.parseInt(applicationProperties.getLogTransMaxHistory()));
			sizeAndTimeBasedRollingPolicy
					.setMaxFileSize(FileSize.valueOf(applicationProperties.getLogTransMaxFileSize()));
			sizeAndTimeBasedRollingPolicy.setTotalSizeCap(FileSize.valueOf(applicationProperties.getLogTotalSizeCap()));
			sizeAndTimeBasedRollingPolicy.start();

			rollingFileAppender.setRollingPolicy(sizeAndTimeBasedRollingPolicy);
		} else if (2 == Integer.parseInt(applicationProperties.getLogTransRollingPolicy())) {
			FixedWindowRollingPolicy fixWindowRollingPolicy = new FixedWindowRollingPolicy();
			fixWindowRollingPolicy.setContext(lc);
			fixWindowRollingPolicy.setParent(rollingFileAppender);
			fixWindowRollingPolicy.setFileNamePattern(
					logLocation + "/archived/" + logName + "-trans.%i" + ".log");
			fixWindowRollingPolicy.setMinIndex(1);
			fixWindowRollingPolicy.setMaxIndex(Integer.parseInt(applicationProperties.getLogTransMaxBackupIndex()));
			fixWindowRollingPolicy.start();

			SizeBasedTriggeringPolicy<ILoggingEvent> sizeTriggerPolicy = new SizeBasedTriggeringPolicy<ILoggingEvent>();
			sizeTriggerPolicy.setMaxFileSize(FileSize.valueOf(applicationProperties.getLogTransMaxFileSize()));
			sizeTriggerPolicy.start();

			rollingFileAppender.setRollingPolicy(fixWindowRollingPolicy);
			rollingFileAppender.setTriggeringPolicy(sizeTriggerPolicy);
		}

		PatternLayoutEncoder ple = new PatternLayoutEncoder();
		ple.setContext(lc);
		ple.setPattern(applicationProperties.getFileLogPatternLayout());
		ple.start();

		rollingFileAppender.setEncoder(ple);
		// In prudent mode, FileAppender will safely write to the specified file, even
		// in the presence of other FileAppender instances running in different JVMs,
		// potentially running on different hosts. The default value for prudent mode is
		// false.
		// rollingFileAppender.setPrudent(true);
		// rollingFileAppender.setAppend(true);
		rollingFileAppender.start();

		asyncAppender.setContext(lc);
		asyncAppender.addAppender(rollingFileAppender);
		asyncAppender.setDiscardingThreshold(0);
		asyncAppender.setNeverBlock(false);
		asyncAppender.setQueueSize(1000000);
		asyncAppender.setName("ASYNC");
		asyncAppender.start();

		Logger logger = lc.getLogger(logName + "-trans");
		logger.setAdditive(false);
		logger.addAppender(asyncAppender);
		logger.setLevel(Level.toLevel(applicationProperties.getLogThreshold()));

		// detach default CONSOLE appender karena logback.xml tidak ditemukan saat
		// inisiasi app
		Logger root = lc.getLogger(Logger.ROOT_LOGGER_NAME);
		root.detachAppender("CONSOLE");

		if ("Y".equals(applicationProperties.getLogTransStdoutEnable())) {
			logger.setAdditive(false);
			ConsoleAppender ca = new ConsoleAppender();
			ca.setContext(lc);
			ca.setName("CONSOLE");

			PatternLayoutEncoder pleConsole = new PatternLayoutEncoder();
			pleConsole.setContext(lc);
			pleConsole.setPattern("[TRANS] " + applicationProperties.getConsoleLogPatternLayout());
			pleConsole.start();

			ca.setEncoder(pleConsole);
			ca.start();

			logger.addAppender(ca);
		}
		return logger;
	}

	public Logger initiateVerboseLogger(String logName, String logLocation) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();

		AsyncAppender asyncAppender = new AsyncAppender();

		rollingFileAppender.setFile(logLocation + "/" + logName + "-verbose.log");
		rollingFileAppender.setContext(lc);
		rollingFileAppender.setName("ROLLINGFILE");

		if (1 == Integer.parseInt(applicationProperties.getLogTransRollingPolicy())) {
			SizeAndTimeBasedRollingPolicy fixWindowRollingPolicy = new SizeAndTimeBasedRollingPolicy();
			fixWindowRollingPolicy.setContext(lc);
			fixWindowRollingPolicy.setParent(rollingFileAppender);
			fixWindowRollingPolicy.setFileNamePattern(logLocation + "/archived/" + logName
					+ "-verbose.%d{yyyy-MM-dd}.%i" + ".log");
			fixWindowRollingPolicy.setMaxHistory(Integer.parseInt(applicationProperties.getLogTransMaxHistory()));
			fixWindowRollingPolicy.setMaxFileSize(FileSize.valueOf(applicationProperties.getLogTransMaxFileSize()));
			fixWindowRollingPolicy.setTotalSizeCap(FileSize.valueOf(applicationProperties.getLogTotalSizeCap()));
			fixWindowRollingPolicy.start();

			rollingFileAppender.setRollingPolicy(fixWindowRollingPolicy);
		} else if (2 == Integer.parseInt(applicationProperties.getLogTransRollingPolicy())) {
			FixedWindowRollingPolicy fixWindowRollingPolicy = new FixedWindowRollingPolicy();
			fixWindowRollingPolicy.setContext(lc);
			fixWindowRollingPolicy.setParent(rollingFileAppender);
			fixWindowRollingPolicy.setFileNamePattern(
					logLocation + "/archived/" + logName + "-verbose.%i" + ".log");
			fixWindowRollingPolicy.setMinIndex(1);
			fixWindowRollingPolicy.setMaxIndex(Integer.parseInt(applicationProperties.getLogVerboseMaxBackupIndex()));
			fixWindowRollingPolicy.start();

			SizeBasedTriggeringPolicy<ILoggingEvent> sizeTriggerPolicy = new SizeBasedTriggeringPolicy<ILoggingEvent>();
			sizeTriggerPolicy.setMaxFileSize(FileSize.valueOf(applicationProperties.getLogVerboseMaxFileSize()));
			sizeTriggerPolicy.start();

			rollingFileAppender.setRollingPolicy(fixWindowRollingPolicy);
			rollingFileAppender.setTriggeringPolicy(sizeTriggerPolicy);
		}

		PatternLayoutEncoder ple = new PatternLayoutEncoder();
		ple.setContext(lc);
		ple.setPattern(applicationProperties.getFileLogPatternLayout());
		ple.start();

		rollingFileAppender.setEncoder(ple);
		// In prudent mode, FileAppender will safely write to the specified file, even
		// in the presence of other FileAppender instances running in different JVMs,
		// potentially running on different hosts. The default value for prudent mode is
		// false.
		// rollingFileAppender.setPrudent(true);
		// rollingFileAppender.setAppend(true);
		rollingFileAppender.start();

		asyncAppender.setContext(lc);
		asyncAppender.addAppender(rollingFileAppender);
		asyncAppender.setDiscardingThreshold(0);
		asyncAppender.setNeverBlock(false);
		asyncAppender.setQueueSize(1000000);
		asyncAppender.setName("ASYNC");
		asyncAppender.start();

		Logger logger = lc.getLogger(logName + "-verbose");
		logger.setAdditive(false);
		logger.addAppender(asyncAppender);
		logger.setLevel(Level.toLevel(applicationProperties.getLogThreshold()));

		// detach default CONSOLE appender karena logback.xml tidak ditemukan saat
		// inisiasi app
		Logger root = lc.getLogger(Logger.ROOT_LOGGER_NAME);
		root.detachAppender("CONSOLE");

		if ("Y".equals(applicationProperties.getLogVerboseStdoutEnable())) {
			logger.setAdditive(false);
			ConsoleAppender ca = new ConsoleAppender();
			ca.setContext(lc);
			ca.setName("CONSOLE");

			PatternLayoutEncoder pleConsole = new PatternLayoutEncoder();
			pleConsole.setContext(lc);
			pleConsole.setPattern("[VERBOSE] " + applicationProperties.getConsoleLogPatternLayout());
			pleConsole.start();

			ca.setEncoder(pleConsole);
			ca.start();

			logger.addAppender(ca);
		}
		return logger;
	}

	private String defineLogLocation(String activeProfile) {
		String logLocation = applicationProperties.getLogLocation();
		if (!"prod".equalsIgnoreCase(activeProfile)) {
			logLocation += "/" + activeProfile;
		}
		return logLocation;
	}
}
