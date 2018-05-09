package io.engi.receipts.config;

import io.engi.receipts.filter.LoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @SuppressWarnings("unused")
    @Bean
    public LoggingFilter requestLoggingFilter() {
        LoggingFilter filter = new LoggingFilter();
        filter.setMaxPayloadLength(3000);
        filter.setIncludeClientInfo(true);
        filter.setIncludeHeaders(true);
        filter.setIncludePayload(true);
        filter.setIncludeQueryString(true);
        return filter;
    }
}
