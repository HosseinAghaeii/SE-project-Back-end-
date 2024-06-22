package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.AcademicDepartmentEntity;
import ir.segroup.unipoll.ws.model.entity.ContactEntity;
import ir.segroup.unipoll.ws.model.entity.CourseEntity;
import ir.segroup.unipoll.ws.model.entity.InstructorCourseEntity;
import ir.segroup.unipoll.ws.model.request.ContactRequest;
import ir.segroup.unipoll.ws.model.request.CourseRequest;
import ir.segroup.unipoll.ws.model.response.ContactResponse;
import ir.segroup.unipoll.ws.model.response.CourseResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourseUtilTest {

    ModelMapper modelMapper = new ModelMapper();
    CourseUtil util = new CourseUtil(modelMapper);

    CourseEntity courseEntity = new CourseEntity();

    CourseRequest courseRequest = new CourseRequest();

    List<InstructorCourseEntity> icEntities = new ArrayList<>();

    List<AcademicDepartmentEntity> academicDepartmentEntities = new ArrayList<>();

    @BeforeEach
    void setUp() {
        courseEntity = CourseEntity.builder()
                .id(1)
                .publicId("publicId")
                .name("math")
                .unit(3)
                .icEntities(icEntities)
                .academicDepartmentEntities(academicDepartmentEntities)
                .build();

        courseRequest = CourseRequest.builder()
                .name("math")
                .unit(3)
                .build();
    }

    @Test
    void test_GiveValidCourseRequest_WhenCallConvert_ThenReturnValidCourseEntity(){
        //give
        //when
        CourseEntity response = util.convert(courseRequest);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(courseRequest.getName());
    }

    @Test
    void test_GiveValidCourseEntity_WhenCallConvert_ThenReturnValidCourseResponse(){
        //give
        //when
        CourseResponse response = util.convert(courseEntity);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(courseEntity.getName());
    }
}