package com.grapplermodule1.GrapplerEnhancement.cerebrus.config;

import com.grapplermodule1.GrapplerEnhancement.cerebrus.jwtauthentication.JwtAuthenticationEntryPoint;
import com.grapplermodule1.GrapplerEnhancement.cerebrus.jwtauthentication.JwtAuthenticationFilter;
import com.grapplermodule1.GrapplerEnhancement.dtos.TeamMembersDTO;
import com.grapplermodule1.GrapplerEnhancement.entities.TeamMembers;
import com.grapplermodule1.GrapplerEnhancement.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableMethodSecurity
public class AuthenticationConfig {

    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf((c) -> c.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/auth/login","forgot-password/send-Password").permitAll()
//                              .requestMatchers("/**").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());

        httpSecurity
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new UserService();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


        modelMapper.addMappings(new PropertyMap<TeamMembers, TeamMembersDTO>() {
            @Override
            protected void configure() {
                map().setName(source.getUser().getName());
                map().setEmail(source.getUser().getEmail());
                map().setDesignation(source.getUser().getDesignation());
            }
        });

        return modelMapper;
    }
}
