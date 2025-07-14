package medatt.demo.repository;

import medatt.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
//    Optional<Product> findByCode(String Code);
   // List<Product> findByCode(List<String> Codes);

    List<Product> findByCodeIn(List<String> Codes);
}
