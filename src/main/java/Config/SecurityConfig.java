package Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
httpSecurity.csrf(t-> t.disable());
        httpSecurity.authorizeHttpRequests(authorize ->{
            authorize.anyRequest().authenticated();
        }
        );

        httpSecurity.oauth2ResourceServer(t->{
            t.jwt(Customizer.withDefaults());
        });
        return httpSecurity.build();

    }



}