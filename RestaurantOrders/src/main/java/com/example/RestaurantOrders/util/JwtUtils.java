package com.example.RestaurantOrders.util;

import com.example.RestaurantOrders.api.model.ApiProblemException;
import com.example.RestaurantOrders.model.Dish;
import com.example.RestaurantOrders.model.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtils {
    public static String secretPlain = "SuperSecretKey";
    public static User parseToken(String token) {
        String secret = shaEncode(secretPlain);
        User user = new User();
        try {
            var claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();

            user.id = (long) claims.get("user_id");
            user.role = (String) claims.get("role");
        } catch (JwtException e) {
            throw new ApiProblemException(
                    HttpStatus.UNAUTHORIZED,
                    "Incorrect token");
        }
        return user;
    }

    private static String shaEncode(final String clearText) {
        try {
            return new String(
                    Base64.getEncoder().encode(MessageDigest.getInstance("SHA-256").digest(clearText.getBytes(StandardCharsets.UTF_8))));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
