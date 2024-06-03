package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.ws.model.entity.BookletCommentEntity;
import ir.segroup.unipoll.ws.model.entity.ICCommentEntity;
import ir.segroup.unipoll.ws.model.request.BookletCommentRequest;
import ir.segroup.unipoll.ws.model.request.CommentCRequest;
import ir.segroup.unipoll.ws.model.response.CommentResponse;
import ir.segroup.unipoll.ws.repository.BookletRepository;
import ir.segroup.unipoll.ws.repository.InstructorCourseRepository;
import ir.segroup.unipoll.ws.repository.TermRepository;
import ir.segroup.unipoll.ws.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CommentUtil extends Util {
    private final TermRepository termRepository;
    private final InstructorCourseRepository icRepository;
    private final BookletRepository bookletRepository;
    private final UserRepository userRepository;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public CommentUtil(TermRepository termRepository, InstructorCourseRepository icRepository, BookletRepository bookletRepository, UserRepository userRepository) {
        this.termRepository = termRepository;
        this.icRepository = icRepository;
        this.bookletRepository = bookletRepository;
        this.userRepository = userRepository;
    }

    public ICCommentEntity convert(CommentCRequest request, String username) {
        return ICCommentEntity.builder()
                .text(request.getText())
                .icEntity(icRepository.findByPublicId(request.getIcPublicId()).orElseThrow(() ->
                        new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND)))
                .createdDate(new Date())
                .termEntity(termRepository.findByPublicId(request.getTermPublicId()).orElseThrow(() ->
                        new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND)))
                .userEntity(userRepository.findByUsername(username).orElseThrow(() ->
                        new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND)))
                .publicId(UUID.randomUUID().toString())
                .isUnknown(request.isUnknown())
                .build();
    }

    public BookletCommentEntity convert(BookletCommentRequest request, String username) {
        return BookletCommentEntity.builder()
                .text(request.getText())
                .bookletEntity(bookletRepository.findByPublicId(request.getBookletPublicId()).orElseThrow(() ->{
                    logger.log(Level.WARNING,"Invalid booklet publicId");
                    return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
                } ))
                .termEntity(termRepository.findByPublicId(request.getTermPublicId()).orElseThrow(() ->
                        new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND)))
                .createdDate(new Date())
                .userEntity(userRepository.findByUsername(username).orElseThrow(() ->
                        new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND)))
                .publicId(UUID.randomUUID().toString())
                .isUnknown(request.isUnknown())
                .build();
    }

    public CommentResponse convert(ICCommentEntity entity) {
        return CommentResponse.builder()
                .writerName(getWriterName(entity.isUnknown(),entity.getUserEntity().getFirstname(),entity.getUserEntity().getLastname()))
                .text(entity.getText())
                .createdDate(getJalaliDate(entity.getCreatedDate()))
                .publicId(entity.getPublicId())
                .term(entity.getTermEntity().getName())
                .writerType(getWriterType(entity.getUserEntity().getRole()))
                .build();
    }

    public CommentResponse convert(BookletCommentEntity entity) {
        return CommentResponse.builder()
                .writerName(getWriterName(entity.isUnknown(),entity.getUserEntity().getFirstname(),entity.getUserEntity().getLastname()))
                .text(entity.getText())
                .createdDate(getJalaliDate(entity.getCreatedDate()))
                .publicId(entity.getPublicId())
                .term(entity.getTermEntity().getName())
                .writerType(getWriterType(entity.getUserEntity().getRole()))
                .build();
    }

    private String getWriterName(boolean isUnknown,String firstname,String lastname){
        return isUnknown ? "ناشناس" : firstname + " " + lastname;
    }

    private String getWriterType (String role){
        return switch (role) {
            case "ADMIN" -> "ادمین";
            case "STUDENT" -> "دانشجو";
            case "INSTRUCTOR" -> "استاد";
            default -> throw new IllegalStateException("Unexpected value: " + role);
        };
    }


}
