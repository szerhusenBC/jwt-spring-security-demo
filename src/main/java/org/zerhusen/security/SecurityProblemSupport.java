package org.zerhusen.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A compound {@link AuthenticationEntryPoint} and {@link AccessDeniedHandler} that delegates exceptions to
 * Spring WebMVC's {@link HandlerExceptionResolver} as defined in {@link WebMvcConfigurationSupport}.
 *
 * Compatible with spring-webmvc 4.3.3.
 *
 * Copied from org.zalando.problem.spring.web.advice.security.SecurityProblemSupport
 *
 * @see WebMvcConfigurationSupport#handlerExceptionResolver()
 */
@Component
public class SecurityProblemSupport implements AuthenticationEntryPoint, AccessDeniedHandler {

   private final HandlerExceptionResolver resolver;

   @Autowired
   public SecurityProblemSupport(
      @Qualifier("handlerExceptionResolver") final HandlerExceptionResolver resolver) {
      this.resolver = resolver;
   }

   @Override
   public void commence(final HttpServletRequest request, final HttpServletResponse response,
                        final AuthenticationException exception) throws IOException, ServletException {
      resolver.resolveException(request, response, null, exception);
   }

   @Override
   public void handle(final HttpServletRequest request, final HttpServletResponse response,
                      final AccessDeniedException exception) throws IOException, ServletException {
      resolver.resolveException(request, response, null, exception);
   }

}
