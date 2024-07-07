package com.api.note.configuration;

import com.api.note.user.service.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private final JWTTokenUtils jwtTokenUtils;
    @Autowired
    private final UserDetailService userDetailService;

    public JWTAuthenticationFilter( JWTTokenUtils jwtTokenUtils, UserDetailService userDetailService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(
                @NonNull HttpServletRequest request,
                @NonNull HttpServletResponse response,
                @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.substring(7);
        String username = jwtTokenUtils.getUsernameFromToken(token);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails =  userDetailService.loadUserByUsername(username);
            if(jwtTokenUtils.validateToken(token , userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request , response);

    }

}
