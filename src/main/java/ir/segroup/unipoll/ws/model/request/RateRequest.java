package ir.segroup.unipoll.ws.model.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateRequest {
    private double rate;
}
