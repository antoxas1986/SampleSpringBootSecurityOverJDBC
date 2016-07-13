package com.example.controllers;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select u.username, uc.password, uc.enabled from users as u inner join usercred as uc on u.id=uc.userid where u.username=?")
                .authoritiesByUsernameQuery("select u.username, r.rolename from users as u inner join role as r on u.roleid=r.id where u.username=?");
        //		.usersByUsernameQuery("select username,password, enabled from users where username=?")
        //		.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll().and().formLogin().loginPage("/login")
                .usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/after").and().logout()
                .logoutSuccessUrl("/login?logout").and().exceptionHandling().accessDeniedPage("/403");
        http.csrf().disable();
    }
}
