package com.example.democrud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.example.democrud.service.impl.UsuarioServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig<T> extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioServiceImpl userDetailsService;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Bean
	public BCryptPasswordEncoder passwordCryp(){
		BCryptPasswordEncoder bCrypPassword = new BCryptPasswordEncoder();
		return bCrypPassword;
	}
	
	protected void configuracion(AuthenticationManagerBuilder auth) 
		throws  Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(bcrypt);
		}
	String[] resources = new String[]{
            "/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**"
    };
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		 http
         .authorizeRequests()
	        .antMatchers(resources).permitAll()  
	        .antMatchers("/","/index","/registrarse","/registraruser","/cerrarSesion").permitAll()
             .anyRequest().authenticated()
             .and()
         .formLogin()
             .loginPage("/login")
             .permitAll()
             .defaultSuccessUrl("/contactos")
             .failureUrl("/login?error=true")
             .usernameParameter("usuario")
             .passwordParameter("password")
             .and()
         .logout()
             .permitAll()
             .logoutSuccessUrl("/login?logout");
    }
	
	
}
