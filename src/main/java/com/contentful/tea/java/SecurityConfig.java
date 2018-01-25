package com.contentful.tea.java;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.String.format;
import static java.lang.String.join;

@Configuration
@EnableConfigurationProperties
@SuppressWarnings("unused")
public class SecurityConfig {
  static class HttpsEnforcer implements Filter {

    private FilterConfig filterConfig;

    public static final String X_FORWARDED_PROTO = "x-forwarded-proto";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      HttpServletRequest request = (HttpServletRequest) servletRequest;
      HttpServletResponse response = (HttpServletResponse) servletResponse;

      final String forwardedHeader = request.getHeader(X_FORWARDED_PROTO);

      if ("localhost".equals(request.getServerName())) {
        // ignore filters
      } else if (forwardedHeader != null && forwardedHeader.indexOf("https") != 0) {
        redirectToHttps(request, response);
      } else if (!request.isSecure()) {
        redirectToHttps(request, response);
      }

      if (!response.isCommitted()) {
        filterChain.doFilter(request, response);
      }
    }

    private void redirectToHttps(HttpServletRequest request, HttpServletResponse response) throws IOException {
      final String parameter = requestParameterToString(request);
      final String redirectUrl = format("https://%s/%s", request.getServerName(), parameter == null ? "" : "?" + parameter);

      response.sendRedirect(redirectUrl);
    }

    private String requestParameterToString(HttpServletRequest request) {
      return join("&",
          request
              .getParameterMap()
              .entrySet()
              .stream()
              .map(
                  e -> e.getValue() != null && e.getValue().length > 0
                      ? e.getKey() + "=" + e.getValue()[0]
                      : null
              )
              .filter(Objects::nonNull)
              .collect(Collectors.toList()));
    }

    @Override
    public void destroy() {
      // nothing
    }
  }

  @Bean
  public Filter httpsEnforcerFilter() {
    return new HttpsEnforcer();
  }
}
