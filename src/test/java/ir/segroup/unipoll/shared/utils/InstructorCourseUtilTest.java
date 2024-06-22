package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.*;
import ir.segroup.unipoll.ws.model.response.CommentResponse;
import ir.segroup.unipoll.ws.model.response.InstructorCourseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InstructorCourseUtilTest {

    @InjectMocks
    InstructorCourseUtil util;

    InstructorCourseEntity instructorCourseEntity = new InstructorCourseEntity();

    CourseEntity courseEntity = new CourseEntity();

    InstructorEntity instructorEntity = new InstructorEntity();

    StudentEntity studentEntity = new StudentEntity();

    RateEntity rateEntity1 = new RateEntity();

    RateEntity rateEntity2 = new RateEntity();

    RateEntity rateEntity3 = new RateEntity();

    List<BookletEntity> bookletEntities = new ArrayList<>();

    List<RateEntity> rateEntities = new ArrayList<>();

    List<ICCommentEntity> commentCEntities = new ArrayList<>();

    Method calculateRate;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        instructorCourseEntity = InstructorCourseEntity.builder()
                .id(1)
                .publicId("publicId")
                .lastUpdate("1/1/1")
                .courseEntity(courseEntity)
                .instructorEntity(instructorEntity)
                .description("desc")
                .bookletEntities(bookletEntities)
                .rateEntities(rateEntities)
                .commentCEntities(commentCEntities)
                .build();

        rateEntity1 = RateEntity.builder()
                .id(1)
                .number(3)
                .instructorCourseEntity(instructorCourseEntity)
                .studentEntity(studentEntity)
                .build();

        rateEntity2 = RateEntity.builder()
                .id(2)
                .number(4)
                .instructorCourseEntity(instructorCourseEntity)
                .studentEntity(studentEntity)
                .build();

        rateEntity3 = RateEntity.builder()
                .id(3)
                .number(5)
                .instructorCourseEntity(instructorCourseEntity)
                .studentEntity(studentEntity)
                .build();

        rateEntities.add(rateEntity1);
        rateEntities.add(rateEntity2);
        rateEntities.add(rateEntity3);

        calculateRate = util.getClass().getDeclaredMethod("calculateRate", List.class);
        calculateRate.setAccessible(true);
    }

    @Test
    void test_GiveValidInstructorCourseEntity_WhenCallConvert_ThenReturnValidInstructorCourseResponse(){
        //give
        //when
        InstructorCourseResponse response = util.convert(instructorCourseEntity);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getCourseName()).isEqualTo(instructorCourseEntity.getCourseEntity().getName());
        assertThat(response.getInstructorCourseFirstname()).isEqualTo(instructorCourseEntity.getInstructorEntity().getFirstname());
        assertThat(response.getInstructorCourseLastname()).isEqualTo(instructorCourseEntity.getInstructorEntity().getLastname());
    }

    @Test
    void test_GiveRateEntityList_WhenCallCalculateRate_ThenReturnRateNumber() throws InvocationTargetException, IllegalAccessException {
        //give;
        //when
        double response = (double) calculateRate.invoke(util,rateEntities);
        //then
        assertThat(response).isEqualTo(4);
    }
}