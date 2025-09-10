package com.prolyzeai.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtTokenManager
{

    @Value("${prolyze.secret-key}")
    String SECRETKEY;
    @Value("${prolyze.issuer}")
    String ISSUER;
    private final Long EXPIRES_IN_12H = 1000L * 60 * 60 * 12 ; // 12 Hour
    private final Long EXPIRES_IN_7D = 1000L * 60 * 60 * 24 * 7; // 7 Days
    private final Long EXPIRES_IN_30S = 1000L * 30; // 30 Seconds

    public Optional<String> createAccessToken (String authUuid){

        return createToken(authUuid, EXPIRES_IN_12H);
    }


    private Optional<String> createToken (String authUuid, Long expiresIn){

        String token;
        try{
            token = JWT.create().withAudience()
                    .withClaim("authId", authUuid)
                    .withIssuer(ISSUER)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expiresIn))
                    .sign(Algorithm.HMAC512(SECRETKEY));
            return Optional.of(token);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public Optional<String> getIdFromToken(String token){
        try {
            Algorithm algorithm=Algorithm.HMAC512(SECRETKEY);
            JWTVerifier verifier=JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT decodedJWT= verifier.verify(token);

            if (decodedJWT==null){
                throw new ProlyzeException(ErrorType.INVALID_TOKEN);
            }

            String id=decodedJWT.getClaim("authId").asString();
            return Optional.of(id);

        }catch (Exception e){
            throw new ProlyzeException(ErrorType.INVALID_TOKEN);
        }
    }

    public boolean validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC512(SECRETKEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getExpiresAt().after(new Date());

        } catch (Exception e) {
            return false;
        }
    }

    public Optional<String> createResetPasswordToken(String userId) {
        Long expiresIn15Min = 1000L * 60 * 15; // expires in 15 min
        return createToken(userId, expiresIn15Min);
    }

}