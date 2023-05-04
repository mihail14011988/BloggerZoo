/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import miha.zika.entity.UserBlogger;
import miha.zika.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Miha
 */
public class JWTAuthoFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JWTAuthoFilter.class);
    @Autowired
    private JWTtokenProvider provider;
    @Autowired
    private CustomUserDetailsService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJWTokenFromRequest(request);
            if (StringUtils.hasText(jwt) && provider.validateToken(jwt)) {
                Long userId = provider.getUserIdFromToken(jwt);
                UserBlogger userDetails = service.loadUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception ex) {
            LOG.error("Could not authenfication");
        }
        filterChain.doFilter(request, response);
    }

    private String getJWTokenFromRequest(HttpServletRequest request) {
        String bearToken = request.getHeader(SecurityConst.HEADER_STRING);
        if (StringUtils.hasText(bearToken) && bearToken.startsWith(SecurityConst.TOKEN_PREFIX)) {
            return bearToken.split(" ")[1];
        }
        return null;
    }

}
