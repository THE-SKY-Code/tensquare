package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

@ConfigurationProperties("jwt.config")
public class JwtUtil {

    private  String key;

    private  long ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成jwt
     * @param id
     * @param subject
     * @param roles
     * @return
     */
    public  String createJWT(String id,String subject,String roles){
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(date)
                .signWith(SignatureAlgorithm.HS256,key)
                .claim("roles",roles);
        if(ttl > 0){

        }
        return  builder.compact();
    }

    public Claims parseJWT(String jwt){
        return  Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
    }
}
