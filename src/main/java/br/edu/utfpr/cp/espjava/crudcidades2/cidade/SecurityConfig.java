package br.edu.utfpr.cp.espjava.crudcidades2.cidade;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager configure() throws Exception{
        UserDetails beto = User.withUsername("beto")
                .password(cifrador().encode("test123"))
                .roles("admin")
                .build();
        UserDetails ana = User.withUsername("ana")
                .password(cifrador().encode("test123"))
                .roles("listar")
                .build();

        return new InMemoryUserDetailsManager(beto, ana);
    }

    @Bean
    public PasswordEncoder cifrador(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/").hasAnyRole("listar", "admin")
                .requestMatchers("/criar","/excluir","/alterar","/preparaAlterar").hasRole("admin")
                .anyRequest().denyAll()
                .and()
                .formLogin().permitAll()
                .and()
                .build();
    }
}
