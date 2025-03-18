package com.example.verifiserer.security.jwt;

import com.example.verifiserer.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/*import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;*/
import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    /*@Autowired
    private JwtService jwtService;*/



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

       /* if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Fjern "Bearer " prefix
            if (jwtService.validateToken(token)) {
                String username = jwtService.extractUsername(token);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
            }
        }*/
        chain.doFilter(request, response);
    }


}