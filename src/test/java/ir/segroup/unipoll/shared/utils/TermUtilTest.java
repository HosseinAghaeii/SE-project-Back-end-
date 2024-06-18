package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.BookletCommentEntity;
import ir.segroup.unipoll.ws.model.entity.BookletEntity;
import ir.segroup.unipoll.ws.model.entity.ICCommentEntity;
import ir.segroup.unipoll.ws.model.entity.TermEntity;
import ir.segroup.unipoll.ws.model.response.InstructorCourseResponse;
import ir.segroup.unipoll.ws.model.response.TermResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TermUtilTest {

    @InjectMocks
    TermUtil util;

    TermEntity termEntity = new TermEntity();

    List<ICCommentEntity> commentCEntities = new ArrayList<>();

    List<BookletCommentEntity> bookletCommentEntities = new ArrayList<>();

    List<BookletEntity> bookletEntities = new ArrayList<>();

    @BeforeEach
    void setUp() {
        termEntity = TermEntity.builder()
                .publicId("publicId")
                .name("term")
                .commentCEntities(commentCEntities)
                .bookletCommentEntities(bookletCommentEntities)
                .bookletEntities(bookletEntities)
                .build();
    }

    @Test
    void test_GiveValidTermEntity_WhenCallConvert_ThenReturnValidTermResponse(){
        //give
        //when
        TermResponse response = util.convert(termEntity);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(termEntity.getName());
    }
}