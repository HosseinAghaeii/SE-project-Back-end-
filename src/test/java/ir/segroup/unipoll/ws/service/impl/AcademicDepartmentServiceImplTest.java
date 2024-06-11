package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.AcademicDepartmentUtil;
import ir.segroup.unipoll.ws.model.entity.AcademicDepartmentEntity;
import ir.segroup.unipoll.ws.model.response.DepartmentCourseResponse;
import ir.segroup.unipoll.ws.model.response.DepartmentInstructorResponse;
import ir.segroup.unipoll.ws.model.response.DepartmentManagerAndAssistantResponse;
import ir.segroup.unipoll.ws.model.response.DepartmentResponse;
import ir.segroup.unipoll.ws.repository.AcademicDepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AcademicDepartmentServiceImplTest {

    @Mock
    private AcademicDepartmentRepository departmentRepository;

    @Mock
    private AcademicDepartmentUtil departmentUtil;

    @InjectMocks
    private AcademicDepartmentServiceImpl departmentService;

    private AcademicDepartmentEntity departmentEntity;
    private DepartmentResponse departmentResponse;
    private ResponseEntity<BaseApiResponse> responseOk;
    private String publicId;
    private String name;
    private String description;


    @BeforeEach
    void setUp() {
        publicId = UUID.randomUUID().toString().replace("-", "");
        name = "نرم افزار";
        description = "گروه مهندسی نرم افزار";

        departmentEntity = AcademicDepartmentEntity.builder()
                .publicId(publicId)
                .name(name)
                .description(description)
                .build();

        departmentResponse = DepartmentResponse.builder()
                .publicId(publicId)
                .name(name)
                .description(description)
                .build();

        BaseApiResponse baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .message("")
                .date(new Date())
                .result(departmentResponse)
                .build();

        responseOk = new ResponseEntity<>(baseApiResponse, HttpStatus.OK);
    }

    @Test
    void testGetADepartment_GivenExistPublicId_WhenCallGetADepartment_ThenShouldReturnDepartmentResponse(){
        // Given
        when(departmentUtil.find(any(String.class))).thenReturn(departmentEntity);
        when(departmentUtil.convert(any(AcademicDepartmentEntity.class))).thenReturn(departmentResponse);
        when(departmentUtil.createResponse(any(DepartmentResponse.class), any(HttpStatus.class))).thenReturn(responseOk);

        // When
        ResponseEntity<BaseApiResponse> department = departmentService.getADepartment(publicId);

        // Then
        assertThat(department).isNotNull();
        assertThat(department.getStatusCode()).isEqualTo(HttpStatus.OK);

        DepartmentResponse response = (DepartmentResponse) department.getBody().getResult();
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getDescription()).isEqualTo(description);
        assertThat(response.getPublicId()).isEqualTo(publicId);
    }

    @Test
    void testGetADepartment_GivenNotExistPublicId_WhenCallGetADepartment_ThenShouldReturnSystemServiceException(){
        //given
        when(departmentUtil.find(any(String.class))).thenReturn(null);
        //then
            //when
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class,() ->
                departmentService.getADepartment("someNonExistentId"));
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(systemServiceException.getException()).isEqualTo(ExceptionMessages.NO_RECORD_FOUND.getMessage());

    }

    @Test
    void testGetManagerAndAssistant_GivenExistPublicId_WhenCallGetManagerAndAssistant_ThenShouldReturnDepartmentManagerAndAssistantResponse() {
        List<DepartmentManagerAndAssistantResponse> responses = new ArrayList<>();
        String managerPublicId = UUID.randomUUID().toString().replace("-", "");
        String assistantPublicId = UUID.randomUUID().toString().replace("-", "");
        String managerType = "Manager";
        String managerFirstname = "رضا";
        String managerLastname = "رمضانی";
        String managerProfilePhoto = "ramezani.jpg";
        String managerPhd = "دکتری مهندسی کامپیوتر";
        String managerAcademicRank = "نامشخص";
        String managerPhoneNumber = "37935621";
        String managerEmail = "r.ramezani@eng.ui.ac.ir";
        String managerWebsite = "https://eng.ui.ac.ir/~r.ramezani";

        String assistantType = "Assistant";
        String assistantFirstname = "محمد رضا ";
        String assistantLastname = "شعرباف";
        String assistantProfilePhoto = "sharbaf.png";
        String assistantPhd = "دکتری مهندسی کامپیوتر-نرم افزار";
        String assistantAcademicRank = "نامشخص";
        String assistantPhoneNumber = "37935641";
        String assistantEmail = "m.sharbaf@eng.ui.ac.ir";
        String assistantWebsite = "https://engold.ui.ac.ir/~m.sharbaf";
        responses.add(DepartmentManagerAndAssistantResponse.builder()
                .type(managerType)
                .publicId(managerPublicId)
                .firstname(managerFirstname)
                .lastname(managerLastname)
                .profilePhoto(managerProfilePhoto)
                .phd(managerPhd)
                .academicRank(managerAcademicRank)
                .phoneNumber(managerPhoneNumber)
                .email(managerEmail)
                .websiteLink(managerWebsite)
                .build());

        responses.add(DepartmentManagerAndAssistantResponse.builder()
                .type(assistantType)
                .publicId(assistantPublicId)
                .firstname(assistantFirstname)
                .lastname(assistantLastname)
                .profilePhoto(assistantProfilePhoto)
                .phd(assistantPhd)
                .academicRank(assistantAcademicRank)
                .phoneNumber(assistantPhoneNumber)
                .email(assistantEmail)
                .websiteLink(assistantWebsite)
                .build());

        BaseApiResponse baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .message("")
                .date(new Date())
                .result(responses)
                .build();

        responseOk = new ResponseEntity<>(baseApiResponse, HttpStatus.OK);

        // Given
        when(departmentUtil.find(any(String.class))).thenReturn(departmentEntity);
        when(departmentUtil.managerAndAssistantConvert(any(AcademicDepartmentEntity.class))).thenReturn(responses);
        when(departmentUtil.createResponse(any(), any(HttpStatus.class))).thenReturn(responseOk);

        // When
        ResponseEntity<BaseApiResponse> department = departmentService.getManagerAndAssistant(publicId);

        // Then
        assertThat(department).isNotNull();
        assertThat(department.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<DepartmentManagerAndAssistantResponse> response = (List<DepartmentManagerAndAssistantResponse>) department.getBody().getResult();
        assertThat(response.get(0).getType()).isEqualTo(managerType);
        assertThat(response.get(0).getPublicId()).isEqualTo(managerPublicId);
        assertThat(response.get(0).getFirstname()).isEqualTo(managerFirstname);
        assertThat(response.get(0).getLastname()).isEqualTo(managerLastname);
        assertThat(response.get(0).getProfilePhoto()).isEqualTo(managerProfilePhoto);
        assertThat(response.get(0).getPhd()).isEqualTo(managerPhd);
        assertThat(response.get(0).getAcademicRank()).isEqualTo(managerAcademicRank);
        assertThat(response.get(0).getPhoneNumber()).isEqualTo(managerPhoneNumber);
        assertThat(response.get(0).getEmail()).isEqualTo(managerEmail);
        assertThat(response.get(0).getWebsiteLink()).isEqualTo(managerWebsite);

        assertThat(response.get(1).getType()).isEqualTo(assistantType);
        assertThat(response.get(1).getPublicId()).isEqualTo(assistantPublicId);
        assertThat(response.get(1).getFirstname()).isEqualTo(assistantFirstname);
        assertThat(response.get(1).getLastname()).isEqualTo(assistantLastname);
        assertThat(response.get(1).getProfilePhoto()).isEqualTo(assistantProfilePhoto);
        assertThat(response.get(1).getPhd()).isEqualTo(assistantPhd);
        assertThat(response.get(1).getAcademicRank()).isEqualTo(assistantAcademicRank);
        assertThat(response.get(1).getPhoneNumber()).isEqualTo(assistantPhoneNumber);
        assertThat(response.get(1).getEmail()).isEqualTo(assistantEmail);
        assertThat(response.get(1).getWebsiteLink()).isEqualTo(assistantWebsite);
    }

    @Test
    void testGetCourses_GivenExistPublicId_WhenCallGetCourses_ThenShouldReturnDepartmentCourseResponse(){
        List<DepartmentCourseResponse> responses = new ArrayList<>();
        String course1PublicId = UUID.randomUUID().toString().replace("-", "");
        String course2PublicId = UUID.randomUUID().toString().replace("-", "");
        String course1Name = "ساختمان داده ها";
        String course2Name = "مهندسی نرم افزار";
        responses.add(DepartmentCourseResponse.builder()
                .courseName(course1Name)
                .publicId(course1PublicId)
                .build());

        responses.add(DepartmentCourseResponse.builder()
                .courseName(course2Name)
                .publicId(course2PublicId)
                .build());

        BaseApiResponse baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .message("")
                .date(new Date())
                .result(responses)
                .build();

        responseOk = new ResponseEntity<>(baseApiResponse, HttpStatus.OK);
        // Given
        when(departmentUtil.find(any(String.class))).thenReturn(departmentEntity);
        when(departmentUtil.courseConvert(any(AcademicDepartmentEntity.class))).thenReturn(responses);
        when(departmentUtil.createResponse(any(), any(HttpStatus.class))).thenReturn(responseOk);

        // When
        ResponseEntity<BaseApiResponse> department = departmentService.getCourses(publicId);

        // Then
        assertThat(department).isNotNull();
        assertThat(department.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<DepartmentCourseResponse> response = (List<DepartmentCourseResponse>) department.getBody().getResult();
        assertThat(response.get(0).getCourseName()).isEqualTo(course1Name);
        assertThat(response.get(0).getPublicId()).isEqualTo(course1PublicId);
        assertThat(response.get(1).getCourseName()).isEqualTo(course2Name);
        assertThat(response.get(1).getPublicId()).isEqualTo(course2PublicId);
    }

    @Test
    void testGetInstructors_GivenExistPublicId_WhenCallGetInstructors_ThenShouldReturnDepartmentInstructorResponse(){
        List<DepartmentInstructorResponse> responses = new ArrayList<>();
        String inst1PublicId = UUID.randomUUID().toString().replace("-", "");
        String inst2PublicId = UUID.randomUUID().toString().replace("-", "");

        String inst1Firstname = "رضا";
        String inst1Lastname = "رمضانی";
        String inst1ProfilePhoto = "ramezani.jpg";
        String inst1AcademicRank = "نامشخص";

        String inst2Firstname = "محمد رضا ";
        String inst2Lastname = "شعرباف";
        String inst2ProfilePhoto = "sharbaf.png";
        String inst2AcademicRank = "نامشخص";
        responses.add(DepartmentInstructorResponse.builder()
                .publicId(inst1PublicId)
                .firstname(inst1Firstname)
                .lastname(inst1Lastname)
                .profilePhoto(inst1ProfilePhoto)
                .academicRank(inst1AcademicRank)
                .build());

        responses.add(DepartmentInstructorResponse.builder()
                .publicId(inst2PublicId)
                .firstname(inst2Firstname)
                .lastname(inst2Lastname)
                .profilePhoto(inst2ProfilePhoto)
                .academicRank(inst2AcademicRank)
                .build());

        BaseApiResponse baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .message("")
                .date(new Date())
                .result(responses)
                .build();

        responseOk = new ResponseEntity<>(baseApiResponse, HttpStatus.OK);
        // Given
        when(departmentUtil.find(any(String.class))).thenReturn(departmentEntity);
        when(departmentUtil.instructorConvert(any(AcademicDepartmentEntity.class))).thenReturn(responses);
        when(departmentUtil.createResponse(any(), any(HttpStatus.class))).thenReturn(responseOk);

        // When
        ResponseEntity<BaseApiResponse> department = departmentService.getInstructors(publicId);

        // Then
        assertThat(department).isNotNull();
        assertThat(department.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<DepartmentInstructorResponse> response = (List<DepartmentInstructorResponse>) department.getBody().getResult();
        assertThat(response.get(0).getPublicId()).isEqualTo(inst1PublicId);
        assertThat(response.get(0).getFirstname()).isEqualTo(inst1Firstname);
        assertThat(response.get(0).getLastname()).isEqualTo(inst1Lastname);
        assertThat(response.get(0).getProfilePhoto()).isEqualTo(inst1ProfilePhoto);
        assertThat(response.get(0).getAcademicRank()).isEqualTo(inst1AcademicRank);
        assertThat(response.get(1).getPublicId()).isEqualTo(inst2PublicId);
        assertThat(response.get(1).getFirstname()).isEqualTo(inst2Firstname);
        assertThat(response.get(1).getLastname()).isEqualTo(inst2Lastname);
        assertThat(response.get(1).getProfilePhoto()).isEqualTo(inst2ProfilePhoto);
        assertThat(response.get(1).getAcademicRank()).isEqualTo(inst2AcademicRank);
    }

}
