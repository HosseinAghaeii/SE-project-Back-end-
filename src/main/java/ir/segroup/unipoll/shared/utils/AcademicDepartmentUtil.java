package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.ws.model.entity.AcademicDepartmentEntity;
import ir.segroup.unipoll.ws.model.response.DepartmentCourseResponse;
import ir.segroup.unipoll.ws.model.response.DepartmentDescriptionResponse;
import ir.segroup.unipoll.ws.model.response.DepartmentInstructorResponse;
import ir.segroup.unipoll.ws.model.response.DepartmentManagerAndAssistantResponse;
import ir.segroup.unipoll.ws.repository.AcademicDepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class AcademicDepartmentUtil extends Util {
    private final ModelMapper modelMapper;
    private final AcademicDepartmentRepository academicDepartmentRepository;

    public AcademicDepartmentUtil(ModelMapper modelMapper, AcademicDepartmentRepository academicDepartmentRepository) {
        this.modelMapper = modelMapper;
        this.academicDepartmentRepository = academicDepartmentRepository;
    }

    public AcademicDepartmentEntity find(String departmentName) {
        Optional<AcademicDepartmentEntity> existedDepartment = academicDepartmentRepository.findByName(departmentName);
        if (existedDepartment.isEmpty())
            throw new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        return existedDepartment.get();
    }

    public DepartmentDescriptionResponse convert(AcademicDepartmentEntity department) {
        return modelMapper.map(department, DepartmentDescriptionResponse.class);
    }

    public List<DepartmentManagerAndAssistantResponse> managerAndAssistantConvert(AcademicDepartmentEntity department) {
        List<DepartmentManagerAndAssistantResponse> result = new ArrayList<>();
        result.add(DepartmentManagerAndAssistantResponse.builder()
                .type("Manager")
                .username(department.getManager().getUsername())
                .firstname(department.getManager().getFirstname())
                .lastname(department.getManager().getLastname())
                .profilePhoto(department.getManager().getProfilePhoto())
                .phd(department.getManager().getPhd())
                .academicRank(department.getManager().getAcademicRank())
                .phoneNumber(department.getManager().getPhoneNumber())
                .email(department.getManager().getEmail())
                .websiteLink(department.getManager().getWebsiteLink())
                .build());

        result.add(DepartmentManagerAndAssistantResponse.builder()
                .type("Assistant")
                .username(department.getAssistant().getUsername())
                .firstname(department.getAssistant().getFirstname())
                .lastname(department.getAssistant().getLastname())
                .profilePhoto(department.getAssistant().getProfilePhoto())
                .phd(department.getAssistant().getPhd())
                .academicRank(department.getAssistant().getAcademicRank())
                .phoneNumber(department.getAssistant().getPhoneNumber())
                .email(department.getAssistant().getEmail())
                .websiteLink(department.getAssistant().getWebsiteLink())
                .build());
        return result;
    }

    public List<DepartmentInstructorResponse> instructorConvert(AcademicDepartmentEntity department) {
        List<DepartmentInstructorResponse> result = new ArrayList<>();
        department.getInstructorEntities()
                .forEach(instructor -> result.add(DepartmentInstructorResponse.builder()
                        .username(instructor.getUsername())
                        .firstname(instructor.getFirstname())
                        .lastname(instructor.getLastname())
                        .profilePhoto(instructor.getProfilePhoto())
                        .academicRank(instructor.getAcademicRank())
                        .build()));
        return result;
    }

    public List<DepartmentCourseResponse> courseConvert(AcademicDepartmentEntity department) {
        List<DepartmentCourseResponse> result = new ArrayList<>();
        department.getCourseEntities()
                .forEach(course -> result.add(DepartmentCourseResponse.builder()
                        .name(course.getName())
                        .publicId(course.getPublicId())
                        .build()));
        return result;
    }
}
