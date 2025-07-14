package medatt.demo.Configuration;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriTemplateHandler;

import java.time.Duration;

@Configuration
public class Config {
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
  /* @Bean
   @LoadBalanced
   public WebClient.Builder loadBalancedWebClientBuilder() {
       return WebClient.builder();
   }*/

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
       UriTemplateHandler uriTemplateHandler = new RootUriTemplateHandler("http://...");
       return restTemplateBuilder
               .uriTemplateHandler(uriTemplateHandler)
               .setReadTimeout(Duration.ofMillis(1000))
               .build();

    }
}
