package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.BookletUtil;
import ir.segroup.unipoll.ws.model.entity.BookletEntity;
import ir.segroup.unipoll.ws.model.entity.UserEntity;
import ir.segroup.unipoll.ws.model.request.BookletRequest;
import ir.segroup.unipoll.ws.model.response.BookletResponse;
import ir.segroup.unipoll.ws.repository.BookletRepository;
import ir.segroup.unipoll.ws.repository.UserRepository;
import ir.segroup.unipoll.ws.service.BookletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class BookletServiceImpl implements BookletService {
    private final BookletRepository bookletRepository;
    private final UserRepository userRepository;
    private final BookletUtil bookletUtil;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public BookletServiceImpl(BookletRepository bookletRepository, UserRepository userRepository, BookletUtil bookletUtil) {
        this.bookletRepository = bookletRepository;
        this.userRepository = userRepository;
        this.bookletUtil = bookletUtil;
    }

    @Override
    public ResponseEntity<byte[]> downloadBooklet(String publicId) {
        Optional<BookletEntity> existedBooklet = bookletRepository.findByPublicId(publicId);
        if (existedBooklet.isEmpty())
            throw new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        String bookletPath = existedBooklet.get().getFilePath();
        try {
            byte[] booklet = Files.readAllBytes(new File(bookletPath).toPath());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_PDF_VALUE))
                    .body(booklet);
        } catch (IOException exception) {
            logger.log(Level.OFF, exception.getMessage());
            throw new SystemServiceException(ExceptionMessages.FILE_EXCEPTION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<BaseApiResponse> uploadBooklet(MultipartFile booklet, BookletRequest bookletRequest, String token) {
        String username = bookletUtil.getUsernameFromToken(token);
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> {
            logger.log(Level.OFF, "Failed to get user details");
            return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        });
        BookletEntity bookletEntity = bookletUtil.convert(booklet, bookletRequest, userEntity);
        if (bookletEntity == null) {
            throw new SystemServiceException(ExceptionMessages.EMPTY_FILE.getMessage(), HttpStatus.NO_CONTENT);
        }
        try {
            BookletEntity savedBookletEntity = bookletRepository.save(bookletEntity);
            booklet.transferTo(new File(bookletEntity.getFilePath()));
            return bookletUtil.createResponse(savedBookletEntity.getPublicId(), HttpStatus.CREATED);
        } catch (IOException exception) {
            logger.log(Level.OFF, exception.getMessage());
            throw new SystemServiceException(ExceptionMessages.FILE_EXCEPTION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception exception) {
            logger.log(Level.OFF, exception.getMessage());
            throw new SystemServiceException(ExceptionMessages.DATABASE_IO_EXCEPTION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<BaseApiResponse> getTenTopBooklets(String token) {
        String username = bookletUtil.getUsernameFromToken(token);

        HashMap<String, Integer> likesNumberMap = new HashMap<>();
        bookletRepository.findAll().forEach(b -> likesNumberMap.put(b.getPublicId(), b.getLikes().size()));
        List<String> tenTopPublicIdList = likesNumberMap.entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();
        List<BookletResponse> responses = tenTopPublicIdList.stream().map(s -> bookletUtil.convert(s, username)).toList();

        return bookletUtil.createResponse(responses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> likeABooklet(String token, String bookletPublicId) {
        String username = bookletUtil.getUsernameFromToken(token);

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> {
            logger.log(Level.OFF, "Failed to get user details");
            return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        });
        BookletEntity bookletEntity = bookletRepository.findByPublicId(bookletPublicId).orElseThrow(() -> {
            logger.log(Level.OFF, "Failed to get booklet details");
            return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        });
        bookletEntity.getLikes().stream().filter(u -> u.getUsername().equals(username)).findFirst()
                .map(s -> {
                    logger.log(Level.OFF, "This user has already liked booklet.");
                    throw  new SystemServiceException(ExceptionMessages.DUPLICATED_LIKE.getMessage(), HttpStatus.CONFLICT);
                });

        userEntity.getLikedBooklets().add(bookletEntity);
        bookletEntity.getLikes().add(userEntity);
        try {
        userRepository.save(userEntity);
        bookletRepository.save(bookletEntity);
        }catch (Exception e){
            logger.log(Level.OFF,"Error in write into db");
            throw new SystemServiceException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return bookletUtil.createResponse("SUCCESSFULLY",HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BaseApiResponse> deleteABooklet(String publicId, String token) {
        String username = bookletUtil.getUsernameFromToken(token);

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> {
            logger.log(Level.OFF, "Failed to get user details");
            return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        });
        BookletEntity bookletEntity = bookletRepository.findByPublicId(publicId).orElseThrow(() -> {
            logger.log(Level.OFF, "Failed to get booklet details");
            return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        });
        if (!bookletEntity.getUploaderUser().getUsername().equals(userEntity.getUsername()))
            throw new SystemServiceException(ExceptionMessages.FORBIDDEN_DELETE_BOOKLET_REQUEST.getMessage(), HttpStatus.FORBIDDEN);
        try {
            bookletRepository.delete(bookletEntity);
        }catch (Exception e){
            logger.log(Level.WARNING, e.getMessage());
            throw new SystemServiceException(ExceptionMessages.DATABASE_IO_EXCEPTION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return bookletUtil.createResponse("DELETING SUCCESSFULLY", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getFavoriteBooklets(String token) {
        String username = bookletUtil.getUsernameFromToken(token);

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> {
            logger.log(Level.OFF, "Failed to get user details");
            return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        });
        List<BookletResponse> responses = userEntity.getFavoriteBooklets().stream()
                .map(bookletEntity -> bookletUtil.convert(bookletEntity, username))
                .toList();
        return bookletUtil.createResponse(responses, HttpStatus.OK);
    }


}
