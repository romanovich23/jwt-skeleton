package com.roman.jwtskeleton.security;

import com.roman.jwtskeleton.model.mapper.UserDetailsMapper;
import com.roman.jwtskeleton.model.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.roman.jwtskeleton.security.Constants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTH_KEY);
        if (null == header || !header.startsWith(TOKEN_BEARER_PREFIX)) {
            chain.doFilter(request, response);
        }
        UsernamePasswordAuthenticationToken authentication = this.getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header) {
        if (null == header) throw new NullPointerException();
        String user = Jwts.parser()
                .setSigningKey(SUPER_SECRET_KEY)
                .parseClaimsJws(header.replace(TOKEN_BEARER_PREFIX, ""))
                .getBody()
                .getSubject();
        if (null == user) return null;
        UserDetails userDetails = UserDetailsMapper.build(this.userRepository.findByUsername(user));
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }
}
