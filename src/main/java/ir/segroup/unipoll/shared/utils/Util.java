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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public String getJalaliDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int gy = calendar.get(Calendar.YEAR);
        int gm = localDate.getMonthValue();
        int gd = calendar.get(Calendar.DATE);



        int days;
        int jm;
        int jd;

        int gy2 = (gm > 2) ? (gy + 1) : gy;
        int[] gdm = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        days = 355666 + (365 * gy) + ((gy2 + 3) / 4) - ((gy2 + 99) / 100) + ((gy2 + 399) / 400) + gd + gdm[gm - 1];

        int jy = -1595 + (33 * (days / 12053));
        days %= 12053;
        jy += 4 * (days / 1461);
        days %= 1461;
        if (days > 365) {
            jy += (days - 1) / 365;
            days = (days - 1) % 365;
        }
        if (days < 186) {
            jm = 1 + (days / 31);
            jd = 1 + (days % 31);
        } else {
            jm = 7 + ((days - 186) / 30);
            jd = 1 + ((days - 186) % 30);
        }
        return jy + "/" + jm + "/" + jd;
    }

}
