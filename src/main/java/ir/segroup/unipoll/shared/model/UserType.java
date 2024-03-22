package ir.segroup.unipoll.shared.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {
    ADMIN("ADMIN"),
    STUDENT("STUDENT"),
    INSTRUCTOR("INSTRUCTOR")
    ;

    private final String type;
}
