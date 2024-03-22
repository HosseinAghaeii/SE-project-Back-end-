package ir.segroup.unipoll.shared.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BaseApiResponse {
    private boolean action;
    private String message;
    private Date date;
    private Object result;
}
