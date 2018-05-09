package io.engi.receipts.filter;

import io.engi.receipts.util.LogUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

public class LoggingFilter extends AbstractRequestLoggingFilter {
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {}

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        String body = getMessagePayload(request);
        LogUtils.logRequestWithBody(request, body);
    }
}
