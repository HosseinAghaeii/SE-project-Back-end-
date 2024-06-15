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
    @InjectMocks
    private BookletServiceImpl bookletService;

    private String username;
    private UserEntity userEntity;
    private String publicId;
    private String firstname;
    private String lastname;
    private String password;
    private String role;

    @BeforeEach
    void setUp() {
        publicId = UUID.randomUUID().toString().replace("-", "");
        username = "username";
        firstname = "Maryam";
        lastname = "Hosseinpour";
        password = "password";
        role = "student";
        userEntity = UserEntity.builder()
                .publicId(publicId)
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .encryptedPassword(password)
                .role(role)
                .build();
    }

    @Test
    void testGetFavoriteBooklets_GivenInValidUsername_WhenCallGetFavoriteBooklets_ThenShouldReturnSystemServiceException(){
        //given
        when(bookletUtil.getUsernameFromToken(any(String.class))).thenReturn(username);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        //then
            //when
        SystemServiceException systemServiceException = assertThrows(SystemServiceException.class,() ->
                bookletService.getFavoriteBooklets(username));
        assertThat(systemServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(systemServiceException.getException()).isEqualTo(ExceptionMessages.NO_RECORD_FOUND.getMessage());
    }

    @Test
    void testGetFavoriteBooklets_GivenValidToken_WhenCallGetFavoriteBooklets_ThenShouldReturnResponse(){
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
        when(bookletUtil.convert(any(BookletEntity.class),any(String.class))).thenReturn(bookletResponse);
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