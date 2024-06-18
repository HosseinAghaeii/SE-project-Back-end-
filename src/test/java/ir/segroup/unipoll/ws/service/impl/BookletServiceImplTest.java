package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.BookletUtil;
import ir.segroup.unipoll.ws.model.entity.BookletEntity;
import ir.segroup.unipoll.ws.model.entity.UserEntity;
import ir.segroup.unipoll.ws.model.response.BookletResponse;

import ir.segroup.unipoll.ws.model.response.DepartmentInstructorResponse;
import ir.segroup.unipoll.ws.repository.UserRepository;
import jakarta.persistence.ManyToOne;

import ir.segroup.unipoll.ws.repository.BookletRepository;
import ir.segroup.unipoll.ws.repository.UserRepository;

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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookletServiceImplTest {
    @Mock
    private BookletUtil bookletUtil;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookletRepository bookletRepository;
    @InjectMocks
    private BookletServiceImpl bookletService;

    private String username;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        String publicId = UUID.randomUUID().toString().replace("-", "");
        username = "username";
        String firstname = "Maryam";
        String lastname = "Hosseinpour";
        String password = "password";
        String role = "student";

        userEntity = new UserEntity();
        userEntity.setPublicId(publicId);
        userEntity.setFirstname(firstname);
        userEntity.setLastname(lastname);
        userEntity.setEncryptedPassword(password);
        userEntity.setRole(role);
    }

    @Test
    void testGetFavoriteBooklets_GivenInValidUsername_WhenCallGetFavoriteBooklets_ThenShouldReturnSystemServiceException() {
        //given
        when(bookletUtil.getUsernameFromToken(any(String.class))).thenReturn(username);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        //then
       
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class, () ->
                 //when
                bookletService.getFavoriteBooklets(username));
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(systemServiceException.getException()).isEqualTo(ExceptionMessages.NO_RECORD_FOUND.getMessage());
    }

    @Test
    void testGetTenTopBooklets_GivenValidToken_WhenCallGetTenTopBooklets_ThenShouldReturnResponse() {
        // Mock data
        String token = "dummyToken";
        String username = "testUser";

        List<BookletEntity> bookletList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            BookletEntity booklet = new BookletEntity();
            booklet.setPublicId("booklet" + i);
            List<UserEntity> likes = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                likes.add(new UserEntity());
            }
            booklet.setLikes(likes);
            bookletList.add(booklet);
        }

        List<BookletResponse> bookletResponseList = new ArrayList<>();
        for (BookletEntity bookletEntity : bookletList) {
            bookletResponseList.add(BookletResponse.builder()
                    .publicId(bookletEntity.getPublicId())
                    .build());
        }
        BaseApiResponse baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .message("")
                .date(new Date())
                .result(bookletResponseList)
                .build();

        ResponseEntity<BaseApiResponse> responseOk = new ResponseEntity<>(baseApiResponse, HttpStatus.OK);

        //given
        when(bookletUtil.getUsernameFromToken(any())).thenReturn(username);
        when(bookletRepository.findAll()).thenReturn(bookletList);
        when(bookletUtil.createResponse(any(), any(HttpStatus.class))).thenReturn(responseOk);
        //when
        ResponseEntity<BaseApiResponse> tenTopBooklet = bookletService.getTenTopBooklets("token");
        //then
        assertThat(tenTopBooklet).isNotNull();
        assertThat(tenTopBooklet.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<BookletResponse> response = (List<BookletResponse>) tenTopBooklet.getBody().getResult();
        assertThat(response.size()).isEqualTo(10);
    }

    @Test
    void testGetFavoriteBooklets_GivenValidToken_WhenCallGetFavoriteBooklets_ThenShouldReturnResponse() {
        String bookletPublicId = UUID.randomUUID().toString().replace("-", "");
        BookletEntity bookletEntity = BookletEntity.builder()
                .publicId(bookletPublicId)
                .build();
        userEntity.setFavoriteBooklets(List.of(bookletEntity));

        BookletResponse bookletResponse = BookletResponse.builder()
                .publicId(bookletPublicId)
                .build();
        List<BookletResponse> bookletResponseList = new ArrayList<>();
        bookletResponseList.add(bookletResponse);

        BaseApiResponse baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .message("")
                .date(new Date())
                .result(bookletResponseList)
                .build();

        ResponseEntity<BaseApiResponse> responseOk = new ResponseEntity<>(baseApiResponse, HttpStatus.OK);

        //given
        when(bookletUtil.getUsernameFromToken(any(String.class))).thenReturn(username);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.ofNullable(userEntity));

        when(bookletUtil.convert(any(BookletEntity.class), any(String.class))).thenReturn(bookletResponse);

        when(bookletUtil.createResponse(any(), any(HttpStatus.class))).thenReturn(responseOk);
        //when
        ResponseEntity<BaseApiResponse> favoriteBooklet = bookletService.getFavoriteBooklets("token");
        //then
        assertThat(favoriteBooklet).isNotNull();
        assertThat(favoriteBooklet.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<BookletResponse> response = (List<BookletResponse>) favoriteBooklet.getBody().getResult();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getPublicId()).isEqualTo(bookletPublicId);
    }


}