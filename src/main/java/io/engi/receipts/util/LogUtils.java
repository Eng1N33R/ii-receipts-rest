package io.engi.receipts.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class LogUtils {
    private static final Logger reqLogger = LoggerFactory.getLogger("controllers");

    public static void logRequestWithBody(HttpServletRequest req, String body) {
        logRequest(req);
        reqLogger.info("Request body: " + body);
    }

    @SuppressWarnings("WeakerAccess")
    public static void logRequest(HttpServletRequest req) {
        reqLogger.info(req.getMethod() + " " + req.getRequestURI());
        reqLogger.info("Authorization: " + req.getHeader("Authorization"));
    }
}
