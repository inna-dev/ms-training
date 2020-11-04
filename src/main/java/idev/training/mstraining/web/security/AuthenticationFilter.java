package idev.training.mstraining.web.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import idev.training.mstraining.shared.UserDto;
import idev.training.mstraining.web.model.LoginRequestModel;
import idev.training.mstraining.web.service.UsersService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UsersService usersService;
    private Environment environment;

    @Autowired
    public AuthenticationFilter(
            UsersService usersService,
            Environment environment,
            AuthenticationManager authenticationManager
    ) {
        this.usersService = usersService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            LoginRequestModel loginRequestModel = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginRequestModel.getEmail(),
                    loginRequestModel.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        String userName = ((User) authResult.getPrincipal()).getUsername();
        UserDto userByEmail = usersService.getUserByEmail(userName);
        String token = Jwts.builder()
                           .setSubject(userByEmail.getUserId())
                           .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(Objects.requireNonNull(environment.getProperty(
                                   "token.expiration_time")))))
                           .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                           .compact();
        response.addHeader("token", token);
        response.addHeader("userId", userByEmail.getUserId());
    }
}
