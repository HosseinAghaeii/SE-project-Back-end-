package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.ContactUtil;
import ir.segroup.unipoll.ws.model.entity.ContactEntity;
import ir.segroup.unipoll.ws.model.request.ContactRequest;
import ir.segroup.unipoll.ws.model.response.ContactResponse;
import ir.segroup.unipoll.ws.repository.ContactRepository;
import ir.segroup.unipoll.ws.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ir.segroup.unipoll.config.exception.constant.ExceptionMessages.DATABASE_IO_EXCEPTION;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final ContactUtil util;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public ContactServiceImpl(ContactRepository contactRepository, ContactUtil util) {
        this.contactRepository = contactRepository;
        this.util = util;
    }

    @Override
    public ResponseEntity<BaseApiResponse> createComment(ContactRequest contactRequest) {
        ContactEntity contactEntity = util.convert(contactRequest);
        ContactEntity savedEntity;
        try {
            savedEntity = contactRepository.save(contactEntity);
        }catch (Exception e){
            logger.log(Level.WARNING, e.getMessage());
            throw new SystemServiceException(DATABASE_IO_EXCEPTION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return util.createResponse(util.convert(savedEntity),HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getAllComments() {
        List<ContactResponse> contactResponse = contactRepository.findAll().stream()
                .map(util::convert)
                .toList();
        return util.createResponse(contactResponse,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getAComment(String publicId) {
        Optional<ContactEntity> commentEntity = contactRepository.findByPublicId(publicId);
        if (commentEntity.isEmpty())
            throw new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(),HttpStatus.NOT_FOUND);
        ContactResponse contactResponse = util.convert(commentEntity.get());
        return util.createResponse(contactResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> deleteComment(String publicId) {
        Optional<ContactEntity> commentEntity = contactRepository.findByPublicId(publicId);
        if (commentEntity.isEmpty())
            throw new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(),HttpStatus.NOT_FOUND);
        ContactResponse contactResponse = util.convert(commentEntity.get());

        try {
            contactRepository.delete(commentEntity.get());
        }catch (Exception exception) {
            logger.log(Level.WARNING, exception.getMessage());
            throw new SystemServiceException(DATABASE_IO_EXCEPTION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return util.createResponse(contactResponse, HttpStatus.OK);
    }


}
