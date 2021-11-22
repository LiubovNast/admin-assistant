package com.alevel.nix7.adminassistant.config.security;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.config.security.filter.JwtAuthenticationFilter;
import com.alevel.nix7.adminassistant.config.security.filter.JwtAuthorizationFilter;
import com.alevel.nix7.adminassistant.model.Role;
import com.alevel.nix7.adminassistant.model.admin.AdminSaveRequest;
import com.alevel.nix7.adminassistant.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String OWNER = "OWNER";

    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final JwtComponent jwtComponent;
    private final ObjectMapper objectMapper;


    public SecurityConfiguration(AdminService adminService, PasswordEncoder passwordEncoder,
                                 JwtComponent jwtComponent, ObjectMapper objectMapper) {
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
        this.jwtComponent = jwtComponent;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // open static resources
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // open swagger-ui
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.POST, RootPath.ROOT).permitAll()

                .antMatchers(HttpMethod.GET, RootPath.USER)
                .hasAnyRole(Role.ROLE_ADMIN.name(), Role.ROLE_OWNER.name(), Role.ROLE_WORKER.name(), Role.ROLE_USER.name())
                .antMatchers(HttpMethod.POST, RootPath.USER)
                .hasAnyRole(Role.ROLE_ADMIN.name(), Role.ROLE_OWNER.name(), Role.ROLE_WORKER.name(), Role.ROLE_USER.name())

                .antMatchers(HttpMethod.POST, RootPath.WORKER).hasAnyRole(Role.ROLE_ADMIN.name(), Role.ROLE_OWNER.name(), Role.ROLE_WORKER.name())
                .antMatchers(HttpMethod.GET, RootPath.WORKER).hasAnyRole(Role.ROLE_ADMIN.name(), Role.ROLE_OWNER.name(), Role.ROLE_WORKER.name())
                .antMatchers(HttpMethod.DELETE, RootPath.WORKER).hasAnyRole(Role.ROLE_ADMIN.name(), Role.ROLE_OWNER.name())

                .antMatchers(HttpMethod.POST, RootPath.ADMIN).hasAuthority(OWNER)
                .antMatchers(HttpMethod.DELETE, RootPath.ADMIN).hasRole(OWNER)
                .antMatchers(HttpMethod.GET, RootPath.ADMIN).hasAnyRole(OWNER, "ADMIN")

                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole(OWNER)

                .anyRequest().authenticated()
                .and()
                .addFilter(jwtAuthenticationFilter())
                .addFilter(jwtAuthorizationFilter())
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private Filter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(authenticationManager(), jwtComponent);

    }

    private Filter jwtAuthenticationFilter() throws Exception {
        var filter = new JwtAuthenticationFilter(authenticationManager(), objectMapper);
        filter.setFilterProcessesUrl(RootPath.ROOT);
        return filter;
    }

    @PostConstruct
    public void init() {
        setupDefaultAdmin();
    }

    private void setupDefaultAdmin() {
        if (adminService.getByLogin("owner") == null) {
            adminService.create(new AdminSaveRequest("Main Owner",
                    passwordEncoder.encode("owner"),
                    "owner",
                    Role.ROLE_OWNER));
        }
    }

    private CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
