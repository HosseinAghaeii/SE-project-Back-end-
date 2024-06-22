package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.InstructorEntity;
import ir.segroup.unipoll.ws.model.response.InstructorCourseResponse;
import ir.segroup.unipoll.ws.model.response.InstructorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InstructorUtilTest {

    @InjectMocks
    InstructorUtil util;

    InstructorEntity instructorEntity = new InstructorEntity();

    @Test
    void test_GiveValidInstructorEntity_WhenCallConvert_ThenReturnValidInstructorResponse(){
        //give
        //when
        InstructorResponse response = util.convert(instructorEntity);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getFirstname()).isEqualTo(instructorEntity.getFirstname());
        assertThat(response.getLastname()).isEqualTo(instructorEntity.getLastname());
    }
}