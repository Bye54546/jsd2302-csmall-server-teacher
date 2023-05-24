package cn.tedu.csmall.passport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTests {

    @Test
    void generate() {
        // 不太简单的、难以预测的字符串
        String secretKey = "jhkkjKJ3831HdSfdsDkdfSA9jklJD734f49FhsadsKf08dfjFhkdfs";

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 9527);
        claims.put("name", "张三");

        String jwt = Jwts.builder()
                // Header
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                // Payload
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 1000))
                // Verify Signature
                .signWith(SignatureAlgorithm.HS256, secretKey)
                // 生成
                .compact();
        System.out.println(jwt);
    }

    // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoi5byg5LiJIiwiaWQiOjk1MjcsImV4cCI6MTY4NDg5OTg5OX0.fYPkky9Eb57FCp-mE0mFXaKGlj2pl5zakoVfKTkcJ1g
    // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoi5byg5LiJIiwiaWQiOjk1MjcsImV4cCI6MTY4NDg5OTkyOH0.82A47it-a-UDRDHk5IW7udtH2o7GCDQtprM3g5H0KOE

    @Test
    void parse() {

    }

}
