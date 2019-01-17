package com.coffeegetaway.config;


import com.coffeegetaway.config.filter.JwtTokenAuthenticationFilter;
import com.coffeegetaway.config.filter.WebSecurityCorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.session.SessionManagementFilter;

import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors().and()
                // handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                // Add a filter to validate the tokens with every request
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new WebSecurityCorsFilter(), SessionManagementFilter.class)
                // authorization requests config
                .authorizeRequests()
                // allow all who are accessing "auth" service
                .antMatchers(jwtConfig.getUri()).permitAll()
                .antMatchers("/oauth/authorize").permitAll()
                .antMatchers("/oauth/check_token").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                //public methods
                .antMatchers(HttpMethod.GET, "/houses/**").permitAll()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.GET, "/recipes/**").permitAll()
                // must be an admin if trying to access admin area (authentication is also required here)
                // Any other request must be authenticated
                .anyRequest().authenticated()
                .and().logout()
                    .permitAll()
                    .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)));
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }

}