package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.AcademicDepartmentEntity;
import ir.segroup.unipoll.ws.model.entity.CollegeEntity;
import ir.segroup.unipoll.ws.model.entity.ContactEntity;
import ir.segroup.unipoll.ws.model.request.CollegeRequest;
import ir.segroup.unipoll.ws.model.request.ContactRequest;
import ir.segroup.unipoll.ws.model.response.CollegeResponse;
import ir.segroup.unipoll.ws.model.response.ContactResponse;
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
class ContactUtilTest {

    ModelMapper modelMapper = new ModelMapper();

    ContactUtil util = new ContactUtil(modelMapper);

    ContactEntity contactEntity = new ContactEntity();

    ContactRequest contactRequest = new ContactRequest();

    @BeforeEach
    void setUp() {
        contactEntity = ContactEntity.builder()
                .id(1)
                .publicId("publicId")
                .firstname("fn")
                .lastname("ln")
                .email("xxx.com")
                .text("hello world")
                .build();

        contactRequest = ContactRequest.builder()
                .firstname("fn")
                .lastname("ln")
                .email("xxx.com")
                .text("hello world")
                .build();
    }

    @Test
    void test_GiveValidContactRequest_WhenCallConvert_ThenReturnValidContactEntity(){
        //give
        //when
        ContactEntity response = util.convert(contactRequest);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getFirstname()).isEqualTo(contactRequest.getFirstname());
        assertThat(response.getLastname()).isEqualTo(contactRequest.getLastname());
    }

    @Test
    void test_GiveValidContactEntity_WhenCallConvert_ThenReturnValidContactResponse(){
        //give
        //when
        ContactResponse response = util.convert(contactEntity);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getFirstname()).isEqualTo(contactEntity.getFirstname());
        assertThat(response.getLastname()).isEqualTo(contactEntity.getLastname());
    }
}