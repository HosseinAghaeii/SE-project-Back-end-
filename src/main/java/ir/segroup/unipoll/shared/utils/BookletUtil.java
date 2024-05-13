package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.ws.model.entity.BookletEntity;
import ir.segroup.unipoll.ws.model.entity.UserEntity;
import ir.segroup.unipoll.ws.model.request.BookletRequest;
import ir.segroup.unipoll.ws.model.response.BookletResponse;
import ir.segroup.unipoll.ws.repository.BookletRepository;
import ir.segroup.unipoll.ws.repository.InstructorCourseRepository;
import ir.segroup.unipoll.ws.repository.TermRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class BookletUtil extends Util {

    private final Path fileStorageLocation;
    private final BookletRepository bookletRepository;
    private final TermRepository termRepository;

    private final InstructorCourseRepository instructorCourseRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public BookletUtil(Environment env, BookletRepository bookletRepository, TermRepository termRepository, InstructorCourseRepository instructorCourseRepository) {
        this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./uploads/files"))
                .toAbsolutePath().normalize();
        this.bookletRepository = bookletRepository;
        this.termRepository = termRepository;

        this.instructorCourseRepository = instructorCourseRepository;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new SystemServiceException(ExceptionMessages.COULD_NOT_CREAT_DIRECTORY.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public BookletEntity convert(MultipartFile booklet, BookletRequest bookletRequest, UserEntity userEntity) {
        String filePath = fileStorageLocation + "_" + booklet.getOriginalFilename();
        if (booklet.isEmpty())
            return null;
        return BookletEntity.builder()
                .publicId(UUID.randomUUID().toString())
                .filePath(filePath)
                .termEntity(termRepository.findByPublicId(bookletRequest.getTermPublicId())
                        .orElseThrow(() -> new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.BAD_REQUEST)))
                .text(bookletRequest.getText())
                .uploaderUser(userEntity)
                .instructorCourseEntity(instructorCourseRepository.findByPublicId(bookletRequest.getInstCoursePublicId())
                        .orElseThrow(() -> new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.BAD_REQUEST)))
                .build();
    }

    public BookletResponse convert(String publicId, String username) {
        BookletEntity bookletEntity = bookletRepository.findByPublicId(publicId).get();
        BookletResponse response = BookletResponse.builder()
                .publicId(bookletEntity.getPublicId())
                .courseName(bookletEntity.getInstructorCourseEntity().getCourseEntity().getName())
                .instructorFirstname(bookletEntity.getInstructorCourseEntity().getInstructorEntity().getFirstname())
                .instructorLastname(bookletEntity.getInstructorCourseEntity().getInstructorEntity().getLastname())
                .uploaderFirstname(bookletEntity.getUploaderUser().getFirstname())
                .uploaderLastname(bookletEntity.getUploaderUser().getLastname())
                .term(bookletEntity.getTermEntity().getName())
                .likeNumber(bookletEntity.getLikes().size())
                .description(bookletEntity.getText())
                .build();
        if (username == null) {
            response.setIsLiked(null);
            response.setIsSaved(null);
        } else {
            response.setIsLiked(false);
            response.setIsSaved(false);
            bookletEntity.getLikes().forEach(user -> {
                if (user.getUsername().equals(username)) {
                    response.setIsLiked(true);
                }
            });
            bookletEntity.getFavoritedUsers().forEach(user ->{
                if (user.getUsername().equals(username)){
                    response.setIsSaved(true);
                }
            });
        }
        return response;

    }

    public BookletResponse convert(BookletEntity bookletEntity, String username) {
        BookletResponse response = BookletResponse.builder()
                .publicId(bookletEntity.getPublicId())
                .courseName(bookletEntity.getInstructorCourseEntity().getCourseEntity().getName())
                .instructorFirstname(bookletEntity.getInstructorCourseEntity().getInstructorEntity().getFirstname())
                .instructorLastname(bookletEntity.getInstructorCourseEntity().getInstructorEntity().getLastname())
                .uploaderFirstname(bookletEntity.getUploaderUser().getFirstname())
                .uploaderLastname(bookletEntity.getUploaderUser().getLastname())
                .term(bookletEntity.getTermEntity().getName())
                .likeNumber(bookletEntity.getLikes().size())
                .description(bookletEntity.getText())
                .build();
        if (username == null) {
            response.setIsLiked(null);
            response.setIsSaved(null);
        } else {
            response.setIsLiked(false);
            response.setIsSaved(false);
            bookletEntity.getLikes().forEach(user -> {
                if (user.getUsername().equals(username)) {
                    response.setIsLiked(true);
                }
            });
            bookletEntity.getFavoritedUsers().forEach(user ->{
                if (user.getUsername().equals(username)){
                    response.setIsSaved(true);
                }
            });
        }
        return response;
    }

}
