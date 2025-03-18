package naver.webtoon.project.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import naver.webtoon.project.common.UserDetailsServiceImpl;
import naver.webtoon.project.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String AUTHORIZATION_ACCESS = "AccessToken";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 2 * 60 * 60 * 1000L;
    @Value("${jwt.secret.key.access}")
    private String accessTokenSecretKey;
    private Key accessTokenKey;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final UserDetailsServiceImpl userDetailsService;

    @PostConstruct
    public void init(){

        byte[] accessTokenBytes = Base64.getDecoder().decode(accessTokenSecretKey);
        accessTokenKey = Keys.hmacShaKeyFor(accessTokenBytes);
    }

    public String resolveToken(HttpServletRequest request, String authorization) {

        String bearerToken = request.getHeader(authorization);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }

    public String createAccessToken(String username) {

        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(accessTokenKey, signatureAlgorithm)
                        .compact();
    }

    public void validateAccessToken(HttpServletRequest request, HttpServletResponse response) {

        try {
            Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(request.getHeader(AUTHORIZATION_ACCESS).substring(7));
        } catch (SecurityException | MalformedJwtException e) {
            e.printStackTrace();
            request.setAttribute("exception", ErrorCode.INVALID_ACCESS_TOKEN.getCode());
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            request.setAttribute("exception", ErrorCode.EXPIRATION_ACCESS_TOKEN.getCode());
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            request.setAttribute("exception", ErrorCode.ACCESS_TOKEN_NOT_SUPPORT.getCode());
        } catch (JwtException e) {
            e.printStackTrace();
            request.setAttribute("exception", ErrorCode.UNKNOWN_ACCESS_TOKEN_ERROR.getCode());
        }
    }

    public Claims getUserInfoFromToken(String token, boolean isRefresh) {
        if(isRefresh){
            try {
                return Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException e) {
                return e.getClaims();
            }
        } else {
            return Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(token).getBody();
        }
    }

    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, null);
    }
}
