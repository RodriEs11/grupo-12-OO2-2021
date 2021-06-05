package com.unla.grupo12.configuration;

import com.unla.grupo12.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("usuarioService")
	public UsuarioServiceImpl usuarioService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		
		
		auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/css/*", "/imgs/*", "/js/*", "/vendor/bootstrap/css/*", "/vendor/jquery/*",
						"/vendor/bootstrap/js/*", "/registrarse", "/rodado","/rodado/buscar","/rodado/agregar","/permiso/buscar").permitAll().anyRequest().authenticated()
				.and().formLogin().loginPage("/login").loginProcessingUrl("/loginprocess").usernameParameter("username").passwordParameter("password")
				.defaultSuccessUrl("/loginsuccess").permitAll().and().logout().logoutUrl("/logout")
				.logoutSuccessUrl("/logout").permitAll();

	}

}
