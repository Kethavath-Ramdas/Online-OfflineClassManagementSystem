package ClassConnect.config;
	import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

	@Component
	public class JWTFilter extends OncePerRequestFilter {

	    @Autowired
	    private JwtUtils jwtUtil;

	    @Autowired
	    private UsersDetailsService userDetailsService;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    FilterChain filterChain)
	            throws ServletException, IOException {
//read authentication  barer token<>
	        String authHeader = request.getHeader("Authorization");
	        String token = null;
	        String username = null;

	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            token = authHeader.substring(7);
	            username = jwtUtil.extractUsername(token);
	        }
//check authentication status for avoiding duplication
	        if (username != null //token is valid  means authenticated 
	        		&&
	                SecurityContextHolder.getContext().getAuthentication() == null) //user not authenticated
	        	{
	            UserDetails userDetails =
	                    userDetailsService.loadUserByUsername(username);

	            if (jwtUtil.validateToken(token, userDetails)) {
	                UsernamePasswordAuthenticationToken authentication =
	                        new UsernamePasswordAuthenticationToken(
	                                userDetails,
	                                null,
	                                userDetails.getAuthorities()
	                        );

	                authentication.setDetails(
	                        new WebAuthenticationDetailsSource()
	                                .buildDetails(request)
	                );

	                SecurityContextHolder.getContext()
	                        .setAuthentication(authentication);
	            }
	        }

	        filterChain.doFilter(request, response);
	    }
	}


