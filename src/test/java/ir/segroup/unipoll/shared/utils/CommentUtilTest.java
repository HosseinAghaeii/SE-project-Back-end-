package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.ws.model.entity.*;
import ir.segroup.unipoll.ws.model.request.BookletCommentRequest;
import ir.segroup.unipoll.ws.model.request.CommentCRequest;
import ir.segroup.unipoll.ws.model.response.CommentResponse;
import ir.segroup.unipoll.ws.repository.BookletRepository;
import ir.segroup.unipoll.ws.repository.InstructorCourseRepository;
import ir.segroup.unipoll.ws.repository.TermRepository;
import ir.segroup.unipoll.ws.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class CommentUtilTest {
    @InjectMocks
    CommentUtil util;
    @Mock
    TermRepository termRepository;
    @Mock
    InstructorCourseRepository icRepository;
    @Mock
    BookletRepository bookletRepository;
    @Mock
    UserRepository userRepository;
    CommentCRequest commentCRequest = new CommentCRequest();
    ICCommentEntity icCommentEntity = new ICCommentEntity();
    InstructorCourseEntity icEntity = new InstructorCourseEntity();
    CommentResponse commentResponse = new CommentResponse();
    TermEntity termEntity = new TermEntity();
    UserEntity userEntity = new UserEntity();
    BookletEntity bookletEntity = new BookletEntity();
    BookletCommentRequest bookletCommentRequest = new BookletCommentRequest();
    BookletCommentEntity bookletCommentEntity = new BookletCommentEntity();
    CommentUtil commentUtil = new CommentUtil(termRepository,icRepository,bookletRepository,userRepository);
    Method getWriterName ;
    Method getWriterType;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        userEntity.setRole("ADMIN");
        commentCRequest = CommentCRequest.builder()
                .text("text")
                .termPublicId("termPublicId")
                .icPublicId("icPublicId")
                .unknown(true)
                .build();
        icCommentEntity = ICCommentEntity.builder()
                .text(commentCRequest.getText())
                .icEntity(icEntity)
                .userEntity(userEntity)
                .publicId("publicId")
                .isUnknown(commentCRequest.isUnknown())
                .id(1)
                .termEntity(termEntity)
                .createdDate(new Date())
                .build();
        bookletCommentRequest = BookletCommentRequest.builder()
                .unknown(true)
                .text("text")
                .bookletPublicId("bookletPublicId")
                .termPublicId("termPublicId")
                .build();
        bookletCommentEntity = BookletCommentEntity.builder()
                .isUnknown(bookletCommentRequest.isUnknown())
                .text(bookletCommentRequest.getText())
                .id(1)
                .bookletEntity(bookletEntity)
                .publicId("publicId")
                .termEntity(termEntity)
                .userEntity(userEntity)
                .createdDate(new Date())
                .build();
        getWriterName = commentUtil.getClass().getDeclaredMethod("getWriterName", boolean.class, String.class, String.class);
        getWriterName.setAccessible(true);
        getWriterType = commentUtil.getClass().getDeclaredMethod("getWriterType", String.class);
        getWriterType.setAccessible(true);
    }

    @Test
    void test_GiveCommentCRequestWithInvalidIcPublicId_WhenCallConvert_ThenThrowException() {
        //give
        when(icRepository.findByPublicId(any())).thenReturn(Optional.empty());
        //then
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class,() ->
                //when
        util.convert(commentCRequest,"Admin"));
        assertThat(systemServiceException.getException()).isEqualTo(ExceptionMessages.NO_RECORD_FOUND.getMessage());
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void test_GiveCommentCRequestWithInvalidTermPublicId_WhenCallConvert_ThenThrowException() {
        //give
        when(icRepository.findByPublicId(any())).thenReturn(Optional.of(icEntity));
        when(termRepository.findByPublicId(any())).thenReturn(Optional.empty());
        //then
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class,() ->
                //when
                util.convert(commentCRequest,"Admin"));
        assertThat(systemServiceException.getException()).isEqualTo(ExceptionMessages.NO_RECORD_FOUND.getMessage());
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void test_GiveCommentCRequestWithInvalidUsername_WhenCallConvert_ThenThrowException() {
        //give
        when(icRepository.findByPublicId(any())).thenReturn(Optional.of(icEntity));
        when(termRepository.findByPublicId(any())).thenReturn(Optional.of(termEntity));
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        //then
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class,() ->
                //when
                util.convert(commentCRequest,"Admin"));
        assertThat(systemServiceException.getException()).isEqualTo(ExceptionMessages.NO_RECORD_FOUND.getMessage());
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    void test_GiveValidCommentCRequest_WhenCallConvert_ThenReturnICCommentEntity() {
        //give
        when(icRepository.findByPublicId(any())).thenReturn(Optional.of(icEntity));
        when(termRepository.findByPublicId(any())).thenReturn(Optional.of(termEntity));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(userEntity));
        //when
        ICCommentEntity response = util.convert(commentCRequest,"Admin");
        //then
        assertThat(response.isUnknown()).isEqualTo(commentCRequest.isUnknown());
        assertThat(response.getText()).isEqualTo(commentCRequest.getText());
        assertThat(response.getTermEntity()).isEqualTo(termEntity);
    }

    //==================================================================================================================

    @Test
    void test_GiveBookletCommentRequestWithInvalidBookletPublicId_WhenCallConvert_ThenThrowException() {
        //give
        when(bookletRepository.findByPublicId(any())).thenReturn(Optional.empty());
        //then
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class,() ->
                //when
                util.convert(bookletCommentRequest,"Admin"));
        assertThat(systemServiceException.getException()).isEqualTo(ExceptionMessages.NO_RECORD_FOUND.getMessage());
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void test_GiveBookletCommentRequestWithInvalidTermPublicId_WhenCallConvert_ThenThrowException() {
        //give
        when(bookletRepository.findByPublicId(any())).thenReturn(Optional.of(bookletEntity));
        when(termRepository.findByPublicId(any())).thenReturn(Optional.empty());
        //then
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class,() ->
                //when
                util.convert(bookletCommentRequest,"Admin"));
        assertThat(systemServiceException.getException()).isEqualTo(ExceptionMessages.NO_RECORD_FOUND.getMessage());
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void test_GiveBookletCommentRequestWithInvalidUsername_WhenCallConvert_ThenThrowException() {
        //give
        when(bookletRepository.findByPublicId(any())).thenReturn(Optional.of(bookletEntity));
        when(termRepository.findByPublicId(any())).thenReturn(Optional.of(termEntity));
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        //then
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class,() ->
                //when
                util.convert(bookletCommentRequest,"Admin"));
        assertThat(systemServiceException.getException()).isEqualTo(ExceptionMessages.NO_RECORD_FOUND.getMessage());
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    void test_GiveValidBookletCommentRequest_WhenCallConvert_ThenReturnBookletCommentEntity() {
        //give
        when(bookletRepository.findByPublicId(any())).thenReturn(Optional.of(bookletEntity));
        when(termRepository.findByPublicId(any())).thenReturn(Optional.of(termEntity));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(userEntity));
        //when
        BookletCommentEntity response = util.convert(bookletCommentRequest,"Admin");
        //then
        assertThat(response.isUnknown()).isEqualTo(bookletCommentRequest.isUnknown());
        assertThat(response.getText()).isEqualTo(bookletCommentRequest.getText());
        assertThat(response.getTermEntity()).isEqualTo(termEntity);
        assertThat(response.getUserEntity()).isEqualTo(userEntity);
        assertThat(response.getBookletEntity()).isEqualTo(bookletEntity);
    }
    //==================================================================================================================

    @Test
    void test_GiveValidICCommentEntity_WhenCallConvert_ThenReturnValidCommentResponse(){
        //give
        //when
        CommentResponse response = util.convert(icCommentEntity);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getText()).isEqualTo(icCommentEntity.getText());
    }
    //==================================================================================================================
    @Test
    void test_GiveValidBookletCommentEntity_WhenCallConvert_ThenReturnValidCommentResponse(){
        //give
        //when
        CommentResponse response = util.convert(bookletCommentEntity);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getText()).isEqualTo(bookletCommentEntity.getText());
    }
    //==================================================================================================================
    @Test
    void test_GiveIsUnknownTrue_WhenCallGetWriterName_ThenReturnUnknown() throws  InvocationTargetException, IllegalAccessException {
        //give;
        //when
        String response = (String) getWriterName.invoke(commentUtil, true, "حسین", "آقایی");
        //then
        assertThat(response).isEqualTo("ناشناس");
    }
    @Test
    void test_GiveIsUnknownFalse_WhenCallGetWriterName_ThenReturnFirstnameAndLastname() throws  InvocationTargetException, IllegalAccessException {
        //give;
        //when
        String response = (String) getWriterName.invoke(commentUtil, false, "حسین", "آقایی");
        //then
        assertThat(response).isEqualTo("حسین آقایی");
    }
    //==================================================================================================================
    @Test
    void test_GiveADMINRole_WhenCallGetWriterType_ThenReturnPersianAdmin() throws  InvocationTargetException, IllegalAccessException {
        //give;
        //when
        String response = (String) getWriterType.invoke(commentUtil,"ADMIN");
        //then
        assertThat(response).isEqualTo("ادمین");
    }
    @Test
    void test_GiveSTUDENTRole_WhenCallGetWriterType_ThenReturnPersianSTUDENT() throws  InvocationTargetException, IllegalAccessException {
        //give;
        //when
        String response = (String) getWriterType.invoke(commentUtil,"STUDENT");
        //then
        assertThat(response).isEqualTo("دانشجو");
    }
    @Test
    void test_GiveINSTRUCTORRole_WhenCallGetWriterType_ThenReturnPersianINSTRUCTOR() throws  InvocationTargetException, IllegalAccessException {
        //give;
        //when
        String response = (String) getWriterType.invoke(commentUtil,"INSTRUCTOR");
        //then
        assertThat(response).isEqualTo("استاد");
    }
    @Test
    void test_GiveInvalidRole_WhenCallGetWriterType_ThenThrowException()  {
        //give;
        //then
        assertThrows(Exception.class,() ->
                //when
                 getWriterType.invoke(commentUtil,"InvalidRole")
                );
    }
}