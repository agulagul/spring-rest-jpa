package com.bca.opentemplate.springrestjpa.configuration.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import ua_parser.Client;
import ua_parser.Parser;

@Configuration
public class RequestInterceptor extends OncePerRequestFilter {
    private static final String X_CORRELATION_HEADER_NAME = "X-Correlation-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String origin = null == request.getHeader("channel-name") ? request.getHeader(HttpHeaders.ORIGIN)
                : request.getHeader("channel-name");
        String sessionId = request.getSession().getId();
        String correlation = getCorrelationIdFromHeader(request);
        String userId = null;
        String userAgent = request.getHeader("User-Agent");

        MDC.put("origin", null == origin ? "no-origin" : origin);
        MDC.put("sessionid", null == sessionId ? "no-session-id" : sessionId);
        MDC.put("correlation", null == correlation ? "no-correlation-id" : correlation);
        MDC.put("userid", null == userId ? "no-user" : userId);
        MDC.put("useragent", null == userAgent ? "no-ua" : parseUserAgent(userAgent));

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }

    }

    private String parseUserAgent(String userAgent) throws IOException {
        Parser uaParser;
        String simplifiedUA = "no-ua";
        if (!"".equalsIgnoreCase(userAgent)) {
            uaParser = new Parser();
            Client c = uaParser.parse(userAgent);
            simplifiedUA = new StringBuilder().append(c.userAgent.family).append(" ")
                    .append(c.userAgent.major == null ? 0 : c.userAgent.major).append(".")
                    .append(c.userAgent.minor == null ? 0 : c.userAgent.minor).append(" on ").append(c.os.family)
                    .append(" ").append(c.os.major == null ? 0 : c.os.major).append(".")
                    .append(c.os.minor == null ? 0 : c.os.minor).append(" (").append(c.device.family).append(")")
                    .toString();
        }
        return simplifiedUA;
    }

    private String getCorrelationIdFromHeader(HttpServletRequest request) {
        String correlationId = request.getHeader(X_CORRELATION_HEADER_NAME);
        if (StringUtils.isBlank(correlationId)) {
            correlationId = generateUniqueCorrelationId();
        }
        return correlationId;
    }

    private String generateUniqueCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
