package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.CollegeUtil;
import ir.segroup.unipoll.ws.model.entity.CollegeEntity;
import ir.segroup.unipoll.ws.model.request.CollegeRequest;
import ir.segroup.unipoll.ws.repository.CollegeRepository;
import ir.segroup.unipoll.ws.service.CollegeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CollegeServiceImpl implements CollegeService {

    private final CollegeRepository collegeRepository;

    private final CollegeUtil collegeUtil;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public CollegeServiceImpl(CollegeRepository collegeRepository, CollegeUtil collegeUtil) {
        this.collegeRepository = collegeRepository;
        this.collegeUtil = collegeUtil;
    }

    @Override
    public ResponseEntity<BaseApiResponse> createCollege(CollegeRequest collegeRequest) {
        Optional<CollegeEntity> existedCollege = collegeRepository.findByName(collegeRequest.getName());
        if (existedCollege.isPresent()) {
            throw new SystemServiceException(ExceptionMessages.RECORD_ALREADY_EXISTS.getMessage(), HttpStatus.CONFLICT);
        }
        CollegeEntity collegeEntity = collegeUtil.convert(collegeRequest);
        CollegeEntity savedCollegeEntity;
        try {
            savedCollegeEntity = collegeRepository.save(collegeEntity);
        }catch (Exception e){
            logger.log(Level.WARNING, "Problem to write in DB");
            throw new SystemServiceException(ExceptionMessages.DATABASE_IO_EXCEPTION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return collegeUtil.createResponse(collegeUtil.convert(savedCollegeEntity),HttpStatus.CREATED);
    }
}
