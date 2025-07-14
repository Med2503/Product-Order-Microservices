package medatt.demo.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import medatt.demo.dto.OrderRequest;
import medatt.demo.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
/*@RequiredArgsConstructor*/
@Validated
public class OrderController {
   private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="product",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name="product")
    @Retry(name="product")
    @RateLimiter(name="product")
    public CompletableFuture<String> saveOrder(@RequestBody @Valid OrderRequest orderRequest){

        return CompletableFuture.supplyAsync(()->orderService.SaveOrder(orderRequest));
    }


    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
    return CompletableFuture.supplyAsync(()->"Cannot reach order ! try again");
    }
}
