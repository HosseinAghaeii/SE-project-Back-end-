package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.AcademicDepartmentUtil;
import ir.segroup.unipoll.ws.model.entity.AcademicDepartmentEntity;
import ir.segroup.unipoll.ws.repository.AcademicDepartmentRepository;
import ir.segroup.unipoll.ws.service.AcademicDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AcademicDepartmentServiceImpl implements AcademicDepartmentService {
    private final AcademicDepartmentUtil util;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public AcademicDepartmentServiceImpl(AcademicDepartmentUtil util) {
        this.util = util;
    }

    @Override
    public ResponseEntity<BaseApiResponse> getDescription(String departmentName) {
        AcademicDepartmentEntity existedDepartment = util.find(departmentName);
        return util.createResponse(util.convert(existedDepartment), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getManagerAndAssistant(String departmentName) {
        AcademicDepartmentEntity existedDepartment = util.find(departmentName);
        return util.createResponse(util.managerAndAssistantConvert(existedDepartment), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getInstructors(String departmentName) {
        AcademicDepartmentEntity existedDepartment = util.find(departmentName);
        return util.createResponse(util.instructorConvert(existedDepartment), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getCourses(String departmentName) {
        AcademicDepartmentEntity existedDepartment = util.find(departmentName);
        return util.createResponse(util.courseConvert(existedDepartment), HttpStatus.OK);
    }
}
