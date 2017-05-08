package org.zerhusen.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerhusen.properties.jwt.JwtProperties;
import org.zerhusen.security.JwtTokenUtil;
import org.zerhusen.security.JwtUser;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserRestController {

    private JwtTokenUtil jwtTokenUtil;
    private UserDetailsService userDetailsService;
    private JwtProperties jwtProperties;

    @Autowired
    public UserRestController(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, JwtProperties jwtProperties) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.jwtProperties = jwtProperties;
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(jwtProperties.getHeader());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

}
