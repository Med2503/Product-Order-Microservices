package medatt.demo.controller;

import lombok.RequiredArgsConstructor;
import medatt.demo.dto.ProductResponse;
import medatt.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
private final ProductService productService;

/*    @GetMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean IsInStock(@PathVariable String code){
        return productService.IsInStock(code);
    }
}*/
// http://localhost:8082/api/inventory/samsungJ6,samsungA33

    // http://localhost:8082/api/product?code=samsungJ6&code=samsungA33
@GetMapping
@ResponseStatus(HttpStatus.OK)
public List<ProductResponse> findByCodeIn(@RequestBody List<String> codes){
return productService.findByCodeIn(codes);

}
}
