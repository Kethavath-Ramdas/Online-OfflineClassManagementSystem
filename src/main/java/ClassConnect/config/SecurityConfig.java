package ClassConnect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    // Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication Manager  It verifies username & password during login
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Security Filter Chain which url is public and  which url need jwt
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        //CSRF is for session-based login  we dont need this so we have to disable we have jwt stateless
            .csrf(csrf -> csrf.disable())
//we have to declare that state less
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
            		//It all all pages in frontend Without this → browser UI will break get 403 
            		   .requestMatchers(
            			        "/",
            			        "/login",
            			        "/register",
            			        "/dashboard",
            			        "/attendance",
            			        "/attendance/history-page",
            			        "/class/create-page",
            			        "/css/**",
            			        "/js/**",
            			        "/images/**"
            			    ).permitAll()

            			    //  Protect APIs ONLY valid jwt token
            		.requestMatchers("/attendance/**","/class/**").authenticated()
            		.requestMatchers("/attendance/**").authenticated()
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/attendance", "/attendance/history").permitAll()
           // All backend API CALLS NEED JWT
              .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
            )

            //  Disable form login (JWT only) Not using Spring’s default login page
            .formLogin(form -> form.disable());

        // run  JWT Filter before check spring authentication
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
