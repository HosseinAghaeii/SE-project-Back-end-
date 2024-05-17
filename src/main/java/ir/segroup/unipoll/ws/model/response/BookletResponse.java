package ir.segroup.unipoll.ws.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Booklet Response", description = "Model for return booklet")
public class BookletResponse {
    @Schema(description = "publicId of booklet", example = "81e7821a-d829-4660-a598-6e20bababb3e")
    private String publicId;

    @Schema(description = "The name of the course to which this booklet belongs", example = "مهندسی نرم افزار")
    private String courseName;

    @Schema(example = "رضا")
    private String instructorFirstname;

    @Schema(example = "رمضانی")
    private String instructorLastname;

    @Schema(description ="firstname of student or instructor that wrote this booklet" ,example = "حسین")
    private String uploaderFirstname;

    @Schema(description ="lastname of student or instructor that wrote this booklet" ,example = "آقایی")
    private String uploaderLastname;

    @Schema(description ="format: year-1/2 == نیمسال اول/دوم سال فلان" ,example = "1400-1")
    private String term;

    @Schema(example = "30")
    private int likeNumber;

    @Schema(description ="it shows is user like this booklet or not. values:" +
            "1- true" +
            "2-false" +
            "3-null: request send without jwt token",example = "حسین")
    private Boolean isSaved;

    @Schema(description ="it shows is user save this booklet or not. values:" +
            "1- true" +
            "2-false" +
            "3-null: request send without jwt token",example = "حسین")
    private Boolean isLiked;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private boolean teacherLike;


}
