package com.project.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.services.JWTService;
import com.project.services.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		
		  String path = request.getServletPath();

		    if (path.startsWith("/api/customer/register") ||
		        path.startsWith("/api/customer/login") ||
		        path.startsWith("/api/customer/updatePassword")) {
		        filterChain.doFilter(request, response);
		        return;
		    }
		
		
		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		try {
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
				username = jwtService.extractUserName(token);
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (jwtService.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authToken);
					System.out.println("JWT Authenticated user: " + authToken.getName());

				}
			}

		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
			return;
		} catch (io.jsonwebtoken.MalformedJwtException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Malformed JWT token");
			return;
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token: " + e.getMessage());
			return;
		}

		filterChain.doFilter(request, response);

	}
}