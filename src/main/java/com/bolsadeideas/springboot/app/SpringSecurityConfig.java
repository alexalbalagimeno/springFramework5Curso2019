package com.bolsadeideas.springboot.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccessHandler;
import com.bolsadeideas.springboot.app.models.service.JpaUserDetailsService;

//para configurar los accesos de usuarios, roles,...
//para permitir anotacion @Secured o @preAuthorize en controller
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	// es para que envie el mensaje de logueado con exito
	@Autowired
	private LoginSuccessHandler successHandler;

	// para la conexion a la BD SQL
	@Autowired
	private DataSource dataSource;

	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// seguridad de las rutas
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// permite todas las rutas para todos los usuarios (de acceso publico)
		// .antMatchers todas las rutas protegidas
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar", "/locale").permitAll()
				// Se comenta esto porque lo queremos controlar con anotaciones (en vez de urls)
				// en los controladores
				/*
				 * .antMatchers("/ver/**").hasAnyRole("USER")
				 * .antMatchers("/uploads/**").hasAnyRole("USER")
				 * .antMatchers("/form/**").hasAnyRole("ADMIN")
				 * .antMatchers("/eliminar/**").hasAnyRole("ADMIN")
				 * .antMatchers("/factura/**").hasAnyRole("ADMIN")
				 */
				.anyRequest().authenticated().and().formLogin().successHandler(successHandler).loginPage("/login")
				.permitAll().and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/error_403");

	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
		
		// otra manera de autenticar con jpa
		
		build.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
		
		
		// autenticar con forma nativa JDBC SQL authenticacion ya tenemos los usuarios en la BD SQL insertados
		// en tabla users y tabla authorities
		// en .usersByUsernameQuery username=? es el del formulario de login lo hace por
		// detras de forma automatica
		// .authoritiesByUsernameQuery para los roles selecciona username y rol
		/*build.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
				.usersByUsernameQuery("select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery(
						"select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");*/

		
		// Comentado porque autenticamos con JDBC, esto era para autenticar en memoria
		// (no hay usuarios en la BD)
		/*
		 * PasswordEncoder encoder = passwordEncoder; UserBuilder users =
		 * User.builder().passwordEncoder(encoder::encode);
		 * 
		 * //usuario admin, andres, alex build.inMemoryAuthentication()
		 * .withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
		 * .withUser(users.username("andres").password("12345").roles("USER"))
		 * .withUser(users.username("alex").password("12345").roles("USER"));
		 */
	}
}
