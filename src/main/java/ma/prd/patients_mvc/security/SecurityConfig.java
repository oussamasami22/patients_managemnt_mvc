package ma.prd.patients_mvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        String pwd = passwordEncoder.encode("12345");
        System.out.println(pwd);
        UserDetails user1 = User.withUsername("user1")
                .password(pwd)
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("3333"))
                .roles("USER", "ADMIN")
                .build();

        UserDetails user2 = User.withUsername("user2")
                .password(passwordEncoder.encode("4444"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, admin, user2);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login", "/notAuthorized").permitAll()
                        .requestMatchers("/delete/**", "/save/**", "/editPatient/**", "/formPatients/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ) // we can add admin/** and edite the mapping in controller (for exemple admin/delete) and it will working
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendRedirect("/notAuthorized")
                        )
                );

        return http.build();
    }
}
