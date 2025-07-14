package medatt.demo.service;

import lombok.RequiredArgsConstructor;
import medatt.demo.RecordKafka.EventConsumer;
import medatt.demo.dto.OrderLineItemsRequest;
import medatt.demo.dto.OrderRequest;
import medatt.demo.dto.ProductResponse;
import medatt.demo.model.Order;
import medatt.demo.model.OrderLineItems;
import medatt.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    //private final WebClient.Builder webClientBuilder;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private RestTemplate restTemplate;

    private final Tracer tracer;

    private final KafkaTemplate<String, EventConsumer> kafkaTemplate;

   /*public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }
*/


    public String SaveOrder(OrderRequest orderRequest){
        Span ProductServiceLookUp = tracer.nextSpan().name("ProductServiceLookUp");
        try(Tracer.SpanInScope spanInScope = tracer.withSpan(ProductServiceLookUp.start()))  {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());


            List<OrderLineItems> list = orderRequest.getOrderLineItemsRequest().stream().map(this::mapToDto).toList();
            order.setOrderLineItems(list);
            kafkaTemplate.send("notificationTopic",new EventConsumer(order.getOrderNumber()));

            List<String> codes = orderRequest.getOrderLineItemsRequest().stream().map(this::getCode).toList();
            ProductResponse[] listt = webClientBuilder.build()
                    .get()
                    .uri("http://product-service/api/product",uriBuilder -> uriBuilder.queryParam("code",codes).build())
                    .retrieve()

                    .bodyToMono(ProductResponse[].class)

                    .block();

/*
              --------------------------------
//            #Des utilisation de restTemplate
              # il y'as plusieurs methodes avec restTemplate:
              restTemplate.getForEntity(....)
              restTemplate.getForObject(....)
              restTemplate.exchange(...)
              ---------------------------------


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            HttpEntity<String> entity = new HttpEntity<>("this Is me!",headers);
            URI location = restTemplate.postForLocation("http://......",entity);


            HttpEntity<String> entity1 = restTemplate.getForEntity("http://....",String.class);
            String body = entity1.getBody();
            System.out.println(body);
            MediaType ContentType = entity1.getHeaders().getContentType();

            InventoryResponse a = new InventoryResponse();
            HttpHeaders headers1 =  new HttpHeaders();
            headers1.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<InventoryResponse> entity2 = new HttpEntity<>(a,headers1);
            URI location2 = restTemplate.postForLocation("http://.....",entity2);

            HttpEntity<InventoryResponse> entity4 = restTemplate.getForEntity("http://....",InventoryResponse.class);
            String body11 = String.valueOf(entity4.getBody());
            MediaType contentType = entity4.getHeaders().getContentType();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<InventoryResponse> entity = new HttpEntity<>(headers);
            ResponseEntity<InventoryResponse> responseEnity = restTemplate.exchange("http://..."
            , HttpMethod.GET,
                   entity,
                    InventoryResponse.class );
                    */
         /*   HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            InventoryResponse a = new InventoryResponse();
            HttpEntity<InventoryResponse> ent = new HttpEntity<>(a,headers);
            restTemplate.postForLocation("http://...",ent);*/


         /*   HttpHeaders headers1 = new HttpHeaders();
            headers1.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity httpEntity  = new HttpEntity(headers1);
            ResponseEntity<InventoryResponse> a1 = restTemplate.exchange("http://....",
                    HttpMethod.GET,
                    httpEntity,
                    InventoryResponse.class
                    );
            a1.getStatusCode();
            a1.getBody();
            a1.getHeaders();*/







            boolean val = Arrays.stream(listt).allMatch(ProductResponse->ProductResponse.isIsInStock());
            if(val){
                orderRepository.save(order);
                return "Order succes!";
            }else{
                throw new IllegalArgumentException("Not in stock!");
            }


        }finally{
            ProductServiceLookUp.end();
        }}


    private String getCode(OrderLineItemsRequest orderLineItemsRequest) {
        return orderLineItemsRequest.getCode();
    }

    private OrderLineItems mapToDto(OrderLineItemsRequest orderLineItemsRequest) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setCode(orderLineItemsRequest.getCode());
        orderLineItems.setQuantity(orderLineItemsRequest.getQuantity());
        orderLineItems.setPrice(orderLineItemsRequest.getPrice());
        return orderLineItems;
    }




}
