package com.lsk.smsbackend2.security;

import com.lsk.smsbackend2.response.Response;
import com.lsk.smsbackend2.response.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.PrimitiveIterator;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/api/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    PrintWriter writer = response.getWriter();
                    writer.write(Response.ok("Login success").build());
                })
                .failureHandler((request, response, exception) -> {
                    PrintWriter writer = response.getWriter();
                    writer.write(Response.error(exception).build());
                })
                .permitAll()
                .and()
                .logout()
                .addLogoutHandler((request, response, authentication) -> {
                    try {
                        PrintWriter writer = response.getWriter();
                        writer.write(Response.ok("Logout success").build());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                })
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    PrintWriter writer = response.getWriter();
                    writer.write(Response.error(new StatusCode(403, "Forbidden")).build());
                })
                .authenticationEntryPoint((request, response, authException) -> {
                    PrintWriter writer = response.getWriter();
                    writer.write(Response.error(new StatusCode(401, "Login needed")).build());
                });
        http.authorizeRequests()
                .antMatchers("/api/admin/*").hasRole("ADMIN")
                .antMatchers("/api/student/*").hasRole("STUDENT")
                .antMatchers("/api/finance/*").hasRole("FINANCE")
                .anyRequest().permitAll();
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
    }


}
