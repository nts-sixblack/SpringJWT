package nts.sixblack.checkjwt.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import nts.sixblack.checkjwt.util.CustomUserDetail;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    private final String JWT_SECRET="sixblack";
    private final long JWT_EXPIRATION=60*60;

    public String generateToken(CustomUserDetail customUserDetail){
        Date now = new Date();
        Date expiration = new Date(now.getTime()+JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(Long.toString(customUserDetail.getUser().getUserId()))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public long getUserId(String token){
        return Long.parseLong(Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject());
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
