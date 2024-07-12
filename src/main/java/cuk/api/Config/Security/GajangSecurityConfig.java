package cuk.api.Config.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import cuk.api.Config.Security.Filter.EntryPoint;
import cuk.api.Config.Security.Handler.JSONAccessDeniedHandler;
import cuk.api.Config.Security.Handler.JSONLogoutHandler;
import cuk.api.Config.Security.Handler.LoginFailureHandler;
import cuk.api.Config.Security.Filter.GajangLoginFilter;
import cuk.api.Config.Security.Handler.LoginSuccessHandler;
import cuk.api.User.Entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(1)
public class GajangSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
//    private static final String[] AUTH_WHITELIST = {
//            // -- Swagger UI v2
//            "/v2/api-docs",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/swagger-ui.html",
//            "/webjars/**",
//            // -- Swagger UI v3 (OpenAPI)
//            "/v3/api-docs/**",
//            "/swagger-ui/**",
//            // default
//            "/login",
//            "/api/**"
//    };

    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http
                .antMatcher("/gajang/**")
                .authorizeRequests()
                .antMatchers("/admin").hasRole(Role.ADMIN.getRoleWithoutPrefix())
                .antMatchers("/auth/**").hasRole(Role.MEMBER.getRoleWithoutPrefix())
                .antMatchers("/login", "/logout").permitAll()
                .anyRequest().authenticated()
//                .antMatchers(AUTH_WHITELIST).permitAll()
//                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .headers().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .addFilterBefore(gajangLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout().logoutUrl("/gajang/logout")
                .logoutSuccessHandler(jsonLogoutHandler())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint())
                .accessDeniedHandler(jsonAccessDeniedHandler())
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry());
        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {//AuthenticationManager 등록
        DaoAuthenticationProvider provider = daoAuthenticationProvider();//DaoAuthenticationProvider 사용
        provider.setPasswordEncoder(passwordEncoder());//PasswordEncoder로는 PasswordEncoderFactories.createDelegatingPasswordEncoder() 사용
        return new ProviderManager(provider);
    }

    @Bean
    public GajangLoginFilter gajangLoginFilter() throws Exception {
        GajangLoginFilter gajangLoginFilter = new GajangLoginFilter(objectMapper);
        gajangLoginFilter.setAuthenticationManager(authenticationManager());
        gajangLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        gajangLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return gajangLoginFilter;
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(objectMapper);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler(objectMapper);
    }

    @Bean
    public JSONLogoutHandler jsonLogoutHandler() {
        return new JSONLogoutHandler(objectMapper);
    }

    @Bean
    public EntryPoint entryPoint() {
        return new EntryPoint(objectMapper);
    }

    @Bean
    public JSONAccessDeniedHandler jsonAccessDeniedHandler() {
        return new JSONAccessDeniedHandler(objectMapper);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
