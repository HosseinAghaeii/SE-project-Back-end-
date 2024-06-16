package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.CommentUtil;
import ir.segroup.unipoll.shared.utils.Util;
import ir.segroup.unipoll.ws.model.entity.BookletCommentEntity;
import ir.segroup.unipoll.ws.model.entity.BookletEntity;
import ir.segroup.unipoll.ws.model.request.BookletCommentRequest;
import ir.segroup.unipoll.ws.model.response.CommentResponse;
import ir.segroup.unipoll.ws.repository.BookletCommentRepository;
import ir.segroup.unipoll.ws.repository.BookletRepository;
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


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookletCommentServiceImplTest {

    @InjectMocks
    BookletCommentServiceImpl bookletCommentService;
    @Mock
    BookletCommentRepository bookletCommentRepository;
    @Mock
    BookletRepository bookletRepository;
    @Mock
    CommentUtil util;
    BookletCommentEntity bookletCommentEntity = new BookletCommentEntity();

    BaseApiResponse baseApiResponse;
    ResponseEntity<BaseApiResponse> response;
    String token = "random token";
    String username = "Admin";

    @BeforeEach
    void setUp() {
        bookletCommentEntity = BookletCommentEntity.builder()
                .text("Hi.This is test comment")
                .build();
    }

    @Test
    void test_GiveTrueBookletRequestWithIsUnKnownIsTrue_WhenCallCreateComment_ThenReturnTrueResponseEntityWithHiddenWriterName() {
        //Given
        BookletCommentRequest bookletCommentRequest = BookletCommentRequest.builder()
                .termPublicId("termPublicId")
                .bookletPublicId("bookletPublicId")
                .text("Hi.This is test comment")
                .unknown(true)
                .build();

        CommentResponse commentResponse = CommentResponse.builder()
                .text("Hi.This is test comment")
                .writerName("unknown")
                .build();
        baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .date(new Date())
                .message("")
                .result(commentResponse)
                .build();
        response = new ResponseEntity<>(baseApiResponse, HttpStatus.OK);

        when(util.getUsernameFromToken(any())).thenReturn(username);
        when(util.convert(any(BookletCommentRequest.class), eq(username))).thenReturn(bookletCommentEntity);
        when(bookletCommentRepository.save(bookletCommentEntity)).thenReturn(bookletCommentEntity);
        when(util.convert(any(BookletCommentEntity.class))).thenReturn(commentResponse);
        when(util.createResponse(any(), any())).thenReturn(response);
        //When
        ResponseEntity<BaseApiResponse> realResponse = bookletCommentService.createComment(bookletCommentRequest, token);
        BaseApiResponse realBaseResponse = realResponse.getBody();
        CommentResponse realCommentResponse = (CommentResponse) realBaseResponse.getResult();
        //Then
        assertThat(realBaseResponse.getResult()).isNotNull();
        assertThat(realCommentResponse.getText()).isEqualTo(bookletCommentRequest.getText());
        assertThat(realCommentResponse.getWriterName()).isEqualTo("unknown");
    }

    @Test
    void test_GiveTrueBookletRequestWithIsUnKnownIsFalse_WhenCallCreateComment_ThenReturnTrueResponseWithWriterName() {
        //Given
        BookletCommentRequest bookletCommentRequest = BookletCommentRequest.builder()
                .termPublicId("termPublicId")
                .bookletPublicId("bookletPublicId")
                .text("Hi.This is test comment")
                .unknown(false)
                .build();

        CommentResponse commentResponse = CommentResponse.builder()
                .text("Hi.This is test comment")
                .writerName("Admin")
                .build();
        baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .date(new Date())
                .message("")
                .result(commentResponse)
                .build();
        response = new ResponseEntity<>(baseApiResponse, HttpStatus.OK);

        when(util.getUsernameFromToken(any())).thenReturn(username);
        when(util.convert(any(BookletCommentRequest.class), eq(username))).thenReturn(bookletCommentEntity);
        when(bookletCommentRepository.save(bookletCommentEntity)).thenReturn(bookletCommentEntity);
        when(util.convert(any(BookletCommentEntity.class))).thenReturn(commentResponse);
        when(util.createResponse(any(), any())).thenReturn(response);
        //When
        ResponseEntity<BaseApiResponse> realResponse = bookletCommentService.createComment(bookletCommentRequest, token);
        BaseApiResponse realBaseResponse = realResponse.getBody();
        CommentResponse realCommentResponse = (CommentResponse) realBaseResponse.getResult();
        //Then
        assertThat(realBaseResponse.getResult()).isNotNull();
        assertThat(realCommentResponse.getText()).isEqualTo(bookletCommentRequest.getText());
        assertThat(realCommentResponse.getWriterName()).isEqualTo("Admin");
    }

    @Test
    void testCallCreateComment_GiveTrueBookletRequest_WhenJpaFailedToSave_ThenThrowException() {
        //Given
        BookletCommentRequest bookletCommentRequest = BookletCommentRequest.builder()
                .termPublicId("termPublicId")
                .bookletPublicId("bookletPublicId")
                .text("Hi.This is test comment")
                .unknown(true)
                .build();

        when(util.getUsernameFromToken(any())).thenReturn(username);
        when(util.convert(any(BookletCommentRequest.class), eq(username))).thenReturn(bookletCommentEntity);
        doThrow(SystemServiceException.class).when(bookletCommentRepository).save(any());
        //Then
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class, () ->
                //when
                bookletCommentService.createComment(bookletCommentRequest, token));
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Test
    void test_GiveExistPublicIdAndFilterTopFiveIsTrue_WhenCallGetABookletComments_ThenReturnTopFiveComment() {
        //Given
        String text1 = "comment1";
        String text2 = "comment2";
        String text3 = "comment3";
        String text4 = "comment4";
        String text5 = "comment5";
        String text6 = "comment6";
        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();
        Date date4 = new Date();
        Date date5 = new Date();
        Date date6 = new Date();
        Util tempUtil = new Util();
        String jalaliDate1 = tempUtil.getJalaliDate(date1);
        String jalaliDate2 = tempUtil.getJalaliDate(date2);
        String jalaliDate3 = tempUtil.getJalaliDate(date3);
        String jalaliDate4 = tempUtil.getJalaliDate(date4);
        String jalaliDate5 = tempUtil.getJalaliDate(date5);
        String jalaliDate6 = tempUtil.getJalaliDate(date6);
        BookletEntity bookletEntity = BookletEntity.builder().publicId("p1").build();
        BookletCommentEntity bookletComment1 = BookletCommentEntity.builder()
                .bookletEntity(bookletEntity)
                .text(text1)
                .createdDate(date1)
                .build();
        BookletCommentEntity bookletComment2 = BookletCommentEntity.builder()
                .bookletEntity(bookletEntity)
                .text(text2)
                .createdDate(date2)
                .build();
        BookletCommentEntity bookletComment3 = BookletCommentEntity.builder()
                .bookletEntity(bookletEntity)
                .text(text3)
                .createdDate(date3)
                .build();
        BookletCommentEntity bookletComment4 = BookletCommentEntity.builder()
                .bookletEntity(bookletEntity)
                .text(text4)
                .createdDate(date4)
                .build();
        BookletCommentEntity bookletComment5 = BookletCommentEntity.builder()
                .bookletEntity(bookletEntity)
                .text(text5)
                .createdDate(date5)
                .build();
        BookletCommentEntity bookletComment6 = BookletCommentEntity.builder()
                .bookletEntity(bookletEntity)
                .text(text6)
                .createdDate(date6)
                .build();
        CommentResponse commentResponse1 = CommentResponse.builder()
                .text(text1)
                .createdDate(jalaliDate1)
                .build();
        CommentResponse commentResponse2 = CommentResponse.builder()
                .text(text2)
                .createdDate(jalaliDate2)
                .build();
        CommentResponse commentResponse3 = CommentResponse.builder()
                .text(text3)
                .createdDate(jalaliDate3)
                .build();
        CommentResponse commentResponse4 = CommentResponse.builder()
                .text(text4)
                .createdDate(jalaliDate4)
                .build();
        CommentResponse commentResponse5 = CommentResponse.builder()
                .text(text5)
                .createdDate(jalaliDate5)
                .build();
        CommentResponse commentResponse6 = CommentResponse.builder()
                .text(text6)
                .createdDate(jalaliDate6)
                .build();
        List<BookletCommentEntity> bookletCommentEntities = List.of(bookletComment1, bookletComment2, bookletComment3, bookletComment4, bookletComment5, bookletComment6);
        Optional<BookletEntity> bookletEntity1 = Optional.of(BookletEntity.builder()
                .commentBEntities(bookletCommentEntities)
                .build());
        List<CommentResponse> commentResponses = List.of(commentResponse5,commentResponse4,commentResponse3,commentResponse2,commentResponse1);
        baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .date(new Date())
                .message("")
                .result(commentResponses)
                .build();
        response = new ResponseEntity<>(baseApiResponse, HttpStatus.OK);

        when(bookletRepository.findByPublicId(any())).thenReturn(bookletEntity1);
        when(bookletCommentRepository.findAll()).thenReturn(bookletCommentEntities);
//        when(util.convert(eq(bookletComment1))).thenReturn(commentResponse1);
//        when(util.convert(eq(bookletComment2))).thenReturn(commentResponse2);
//        when(util.convert(eq(bookletComment3))).thenReturn(commentResponse3);
//        when(util.convert(eq(bookletComment4))).thenReturn(commentResponse4);
//        when(util.convert(eq(bookletComment5))).thenReturn(commentResponse5);
//        when(util.convert(eq(bookletComment6))).thenReturn(commentResponse6);
        when(util.createResponse(any(), any())).thenReturn(response);
        //when
        ResponseEntity<BaseApiResponse> realResponse = bookletCommentService.getABookletComments("randomPublicId", true);
        List<CommentResponse> realCommentResponses = (List<CommentResponse>) realResponse.getBody().getResult();
        //then
        assertThat(realCommentResponses).isNotNull();
        assertThat(realCommentResponses.size()).isEqualTo(5);
        assertThat(realCommentResponses.get(0).getText()).isEqualTo(text5);
    }

    @Test
    void test_GiveExistPublicIdAndFilterTopFiveIsFalse_WhenCallGetABookletComments_ThenReturnAllComment() {
        //Given
        String text1 = "comment1";
        String text2 = "comment2";
        String text3 = "comment3";
        String text4 = "comment4";
        String text5 = "comment5";
        String text6 = "comment6";
        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();
        Date date4 = new Date();
        Date date5 = new Date();
        Date date6 = new Date();
        Util tempUtil = new Util();
        String jalaliDate1 = tempUtil.getJalaliDate(date1);
        String jalaliDate2 = tempUtil.getJalaliDate(date2);
        String jalaliDate3 = tempUtil.getJalaliDate(date3);
        String jalaliDate4 = tempUtil.getJalaliDate(date4);
        String jalaliDate5 = tempUtil.getJalaliDate(date5);
        String jalaliDate6 = tempUtil.getJalaliDate(date6);
        BookletEntity bookletEntity = BookletEntity.builder().publicId("p1").build();
        BookletCommentEntity bookletComment1 = BookletCommentEntity.builder()
                .bookletEntity(bookletEntity)
                .text(text1)
                .createdDate(date1)
                .build();
        BookletCommentEntity bookletComment2 = BookletCommentEntity.builder()
                .bookletEntity(bookletEntity)
                .text(text2)
                .createdDate(date2)
                .build();
        BookletCommentEntity bookletComment3 = BookletCommentEntity.builder()
                .bookletEntity(bookletEntity)
                .text(text3)
                .createdDate(date3)
                .build();
        BookletCommentEntity bookletComment4 = BookletCommentEntity.builder()
                .bookletEntity(bookletEntity)
                .text(text4)
                .createdDate(date4)
                .build();
        BookletCommentEntity bookletComment5 = BookletCommentEntity.builder()
                .bookletEntity(bookletEntity)
                .text(text5)
                .createdDate(date5)
                .build();
        BookletCommentEntity bookletComment6 = BookletCommentEntity.builder()
                .bookletEntity(bookletEntity)
                .text(text6)
                .createdDate(date6)
                .build();
        CommentResponse commentResponse1 = CommentResponse.builder()
                .text(text1)
                .createdDate(jalaliDate1)
                .build();
        CommentResponse commentResponse2 = CommentResponse.builder()
                .text(text2)
                .createdDate(jalaliDate2)
                .build();
        CommentResponse commentResponse3 = CommentResponse.builder()
                .text(text3)
                .createdDate(jalaliDate3)
                .build();
        CommentResponse commentResponse4 = CommentResponse.builder()
                .text(text4)
                .createdDate(jalaliDate4)
                .build();
        CommentResponse commentResponse5 = CommentResponse.builder()
                .text(text5)
                .createdDate(jalaliDate5)
                .build();
        CommentResponse commentResponse6 = CommentResponse.builder()
                .text(text6)
                .createdDate(jalaliDate6)
                .build();
        List<BookletCommentEntity> bookletCommentEntities = List.of(bookletComment1, bookletComment2, bookletComment3, bookletComment4, bookletComment5, bookletComment6);
        Optional<BookletEntity> bookletEntity1 = Optional.of(BookletEntity.builder()
                .commentBEntities(bookletCommentEntities)
                .build());
        List<CommentResponse> commentResponses = List.of(commentResponse6,commentResponse5,commentResponse4,commentResponse3,commentResponse2,commentResponse1);
        baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .date(new Date())
                .message("")
                .result(commentResponses)
                .build();
        response = new ResponseEntity<>(baseApiResponse, HttpStatus.OK);

        when(bookletRepository.findByPublicId(any())).thenReturn(bookletEntity1);
        when(bookletCommentRepository.findAll()).thenReturn(bookletCommentEntities);
//        when(util.convert(eq(bookletComment1))).thenReturn(commentResponse1);
//        when(util.convert(eq(bookletComment2))).thenReturn(commentResponse2);
//        when(util.convert(eq(bookletComment3))).thenReturn(commentResponse3);
//        when(util.convert(eq(bookletComment4))).thenReturn(commentResponse4);
//        when(util.convert(eq(bookletComment5))).thenReturn(commentResponse5);
//        when(util.convert(eq(bookletComment6))).thenReturn(commentResponse6);
        when(util.createResponse(any(), any())).thenReturn(response);
        //when
        ResponseEntity<BaseApiResponse> realResponse = bookletCommentService.getABookletComments("randomPublicId", false);
        List<CommentResponse> realCommentResponses = (List<CommentResponse>) realResponse.getBody().getResult();
        //then
        assertThat(realCommentResponses).isNotNull();
        assertThat(realCommentResponses.size()).isEqualTo(6);
        assertThat(realCommentResponses.get(0).getText()).isEqualTo(text6);
    }

    @Test
    void test_GiveNotExistPublicId_WhenCallGetABookletComments_ThenThrowException() {
        //Given
        when(bookletRepository.findByPublicId(any())).thenReturn(Optional.empty());
        //then
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class, () ->
                //when
                bookletCommentService.getABookletComments("randomPublicId", true));
        assertThat(systemServiceException.getException()).isEqualTo(ExceptionMessages.NO_RECORD_FOUND.getMessage());
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}