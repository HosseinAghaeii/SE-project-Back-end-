package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.TermEntity;
import ir.segroup.unipoll.ws.model.response.TermResponse;
import org.springframework.stereotype.Component;

@Component
public class TermUtil extends Util{

    public TermResponse convert(TermEntity termEntity) {
        return TermResponse.builder()
                .name(termEntity.getName())
                .publicId(termEntity.getPublicId())
                .build();
    }
}
