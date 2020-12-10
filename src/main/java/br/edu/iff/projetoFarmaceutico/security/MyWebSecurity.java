package br.edu.iff.projetoFarmaceutico.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class MyWebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private RepresentanteDetailsService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/apirest/**")
                .and()
                .authorizeRequests()
                .antMatchers("/apirest/**").hasRole("ADMIN")
                .antMatchers("/representantes/meusdados/**").hasAnyRole("ADMIN", "REPR")
                .antMatchers("/representantes").hasRole("ADMIN")
                .antMatchers("/representantes/**").hasRole("ADMIN")
                .antMatchers("/**").hasAnyRole("ADMIN", "REPR")                
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .usernameParameter("email");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
    }

}