package ir.segroup.unipoll.shared.utils;


import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UtilTest {
    Util util = new Util() ;

    @Test
    void test_GiveTrueInput_WhenCallCreateResponse_ThenReturnTrueResponseEntity(){
        //give
        Object object = "testObject";
        HttpStatus status = HttpStatus.OK;
        //when
        ResponseEntity<BaseApiResponse> response = util.createResponse(object,status);
        BaseApiResponse body = response.getBody();
        //then
        assertThat(response).isNotNull();
        assertThat(body).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(status);
        assertThat(body.isAction()).isEqualTo(true);
        assertThat(body.getMessage()).isEqualTo("");
        assertThat(body.getResult()).isEqualTo(object);
    }

    @Test
    void test_GiveNullToken_WhenCallGetUsernameFromToken_ThenReturnNull(){
        //give
        //when
        String response = util.getUsernameFromToken(null);
        //then
        assertThat(response).isNull();
    }

    @Test
    void test_GiveValidToken_WhenCallGetUsernameFromToken_ThenReturnUsername(){
        //give
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJVbmlQb2xsIiwic3ViIjoiandUb2tlbiIsInVzZXJuYW1lIjoiYWRtaW4iLCJhdXRob3JpdGllcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE3MTg2MjUwNjgsImV4cCI6MTcxODY1NTA2OH0.0EQ3yPBOtYgCp8I1ib4NMJ-zXnfX8MHa9sM79Z69om-EUsiGMpEHsOp9CYd2Vo5b0cz8RtJzUNG2lKRb9ph4wg";
        //when
        String response = util.getUsernameFromToken(token);
        //then
        assertThat(response).isEqualTo("admin");
    }

    @Test
    void test_GiveBadToken_WhenCallGetUsernameFromToken_ThenThrowException(){
        //give
        String token = "BadToken";
        //then
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class, () ->
                //when
                util.getUsernameFromToken(token));
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void test1_GiveDate_WhenCallGetJalaliDate_ThenReturnJalaliDate(){
        //give
        LocalDate localDate = LocalDate.of(2024, 6, 17);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //when
        String response = util.getJalaliDate(date);
        //then
        assertThat(response).isEqualTo("1403/3/28");
    }
    @Test
    void test2_GiveDate_WhenCallGetJalaliDate_ThenReturnJalaliDate(){
        //give
        LocalDate localDate = LocalDate.of(2024, Month.APRIL, 19);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //when
        String response = util.getJalaliDate(date);
        //then
        assertThat(response).isEqualTo("1403/1/31");
    }

    @Test
    void test3_GiveDate_WhenCallGetJalaliDate_ThenReturnJalaliDate(){
        //give
        LocalDate localDate = LocalDate.of(2024, Month.MARCH, 20);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //when
        String response = util.getJalaliDate(date);
        //then
        assertThat(response).isEqualTo("1403/1/1");
    }
    @Test
    void test4_GiveDate_WhenCallGetJalaliDate_ThenReturnJalaliDate(){
        //give
        LocalDate localDate = LocalDate.of(2024, Month.MARCH, 19);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //when
        String response = util.getJalaliDate(date);
        //then
        assertThat(response).isEqualTo("1402/12/29");
    }
}