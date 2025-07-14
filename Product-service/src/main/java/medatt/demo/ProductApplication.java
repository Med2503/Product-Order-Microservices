package medatt.demo;

import medatt.demo.model.Product;
import medatt.demo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class ProductApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository){
        return args->{
            Product product = new Product();
            product.setCode("60");
            product.setQuantity(10);

            Product product1 = new Product();
            product1.setCode("55");
            product1.setQuantity(10);

            productRepository.save(product);
            productRepository.save(product1);
        };
    }
}