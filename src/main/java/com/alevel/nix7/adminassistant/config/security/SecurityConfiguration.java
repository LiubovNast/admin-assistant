package com.alevel.nix7.adminassistant.config.security;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.config.security.filter.JwtAuthenticationFilter;
import com.alevel.nix7.adminassistant.config.security.filter.JwtAuthorizationFilter;
import com.alevel.nix7.adminassistant.model.admin.AdminSaveRequest;
import com.alevel.nix7.adminassistant.repository.AdminRepository;
import com.alevel.nix7.adminassistant.service.impl.AdminServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
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

    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfiguration.class);

    private final AdminServiceImpl adminService;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtComponent jwtComponent;
    private final ObjectMapper objectMapper;


    public SecurityConfiguration(AdminServiceImpl adminService,
                                 AdminRepository adminRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtComponent jwtComponent,
                                 ObjectMapper objectMapper) {
        this.adminService = adminService;
        this.adminRepository = adminRepository;
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
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // open swagger-ui
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .antMatchers(RootPath.ROOT, RootPath.ROOT + "/**").permitAll()
                .and()
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
        if (!adminRepository.existsByLogin("owner")) {
            adminService.createOwner(new AdminSaveRequest("Main Owner",
                    passwordEncoder.encode("owner"),
                    "owner"));
            LOG.info("Create default owner");
        }
    }

    private CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
