package com.alevel.nix7.adminassistant.config.security;

import com.alevel.nix7.adminassistant.RootPath;
import com.alevel.nix7.adminassistant.model.Role;
import com.alevel.nix7.adminassistant.model.admin.Admin;
import com.alevel.nix7.adminassistant.repository.AdminRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public SecurityConfiguration(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                // open static resources
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // open swagger-ui
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .antMatchers(RootPath.ROOT).permitAll()

                .antMatchers(HttpMethod.GET, RootPath.USER)
                .hasAnyRole(Role.ROLE_ADMIN.name(), Role.ROLE_OWNER.name(), Role.ROLE_WORKER.name(), Role.ROLE_USER.name())
                .antMatchers(HttpMethod.POST, RootPath.USER)
                .hasAnyRole(Role.ROLE_ADMIN.name(), Role.ROLE_OWNER.name(), Role.ROLE_WORKER.name(), Role.ROLE_USER.name())

                .antMatchers(HttpMethod.POST, RootPath.WORKER).hasAnyRole(Role.ROLE_ADMIN.name(), Role.ROLE_OWNER.name(), Role.ROLE_WORKER.name())
                .antMatchers(HttpMethod.GET, RootPath.WORKER).hasAnyRole(Role.ROLE_ADMIN.name(), Role.ROLE_OWNER.name(), Role.ROLE_WORKER.name())
                .antMatchers(HttpMethod.DELETE, RootPath.WORKER).hasAnyRole(Role.ROLE_ADMIN.name(), Role.ROLE_OWNER.name())

                .antMatchers(HttpMethod.POST, RootPath.ADMIN).hasRole("OWNER")
                .antMatchers(HttpMethod.DELETE, RootPath.ADMIN).hasRole("OWNER")
                .antMatchers(HttpMethod.GET, RootPath.ADMIN).hasAnyRole(Role.ROLE_OWNER.name(), Role.ROLE_ADMIN.name())

                //.anyRequest().authenticated()
                .and()
                .apply(new JwtConfig(jwtTokenProvider))

                // auth filter
//                .addFilter(jwtAuthenticationFilter())
//                // jwt-verification filter
//                .addFilter(jwtAuthorizationFilter())
                // for unauthorized requests return 401
//                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @PostConstruct
    public void init() {
        setupDefaultAdmin();
    }

    private void setupDefaultAdmin() {
        Admin admin = adminRepository.findAdminByLogin("owner");
        if (admin == null) {
            admin = new Admin();
            admin.setFullName("Main Owner");
            admin.setLogin("owner");
            admin.setPassword(passwordEncoder.encode("owner"));
            admin.setRole(Role.valueOf("ROLE_OWNER"));
            adminRepository.save(admin);
        }
    }

    private CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
