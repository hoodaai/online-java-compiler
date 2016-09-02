package com.config;

import com.security.AuthoritiesConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

    }


    @Configuration
    @Order(1)
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter{

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/web/**")
                    .formLogin()
                    .loginPage("/web/login").permitAll()
                    .loginProcessingUrl("/web/login")
                    .and()
                    .logout().permitAll()
                    .logoutUrl("/web/logout")
                    .logoutSuccessUrl("/web/login")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/web/login").permitAll()
                    .antMatchers("/web/register").permitAll()
                    .antMatchers("/web", "/web/home").permitAll()
                    .antMatchers(org.springframework.http.HttpMethod.OPTIONS, "/api/**").permitAll()
                    .antMatchers("/web/hello", "/web/hello").hasAuthority(AuthoritiesConstants.USER)
                    .and()
                    .exceptionHandling().accessDeniedPage("/403");
        }
    }

    @Configuration
    @Order(2)
    public static class ApiTokenSecurityConfig extends WebSecurityConfigurerAdapter{

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .httpBasic().realmName("unicorn")
                    .and()
                    .antMatcher("/api/**")
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .requestMatchers().antMatchers("/oauth/authorize")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/oauth/authorize").authenticated();

        }

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/scripts/**/*.{js,html}")
                .antMatchers("/bower_components/**")
                .antMatchers("/i18n/**")
                .antMatchers("/assets/**")
                .antMatchers("/api/register")
                .antMatchers("/api/activate")
                .antMatchers("/api/account/reset_password/init")
                .antMatchers("/api/account/reset_password/finish")
                .antMatchers("/test/**")
                .antMatchers("/h2-console/**");
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}