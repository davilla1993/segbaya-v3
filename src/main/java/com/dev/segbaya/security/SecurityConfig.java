package com.dev.segbaya.security;

import com.dev.segbaya.filter.CustomAuthenticationFilter;
import com.dev.segbaya.filter.CustomAuthorizationFilter;
import com.dev.segbaya.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**",
                        "/api/token/refresh/**",
                        "/api/user/register/**",
                        "/api/user/all/**",
                        "/logout/**",
                        "/api/book/all/**",
                        "/api/publish-house/all/**",
                        "/api/publish-house/register/**",
                        "/api-docs/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger.json",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html")
                .permitAll();
        http.authorizeRequests().antMatchers("/api/user/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers("/api/cart/**").hasAnyAuthority("ROLE_USER");
//        http.authorizeRequests().antMatchers("/api/publish-house/**").hasAnyAuthority("ROLE_PUBLISH_HOUSE");
        http.authorizeRequests().antMatchers("/api/**", "/api/role/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
