package com.bca.opentemplate.springrestjpa.util.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Autowired
    StandardEnvironment environment;

    public String getActivatedProfile() {
        return environment.getActiveProfiles()[0];
    }

    // Application Properties
    public String getLocalDev() {
        return environment.getProperty("application.localDev");
    }

    public String getVersion() {
        return environment.getProperty("application.version");
    }
    
    public String getGraphqlUrl() {
        return environment.getProperty("application.graphql-url");
    }

    // Datasource Properties
    public String getJndiUrl() {
        return environment.getProperty("datasource.jndi.url");
    }

    public String getJdbcUrl() {
        return environment.getProperty("datasource.jdbc.url");
    }

    public String getJdbcUsername() {
        return environment.getProperty("datasource.jdbc.username");
    }

    public String getJdbcPassword() {
        return environment.getProperty("datasource.jdbc.password");
    }

    // HTTP Properties
    public String getApiOrigin() {
        return environment.getProperty("http.api.origin");
    }

    public String getApiBaseUrl() {
        return environment.getProperty("http.api.baseUrl");
    }

    public String getApiClientId() {
        return environment.getProperty("http.api.clientId");
    }

    public String getApiClientSecret() {
        return environment.getProperty("http.api.clientSecret");
    }

    public String getApiIdsAccountPath() {
        return environment.getProperty("http.api.idsAccountPath");
    }

    public String getApiUidmGeneralPath() {
        return environment.getProperty("http.api.uidmGeneralPath");
    }

    // Logging Properties
    public String getFileLogName() {
        return environment.getProperty("log.file.name");
    }

    public String getFileLogPatternLayout() {
        return environment.getProperty("log.file.patternLayout");
    }

    public String getConsoleLogPatternLayout() {
        return environment.getProperty("log.console.patternLayout");
    }

    public String getLogLocation() {
        return environment.getProperty("log.file.location");
    }

    public String getLogThreshold() {
        return environment.getProperty("log.threshold");
    }

    public String getLogTotalSizeCap() {
        return environment.getProperty("log.file.totalSizeCap");
    }

    public String getLogTransRollingPolicy() {
        return environment.getProperty("log.trans.rollingPolicy");
    }

    public String getLogTransMaxFileSize() {
        return environment.getProperty("log.trans.maxFileSize");
    }

    public String getLogTransMaxBackupIndex() {
        return environment.getProperty("log.trans.maxBackupIndex");
    }

    public String getLogTransMaxHistory() {
        return environment.getProperty("log.trans.maxHistory");
    }

    public String getLogTransStdoutEnable() {
        return environment.getProperty("log.trans.stdoutEnable");
    }

    public String getLogVerboseRollingPolicy() {
        return environment.getProperty("log.verbose.rollingPolicy");
    }

    public String getLogVerboseMaxFileSize() {
        return environment.getProperty("log.verbose.maxFileSize");
    }

    public String getLogVerboseMaxBackupIndex() {
        return environment.getProperty("log.verbose.maxBackupIndex");
    }

    public String getLogVerboseMaxHistory() {
        return environment.getProperty("log.verbose.maxHistory");
    }

    public String getLogVerboseStdoutEnable() {
        return environment.getProperty("log.verbose.stdoutEnable");
    }

    // HCP
    public String getHcpUrl() {
        return environment.getProperty("hcp.url");
    }

    public String getHcpAuth() {
        return environment.getProperty("hcp.auth");
    }
}
