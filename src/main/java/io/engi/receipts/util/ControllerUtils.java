package io.engi.receipts.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ControllerUtils {
    private static final Logger reqLogger = LoggerFactory.getLogger("controllers");

    public static void logRequest(HttpServletRequest req, String body) {
        reqLogger.info(req.getMethod() + " " + req.getRequestURI());
        reqLogger.info("Authorization: " + req.getHeader("Authorization"));
        if (!body.isEmpty()) reqLogger.info(body);
    }

    public static void logRequest(HttpServletRequest req) {
        logRequest(req, "");
    }
}
