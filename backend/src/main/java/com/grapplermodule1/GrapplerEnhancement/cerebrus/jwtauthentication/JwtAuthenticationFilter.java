//package com.grapplermodule1.GrapplerEnhancement.cerebrus.jwtauthentication;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtHelper jwtHelper;
//
//    @Autowired
//    @Qualifier("userService")
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
////        try {
////            Thread.sleep(500);
////        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
////        }
//        //Authorization
//
//        String requestHeader = request.getHeader("Authorization");
//        //Bearer 2352345235sdfrsfgsdfsdf
////        logger.info(" Header :  {}", requestHeader);
//        String username = null;
//        String token = null;
//        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
//            //looking good
//            token = requestHeader.substring(7);
//            try {
//
//                username = this.jwtHelper.getUsernameFromToken(token);
//
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            } catch (ExpiredJwtException e) {
//                e.printStackTrace();
//            } catch (MalformedJwtException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//
//
//        } else {
//
//        }
//
//
//        //
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//
//            //fetch user detail from username
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
//            if (validateToken) {
//
//                //set the authentication
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//
//            } else {
//
//            }
//
//
//        }
//
//        filterChain.doFilter(request, response);
//
//
//    }
//}

package com.grapplermodule1.GrapplerEnhancement.cerebrus.jwtauthentication;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class); // Use the same logger pattern

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    @Qualifier("userService")
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String debugUuid = UUID.randomUUID().toString();
        log.info("UUID {} - Processing authentication for '{}'", debugUuid, request.getRequestURL());

        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                log.error("UUID {} - IllegalArgumentException: {}", debugUuid, e.getMessage());
            } catch (ExpiredJwtException e) {
                log.error("UUID {} - Token has expired: {}", debugUuid, e.getMessage());
            } catch (MalformedJwtException e) {
                log.error("UUID {} - Malformed token: {}", debugUuid, e.getMessage());
            } catch (Exception e) {
                log.error("UUID {} - Exception: {}", debugUuid, e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

            if (validateToken) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("UUID {} - Authentication successful for '{}'", debugUuid, username);
            } else {
                log.warn("UUID {} - Token validation failed for '{}'", debugUuid, username);
            }
        }

        filterChain.doFilter(request, response);
    }
}

