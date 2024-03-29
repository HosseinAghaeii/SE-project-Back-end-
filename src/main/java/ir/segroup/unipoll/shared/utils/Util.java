package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.shared.model.BaseApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;


abstract class Util {

    public ResponseEntity<BaseApiResponse> createResponse(Object object, HttpStatus httpStatus) {
        BaseApiResponse baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .date(new Date())
                .message("")
                .result(object)
                .build();
        return new ResponseEntity<>(baseApiResponse, httpStatus);
    }

}
