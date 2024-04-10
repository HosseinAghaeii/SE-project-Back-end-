package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.ContactEntity;
import ir.segroup.unipoll.ws.model.request.ContactRequest;
import ir.segroup.unipoll.ws.model.response.ContactResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ContactUtil extends Util{

    private final ModelMapper modelMapper;

    public ContactUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ContactEntity convert(ContactRequest contactRequest){
        ContactEntity result = modelMapper.map(contactRequest, ContactEntity.class);
        result.setPublicId(UUID.randomUUID().toString());
        return result;
    }

    public ContactResponse convert(ContactEntity contactEntity){
        return modelMapper.map(contactEntity, ContactResponse.class);
    }
}
