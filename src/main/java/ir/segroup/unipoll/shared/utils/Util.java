package ir.segroup.unipoll.shared.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.security.constant.SecurityConstants;
import ir.segroup.unipoll.shared.model.BaseApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Util {

    public ResponseEntity<BaseApiResponse> createResponse(Object object, HttpStatus httpStatus) {
        BaseApiResponse baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .date(new Date())
                .message("")
                .result(object)
                .build();
        return new ResponseEntity<>(baseApiResponse, httpStatus);
    }

    public String getUsernameFromToken(String token) {
        if (token == null){
            return null;
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.JWT_KEY.getBytes())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return (String) claims.get("username");
        } catch (JwtException e) {
            throw new SystemServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
