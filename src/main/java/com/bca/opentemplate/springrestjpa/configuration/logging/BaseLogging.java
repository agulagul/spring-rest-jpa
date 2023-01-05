package com.bca.opentemplate.springrestjpa.configuration.logging;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bca.opentemplate.springrestjpa.util.logging.LogInitializer;

import ch.qos.logback.classic.Logger;
import lombok.Getter;

@Component
@Getter
public class BaseLogging {

    @Autowired
    private LogInitializer logInitializer;
    public static Logger transLog;
    public static Logger verboseLog;

    @PostConstruct
    public void init() {
        transLog = logInitializer.getTransLog();
        verboseLog = logInitializer.getVerboseLog();
    }

}