package com.dzqc.cloud.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@SuppressWarnings("all")
public class JwtTokenUtil {

    /**
     * token有效期时间
     */
    @Value("${jwt.expire-interval}")
    private Long expireInterval;


    /**
     * 加密秘钥KEY
     */
    @Value("${jwt.authentication-key}")
    private String authenticationKey;

    /**
     * 通过token返回用户名
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String name = null;
        try{
            name = getClaimFromToken(token, Claims::getSubject);
        }catch (ExpiredJwtException e){
            name = e.getClaims().getSubject();
        }
        return name;
    }

    /**
     * 通过token得到token过期时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从token中获得用户信息
     *
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从token中解密 获得用户信息
     *
     * @param token
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(authenticationKey).parseClaimsJws(token).getBody();
    }

    /**
     * 验证token是否过期
     *
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration;
        try {
            expiration = getExpirationDateFromToken(token);
        }catch (ExpiredJwtException e){
            return true;
        }
        return expiration.before(new Date());
    }

    /**
     * 根据用户生成token
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * 根据用户生成token
     *
     * @param userDetails
     * @return
     */
    public String generateToken(String subject) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, subject);
    }

    /**
     * 生成token
     *
     * @param claims
     * @param subject
     * @return
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (expireInterval * 1000)))
                .signWith(SignatureAlgorithm.HS512, authenticationKey).compact();
    }

    /**
     * 验证token
     *
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName = getUsernameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
