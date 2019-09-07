package org.zerhusen.config;

import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zerhusen.security.SecurityProblemSupport;
import org.zerhusen.security.jwt.JWTConfigurer;
import org.zerhusen.security.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final TokenProvider tokenProvider;

   private final CorsFilter corsFilter;
   private final SecurityProblemSupport problemSupport;

   public WebSecurityConfig(
      TokenProvider tokenProvider,
      CorsFilter corsFilter,
      SecurityProblemSupport problemSupport
   ) {
      this.tokenProvider = tokenProvider;
      this.corsFilter = corsFilter;
      this.problemSupport = problemSupport;
   }

   @Override
   public void configure(WebSecurity web) {
      web.ignoring()
         .antMatchers(HttpMethod.OPTIONS, "/**")

         // allow anonymous resource requests
         .antMatchers(
            HttpMethod.GET,
            "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/h2-console/**"
         );
   }

   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
         // we don't need CSRF because our token is invulnerable
         .csrf().disable()

         .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

         .exceptionHandling()
         .authenticationEntryPoint(problemSupport)
         .accessDeniedHandler(problemSupport)

         // enable h2-console
         .and()
         .headers()
         .frameOptions()
         .sameOrigin()

         // create no session
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

         .and()
         .authorizeRequests()
         .antMatchers("/api/authenticate").permitAll()
         // .antMatchers("/api/register").permitAll()
         // .antMatchers("/api/activate").permitAll()
         // .antMatchers("/api/account/reset-password/init").permitAll()
         // .antMatchers("/api/account/reset-password/finish").permitAll()

         .and()
         .httpBasic()
         .and()
         .apply(securityConfigurerAdapter());
   }

   private JWTConfigurer securityConfigurerAdapter() {
      return new JWTConfigurer(tokenProvider);
   }
}
