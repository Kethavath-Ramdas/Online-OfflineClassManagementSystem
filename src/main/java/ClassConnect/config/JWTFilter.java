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
//JwtFilter is used to check every request and validate the JWT token before allowing access to secured APIs.
	@Component
	public class JWTFilter extends OncePerRequestFilter {

	    @Autowired
	    private JwtUtils jwtUtil;

	    @Autowired
	    private UsersDetailsService userDetailsService;

	    @Override
	    //This method runs for every request
	    protected void doFilterInternal(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    FilterChain filterChain)
	            throws ServletException, IOException {
//read authentication  barer token<>
	        String authHeader = request.getHeader("Authorization");
	        //We’ll extract these from the header
	        String token = null;
	        String username = null;
//check jwttoken exist or not
	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            token = authHeader.substring(7);
	            username = jwtUtil.extractUsername(token);
	        }
//check authentication status for avoiding duplication
	        if (username != null //token is valid  means authenticated 
	        		&&
	                SecurityContextHolder.getContext().getAuthentication() == null) //user not authenticated
	        	{
//load the user detail from Data base like username and password and role
	            UserDetails userDetails =
	                    userDetailsService.loadUserByUsername(username);
//vadidate token
	            if (jwtUtil.validateToken(token, userDetails)) {
//create the authentication object
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
//set authentication in spring context
	                SecurityContextHolder.getContext()
	                        .setAuthentication(authentication);
	            }
	        }

	        filterChain.doFilter(request, response);
	    }
	}


