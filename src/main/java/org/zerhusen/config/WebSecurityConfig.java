package org.zerhusen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zerhusen.security.JwtAccessDeniedHandler;
import org.zerhusen.security.JwtAuthenticationEntryPoint;
import org.zerhusen.security.jwt.JWTConfigurer;
import org.zerhusen.security.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final TokenProvider tokenProvider;
   private final CorsFilter corsFilter;
   private final JwtAuthenticationEntryPoint authenticationErrorHandler;
   private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

   public WebSecurityConfig(
      TokenProvider tokenProvider,
      CorsFilter corsFilter,
      JwtAuthenticationEntryPoint authenticationErrorHandler,
      JwtAccessDeniedHandler jwtAccessDeniedHandler
   ) {
      this.tokenProvider = tokenProvider;
      this.corsFilter = corsFilter;
      this.authenticationErrorHandler = authenticationErrorHandler;
      this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
   }

   // Configure BCrypt password encoder =====================================================================

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   // Configure paths and requests that should be ignored by Spring Security ================================

   @Override
   public void configure(WebSecurity web) {
      web.ignoring()
         .antMatchers(HttpMethod.OPTIONS, "/**")

         // allow anonymous resource requests
         .antMatchers(
            "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/h2-console/**"
         );
   }

   // Configure security settings ===========================================================================

   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
         // we don't need CSRF because our token is invulnerable
         .csrf().disable()

         .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

         .exceptionHandling()
         .authenticationEntryPoint(authenticationErrorHandler)
         .accessDeniedHandler(jwtAccessDeniedHandler)

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

         .antMatchers("/api/person").hasAuthority("ROLE_USER")
         .antMatchers("/api/hiddenmessage").hasAuthority("ROLE_ADMIN")

         .anyRequest().authenticated()

         .and()
         .apply(securityConfigurerAdapter());
   }

   private JWTConfigurer securityConfigurerAdapter() {
      return new JWTConfigurer(tokenProvider);
   }
}
