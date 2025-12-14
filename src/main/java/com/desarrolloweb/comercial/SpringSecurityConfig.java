package com.desarrolloweb.comercial;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//import com.desarrolloweb.comercial.model.service.UsuarioDetailService;

@Configuration
public class SpringSecurityConfig {

    // @Autowired
    // private UsuarioDetailService usuarioDetailService;
/* 
    private final UsuarioDetailService usuarioDetailService;

    public SpringSecurityConfig(UsuarioDetailService usuarioDetailService) {
        this.usuarioDetailService = usuarioDetailService;
    }
*/
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // para utilizar este método(bean) se deben tener: BCryptPasswordEncoder y el UsuarioDetailService
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // @Bean
    // UserDetailsService userDetailsService() throws Exception {
    //     InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    //     manager.createUser(User.withUsername("jefe").password(passwordEncoder().encode("Abc123")).roles("ADMIN", "USER").build());
    //     manager.createUser(User.withUsername("luis").password(passwordEncoder().encode("Abc123")).roles("USER").build());
    //     return manager;
    // }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
            .requestMatchers("/", "/css/**", "/js/**", "/img/**", "/comercial/clienteslistar", "/comercial/productoslistar", "/comercial/categoriaslistar", "/reports/**", "/uploads/**").permitAll()
            .requestMatchers("/comercial/clienteconsultar/**", "/comercial/productoconsultar/**", "/comercial/categoriaconsultar/**").hasAnyRole("USER")
            .requestMatchers("/comercial/clientenuevo/**", "/comercial/clienteeliminar/**", "/comercial/clientemodificar/**").hasAnyRole("ADMIN")
            .requestMatchers("/comercial/productonuevo/**", "/comercial/productoeliminar/**", "/comercial/productomodificar/**").hasAnyRole("ADMIN")
            .requestMatchers("/comercial/facturanueva/**", "/comercial/facturaconsultar/**", "/comercial/facturaeliminar/**").hasAnyRole("ADMIN")
            .requestMatchers("/mantenimiento/**").hasAnyRole("MMTO")
            .anyRequest().authenticated()
        )
        .formLogin(login -> login
            .loginPage("/login")
            //.defaultSuccessUrl("/comercial/productoslistar", true)
            .defaultSuccessUrl("/?loginSuccess", true)
            .permitAll()
        )
        .logout(logout -> logout
            .permitAll()
        )
        .exceptionHandling(ex -> ex
            .accessDeniedPage("/error_403")
        )
        .exceptionHandling(ex -> ex
	        .accessDeniedPage("/error_403")
        );

        return http.build();
    }
}
