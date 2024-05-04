package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.ws.model.entity.CommentCEntity;
import ir.segroup.unipoll.ws.model.request.CommentCRequest;
import ir.segroup.unipoll.ws.model.response.CommentCResponse;
import ir.segroup.unipoll.ws.repository.InstructorCourseRepository;
import ir.segroup.unipoll.ws.repository.TermRepository;
import ir.segroup.unipoll.ws.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommentCUtil extends Util{
    private final TermRepository termRepository;
    private final InstructorCourseRepository icRepository;
    private final UserRepository userRepository;

    public CommentCUtil(TermRepository termRepository, InstructorCourseRepository icRepository, UserRepository userRepository) {
        this.termRepository = termRepository;
        this.icRepository = icRepository;
        this.userRepository = userRepository;
    }

    public CommentCEntity convert(CommentCRequest request,String username){
        return CommentCEntity.builder()
                .text(request.getText())
                .icEntity(icRepository.findByPublicId(request.getIcPublicId()).orElseThrow(() ->
                        new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND)))
                .createdDate(getJalaliDate())
                .termEntity(termRepository.findByPublicId(request.getTermPublicId()).orElseThrow(() ->
                        new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND)))
                .userEntity(userRepository.findByUsername(username).orElseThrow(() ->
                        new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND)))
                .publicId(UUID.randomUUID().toString())
                .build();
    }

    public CommentCResponse convert(CommentCEntity entity){
        return CommentCResponse.builder()
                .writerName(entity.getUserEntity().getFirstname()+" "+entity.getUserEntity().getLastname())
                .text(entity.getText())
                .createdDate(entity.getCreatedDate())
                .publicId(entity.getPublicId())
                .term(entity.getTermEntity().getName())
                .build();
    }
}
