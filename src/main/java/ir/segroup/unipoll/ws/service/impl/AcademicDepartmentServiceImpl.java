package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.AcademicDepartmentUtil;
import ir.segroup.unipoll.ws.model.entity.AcademicDepartmentEntity;
import ir.segroup.unipoll.ws.model.response.DepartmentResponse;
import ir.segroup.unipoll.ws.repository.AcademicDepartmentRepository;
import ir.segroup.unipoll.ws.service.AcademicDepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AcademicDepartmentServiceImpl implements AcademicDepartmentService {
    private final AcademicDepartmentUtil util;

    private final AcademicDepartmentRepository academicDepartmentRepository;

    public AcademicDepartmentServiceImpl(AcademicDepartmentUtil util, AcademicDepartmentRepository academicDepartmentRepository) {
        this.util = util;
        this.academicDepartmentRepository = academicDepartmentRepository;
    }

    @Override
    public ResponseEntity<BaseApiResponse> getAllDepartments() {
        List<DepartmentResponse> result = academicDepartmentRepository.findAll().stream().map(util::convert).toList();
        return util.createResponse(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getManagerAndAssistant(String publicId) {
        AcademicDepartmentEntity existedDepartment = util.find(publicId);
        return util.createResponse(util.managerAndAssistantConvert(existedDepartment), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getInstructors(String publicId) {
        AcademicDepartmentEntity existedDepartment = util.find(publicId);
        return util.createResponse(util.instructorConvert(existedDepartment), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getCourses(String publicId) {
        AcademicDepartmentEntity existedDepartment = util.find(publicId);
        return util.createResponse(util.courseConvert(existedDepartment), HttpStatus.OK);
    }
}
