package medatt.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import medatt.demo.model.OrderLineItems;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private List<OrderLineItemsRequest> orderLineItemsRequest;
}
