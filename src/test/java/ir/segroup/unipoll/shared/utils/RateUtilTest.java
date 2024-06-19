package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.ws.model.entity.InstructorCourseEntity;
import ir.segroup.unipoll.ws.model.entity.RateEntity;
import ir.segroup.unipoll.ws.model.entity.StudentEntity;
import ir.segroup.unipoll.ws.model.request.RateRequest;
import ir.segroup.unipoll.ws.model.response.ARateResponse;
import ir.segroup.unipoll.ws.model.response.CommentResponse;
import ir.segroup.unipoll.ws.model.response.RateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateUtilTest {

    InstructorCourseUtil instructorCourseUtil = new InstructorCourseUtil();

    @InjectMocks
    RateUtil util = new RateUtil(instructorCourseUtil);

    InstructorCourseEntity instructorCourseEntity = new InstructorCourseEntity();

    StudentEntity studentEntity = new StudentEntity();

    List<RateEntity> rateEntityList = new ArrayList<>();

    RateEntity rateEntity = new RateEntity();

    RateRequest rateRequest = new RateRequest();

    @BeforeEach
    void setUp() {
        rateEntity = RateEntity.builder()
                .id(1)
                .number(4)
                .instructorCourseEntity(instructorCourseEntity)
                .studentEntity(studentEntity)
                .build();

        rateRequest = RateRequest.builder()
                .rate(4)
                .build();

        rateEntityList.add(rateEntity);
    }

    @Test
    void test_GiveValidRateEntityListAndRate_WhenCallConvert_ThenReturnValidRateResponse(){
        //give
        //when
        RateResponse response = util.convert(rateEntityList,4);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getAverageRate()).isEqualTo(4);
    }

    @Test
    void test_GiveValidRateRequest_WhenCallConvert_ThenReturnValidRateEntity(){
        //give
        //when
        RateEntity response = util.convert(studentEntity, instructorCourseEntity, rateRequest);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getNumber()).isEqualTo(4);
    }

    @Test
    void test_GiveValidRateEntityList_WhenCallConvert_ThenReturnValidRateResponse(){
        //give
        //when
        ARateResponse response = util.convert(rateEntity);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getRate()).isEqualTo(4);
    }

    @Test
    void test_GiveValidRateRequest_WhenCallUpdate_ThenReturnValidRateEntity(){
        //give
        //when
        RateEntity response = util.update(rateEntity, rateRequest);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getNumber()).isEqualTo(4);
    }
}