package ir.segroup.unipoll.ws.model.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RateResponse {
    private double rate;
    private double averageRate;
}
