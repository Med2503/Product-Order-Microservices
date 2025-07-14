package medatt.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import medatt.demo.dto.ProductResponse;
import medatt.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository ProductRepository;

   /* public boolean IsInStock(String code){
        return ProductRepository.findByCode(code).isPresent();
    }*/
   @Transactional(readOnly = true)
    public List<ProductResponse> findByCodeIn(List<String> codes) {
        log.info("Checking Inventory");
        return ProductRepository.findByCodeIn(codes).stream()
                .map(product ->
                        ProductResponse.builder()
                                .Code(product.getCode())
                                .IsInStock(product.getQuantity() > 0)
                                .build()
                ).toList();
    }

    }

   /* private ProductResponse mapTo(Product product) {
        return ProductResponse.builder()
                .code(product.getCode())
                .IsInStock(product.getQuantity() > 0)
                .build();
    }*/

