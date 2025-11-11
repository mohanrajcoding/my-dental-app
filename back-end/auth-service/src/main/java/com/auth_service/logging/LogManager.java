package com.auth_service.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogManager {

    // Dedicated named loggers (configured in logback)
    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("APP-DEBUG");
    //private static final Logger ERROR_LOGGER = LoggerFactory.getLogger("APP-ERROR");

    public void logDebug(String message) {
        if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug(message);
        }
    }

    // optional varargs helper
    public void logDebug(String format, Object... args) {
        if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug(format, args);
        }
    }

}
