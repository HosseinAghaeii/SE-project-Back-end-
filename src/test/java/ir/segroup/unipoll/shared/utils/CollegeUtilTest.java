package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.AcademicDepartmentEntity;
import ir.segroup.unipoll.ws.model.entity.CollegeEntity;
import ir.segroup.unipoll.ws.model.request.CollegeRequest;
import ir.segroup.unipoll.ws.model.response.CollegeResponse;
import ir.segroup.unipoll.ws.model.response.InstructorCourseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CollegeUtilTest {

    ModelMapper modelMapper = new ModelMapper();

    CollegeUtil util = new CollegeUtil(modelMapper);

    CollegeEntity collegeEntity = new CollegeEntity();

    CollegeRequest collegeRequest = new CollegeRequest();

    List<AcademicDepartmentEntity> academicDepartmentEntities = new ArrayList<>();

    @BeforeEach
    void setUp() {
        collegeEntity = CollegeEntity.builder()
                .id(1)
                .name("comp")
                .academicDepartmentEntities(academicDepartmentEntities)
                .build();

        collegeRequest = CollegeRequest.builder()
                .name("comp")
                .build();
    }

    @Test
    void test_GiveValidCollegeRequest_WhenCallConvert_ThenReturnValidCollegeEntity(){
        //give
        //when
        CollegeEntity response = util.convert(collegeRequest);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(collegeRequest.getName());
    }

    @Test
    void test_GiveValidCollegeEntity_WhenCallConvert_ThenReturnValidCollegeResponse(){
        //give
        //when
        CollegeResponse response = util.convert(collegeEntity);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(collegeEntity.getName());
    }
}