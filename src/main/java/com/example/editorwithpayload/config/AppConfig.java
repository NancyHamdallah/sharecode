package com.example.editorwithpayload.config;

import com.example.editorwithpayload.handler.CustomAuthenticationSuccessHandler;
import com.example.editorwithpayload.service.security.UserDetailsServiceCustom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AppConfig {


    private final CustomAuthenticationSuccessHandler successHandler;

    public AppConfig(
                          CustomAuthenticationSuccessHandler successHandler) {

        this.successHandler = successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceCustom();
    }
//comment

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

        AuthenticationManager manager = builder.build();



        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/editor/**").hasAuthority("EDITOR")
                        .requestMatchers("/viewer/**").hasAuthority("VIEWER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/sign-in")
                        .successHandler(successHandler)
                        .permitAll()
                )

                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/403")
                )
                .csrf(csrf -> csrf.disable())
                .authenticationManager(manager)
                .httpBasic(withDefaults())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(authorities -> {
                                    return authorities.stream()
                                            .filter(a -> a instanceof OAuth2UserAuthority)
                                            .map(a -> (OAuth2UserAuthority) a)
                                            .findFirst()
                                            .map(authority -> {
                                                String email = (String) authority.getAttributes().get("email");

                                                    return List.of(new SimpleGrantedAuthority("EDITOR"));

                                            })
                                            .orElse(List.of(new SimpleGrantedAuthority("VIEWER")));
                                })

                )
                        .successHandler(successHandler)
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) ->
                web.ignoring()
                        .requestMatchers("/js/**", "/css/**");
    }

}
