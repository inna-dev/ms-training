package idev.training.mstraining.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import idev.training.mstraining.web.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final UsersService usersService;
    private final BCryptPasswordEncoder bBCryptPasswordEncoder;
    private final Environment environment;

    @Autowired
    public WebSecurity(
            UsersService usersService,
            BCryptPasswordEncoder bBCryptPasswordEncoder,
            Environment environment
    ) {
        this.usersService = usersService;
        this.bBCryptPasswordEncoder = bBCryptPasswordEncoder;
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
            .disable();
        http.authorizeRequests()
            .antMatchers(setPaths("/users/**", "/login/**"))
            .permitAll()
            .and()
            .addFilter(getAuthenticationFilter());
        http.headers()
            .frameOptions()
            .disable();
    }

    private String[] setPaths(String... paths) {
        return paths;
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment, authenticationManager());
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService)
            .passwordEncoder(bBCryptPasswordEncoder);
    }
}
