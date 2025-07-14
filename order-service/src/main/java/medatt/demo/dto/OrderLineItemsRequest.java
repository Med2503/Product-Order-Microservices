package medatt.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineItemsRequest {
    private Long id;
    private String Code;
    private BigDecimal price;
    private Integer quantity;
}
