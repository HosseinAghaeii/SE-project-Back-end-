package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.RateUtil;
import ir.segroup.unipoll.ws.model.entity.*;
import ir.segroup.unipoll.ws.model.response.ARateResponse;
import ir.segroup.unipoll.ws.model.response.BookletResponse;
import ir.segroup.unipoll.ws.model.response.DepartmentResponse;
import ir.segroup.unipoll.ws.repository.InstructorCourseRepository;
import ir.segroup.unipoll.ws.repository.RateRepository;
import ir.segroup.unipoll.ws.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateServiceImplTest {

    @Mock
    private RateUtil rateUtil;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private InstructorCourseRepository instructorCourseRepository;
    @Mock
    private RateRepository rateRepository;
    @InjectMocks
    private RateServiceImpl rateService;

    private String publicId;
    private String token;
    private String username;
    private StudentEntity studentEntity;
    private InstructorCourseEntity instructorCourseEntity;
    private RateEntity rateEntity;

    @BeforeEach
    void setUp() {
        publicId = UUID.randomUUID().toString().replace("-", "");
        token = "token";
        username = "username";
        studentEntity = new StudentEntity();
        instructorCourseEntity = new InstructorCourseEntity();
    }

    @Test
    void testGetARate_GivenInValidToken_WhenCallGetARate_ThenShouldReturnSystemServiceException(){
        //given
        when(rateUtil.getUsernameFromToken(any(String.class))).thenReturn(null);
        //then
            //when
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class,() ->
                rateService.getARate("someInvalidToken",publicId));
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(systemServiceException.getException()).isEqualTo(ExceptionMessages.NO_RECORD_FOUND.getMessage());
    }

    @Test
    void testGetARate_GivenValidTokenAndPublicId_WhenCallGetARate_ThenShouldReturnRateResponse(){
        double rate = 2.5;
        rateEntity = RateEntity.builder()
                .number(rate)
                .build();
        ARateResponse rateResponse = ARateResponse.builder()
                .rate(rate)
                .build();
        BaseApiResponse baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .message("")
                .date(new Date())
                .result(rateResponse)
                .build();

        ResponseEntity<BaseApiResponse> responseOk = new ResponseEntity<>(baseApiResponse, HttpStatus.OK);
        //given
        when(rateUtil.getUsernameFromToken(any(String.class))).thenReturn(username);
        when(studentRepository.findByUsername(any(String.class))).thenReturn(Optional.ofNullable(studentEntity));
        when(instructorCourseRepository.findByPublicId(any(String.class))).thenReturn(Optional.ofNullable(instructorCourseEntity));
        when(rateRepository.findByStudentEntityAndInstructorCourseEntity(any(StudentEntity.class),any(InstructorCourseEntity.class))).thenReturn(Optional.ofNullable(rateEntity));
        when(rateUtil.convert(any(RateEntity.class))).thenReturn(rateResponse);
        when(rateUtil.createResponse(any(), any(HttpStatus.class))).thenReturn(responseOk);
        //when
        ResponseEntity<BaseApiResponse> getRate = rateService.getARate(token, publicId);
        //then
        assertThat(getRate).isNotNull();
        assertThat(getRate.getStatusCode()).isEqualTo(HttpStatus.OK);

        ARateResponse response = (ARateResponse) getRate.getBody().getResult();
        assertThat(response.getRate()).isEqualTo(rate);
    }

    @Test
    void testGetARate_GivenNotExistStudentOrInstructorCourse_WhenCallGetARate_ThenShouldReturnSystemServiceException(){
        //given
        when(rateUtil.getUsernameFromToken(any(String.class))).thenReturn(username);
        when(studentRepository.findByUsername(any(String.class))).thenReturn(Optional.ofNullable(studentEntity));
        when(instructorCourseRepository.findByPublicId(any(String.class))).thenReturn(Optional.ofNullable(instructorCourseEntity));
        when(rateRepository.findByStudentEntityAndInstructorCourseEntity(any(StudentEntity.class),any(InstructorCourseEntity.class))).thenReturn(Optional.empty());
        //then
            //when
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class,() ->
                rateService.getARate(token, publicId));
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(systemServiceException.getException()).isEqualTo(ExceptionMessages.NO_RECORD_FOUND.getMessage());
    }
}