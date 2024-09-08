package company.solo.gametogether.config;

import company.solo.gametogether.jwt.JwtAuthenticationFilter;
import company.solo.gametogether.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/members/sign-in","/error","/chat").permitAll()
                        .requestMatchers("/ws/**").permitAll() //authenticated 니중에 바꿀 예정 왜냐면 USER가 맞는지 확인해야 하기 때문
                        .requestMatchers("/members/sign-up").permitAll()
                        .requestMatchers("/members/find-user").permitAll()
                        .requestMatchers("/members/find-all").permitAll()
                        .requestMatchers("/members/{id}").permitAll()
                        .requestMatchers("/teams/create-team").hasRole("USER")
                        .requestMatchers("/teams/team-join").permitAll()
                        .requestMatchers("/teams/team-out").permitAll()
                        .requestMatchers("/teams/{id}").permitAll()
                        .requestMatchers("/teams/unread-message").permitAll()
                        .requestMatchers("/point/event/{memberId}").permitAll()
                        .requestMatchers("/point/{memberId}").permitAll()
                        .requestMatchers("/friend").permitAll()
                        .requestMatchers("/friend/list/{memberId}").permitAll()
                        .requestMatchers("/friend/{memberId}").permitAll()
                        .requestMatchers("/members/test").hasRole("USER")
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class).build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
