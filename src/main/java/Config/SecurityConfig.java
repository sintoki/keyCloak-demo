package Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


@Autowired
private JwtAuthConverter jwtAuthConverter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//httpSecurity.csrf(t-> t.disable());
//        httpSecurity.authorizeHttpRequests(authorize ->{
//            authorize.anyRequest().authenticated();
////            ikkadra clown lk ikkada annitiji authorize pettukoni owner enduku chestunnadu antav entra erripuka
//        }
//        );

//        httpSecurity.oauth2ResourceServer(t->{
//            t.jwt(Customizer.withDefaults());
//        });

//        httpSecurity.oauth2ResourceServer(t->{
//            t.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter));
//        });httpSecurity.sessionManagement(
//                t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );


        http.csrf(t -> t.disable());
        http.authorizeHttpRequests(authorize -> {
            authorize.anyRequest().authenticated();
            authorize
                    .requestMatchers(HttpMethod.GET, "/restaurant/public/list").permitAll()
                    .requestMatchers(HttpMethod.GET, "/restaurant/public/menu/*").permitAll()
                    .anyRequest().authenticated();
        });
        http.oauth2ResourceServer(t-> {
            //t.jwt(Customizer.withDefaults());
            t.opaqueToken(Customizer.withDefaults());
            t.jwt(configurer -> configurer.jwtAuthenticationConverter(jwtAuthConverter));
            //t.opaqueToken(Customizer.withDefaults());
        });
        http.sessionManagement(
                t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

//        httpSecurity.oauth2ResourceServer().opaqueToken().introspector(in)
        return http.build();
    }






}