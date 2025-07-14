package medatt.demo.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Entity
@Table(name="t_OrderLineItems1")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private BigDecimal price;
    private Integer quantity;
    /*@ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;*/

}
