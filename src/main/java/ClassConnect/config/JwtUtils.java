package ClassConnect.config;

	import io.jsonwebtoken.Claims;
	import io.jsonwebtoken.Jwts;
	import io.jsonwebtoken.SignatureAlgorithm;
	import io.jsonwebtoken.security.Keys;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.stereotype.Component;

	import java.security.Key;
	import java.util.Date;

	@Component
	public class JwtUtils {

	    // step 1 SECRET KEY used for token Tamparing  secrete key mixed with the
		//token so others not modify  username or password when they are try  then invalid token
	    private static final String SECRET_KEY =
	            "my_jwt_secret_key_for_classconnect_123456";

	    // step 2 Time setting for expairation upto 10hs only after it will be expire  formate  millsec and sec and min
	    private static final long JWT_EXPIRATION_TIME =
	            10000 * 60 * 60;

	    private Key getSigningKey() {
	        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	    }

	    // Step 3 genrate tokn
	    public String generateToken(String username) {

	        return Jwts.builder()
	                .setSubject(username)
	                .setIssuedAt(new Date())
	                .setExpiration(
	                        new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME)
	                )
	                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
	                .compact();
	    }
// step 4 extracting usernm and expire date
	    public String extractUsername(String token) {
	        return extractAllClaims(token).getSubject();
	    }
	    private Date extractExpiration(String token) {
	        return extractAllClaims(token).getExpiration();
	    }
	    
	    // step 5 validating
	    public boolean validateToken(String token, UserDetails userDetails) {
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername())
	                && !isTokenExpired(token));
	    }
	    private boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }
	    //read all clims
	    private Claims extractAllClaims(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }
	}


