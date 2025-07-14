package medatt.demo.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name="t_order1")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*
@JsonInclude(JsonInclude.Include.NON_EMPTY) : exemple
Order or = new Order(5,null,null);
ObjectMapper obj = new ObjectMapper();
String ord = obj.WriteValueAsString(or);
System.out.println(ord);
{"id":"5"}

*/

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String OrderNumber;

   /* @Email
    private String email;


    @Min(10)
    @Max(1000)
    private Integer number;


    @NotNull
    @Length(min = 10,max = 20)
    private  String chaine;*/





    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItems;
}
