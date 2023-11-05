package com.springboot.blog.config;

import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name         = "Bearer Authentication",
        type         =  SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme       = "bearer"
)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            UserDetailsService userDetailsService,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration conf) throws Exception {
        return conf.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}




/**
 *
 *
 *
   @http.csrf(AbstractHttpConfigurer::disable):
   En esta línea, se desactiva la protección contra CSRF (Cross-Site Request Forgery). CSRF es un tipo de
   ataque de seguridad web, y al desactivarlo, se permite que las solicitudes HTTP no necesiten un token
   CSRF válido.

   @.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated()): Esta línea configura la
   autorización de las solicitudes HTTP. Está indicando que todas las solicitudes deben estar autenticadas
   para ser permitidas. En otras palabras, se requiere que los usuarios estén autenticados para acceder a
   cualquier recurso o endpoint de la aplicación.

 @.httpBasic(Customizer.withDefaults()): Esta línea configura la autenticación HTTP básica. La autenticación
 básica HTTP es un método simple de autenticación en el que los usuarios deben proporcionar un nombre de
 usuario y una contraseña en las solicitudes HTTP. Customizer.withDefaults() se utiliza para aplicar la
 configuración predeterminada para la autenticación básica.


 public UserDetailsService userDetailsService() {
 UserDetails myUser = User.builder()
 .username("admin")
 .password(passwordEncoder().encode("1234569"))
 .roles("USER")
 .build();

 UserDetails userAdmin = User.builder()
 .username("admin2")
 .password(passwordEncoder().encode("admin"))
 .roles("ADMIN")
 .build();

 return new InMemoryUserDetailsManager(myUser, userAdmin);
 }

 public static PasswordEncoder passwordEncoder(){
 return new BCryptPasswordEncoder();
 }


 */