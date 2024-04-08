package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.ws.model.entity.BookletEntity;
import ir.segroup.unipoll.ws.model.request.BookletRequest;
import ir.segroup.unipoll.ws.repository.InstructorCourseRepository;
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
public class BookletUtil extends Util{

    private final Path fileStorageLocation;

    private final InstructorCourseRepository instructorCourseRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    public BookletUtil(Environment env, InstructorCourseRepository instructorCourseRepository) {
        this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./uploads/files"))
                .toAbsolutePath().normalize();
        this.instructorCourseRepository = instructorCourseRepository;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new SystemServiceException(ExceptionMessages.COULD_NOT_CREAT_DIRECTORY.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public BookletEntity convert(MultipartFile booklet, BookletRequest bookletRequest) {
        String filePath = fileStorageLocation + booklet.getOriginalFilename();
        if (booklet.isEmpty())
            return null;
        return BookletEntity.builder()
                .publicId(UUID.randomUUID().toString())
                .filePath(filePath)
                .term(bookletRequest.getTerm())
                .text(bookletRequest.getText())
                .instructorCourseEntity(instructorCourseRepository.findByPublicId(bookletRequest.getInstCoursePublicId())
                        .orElseThrow(()-> new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.BAD_REQUEST)))
                .build();
    }
}
