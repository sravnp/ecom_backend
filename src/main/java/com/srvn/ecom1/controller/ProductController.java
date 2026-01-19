package com.srvn.ecom1.controller;

import com.srvn.ecom1.model.Product;
import com.srvn.ecom1.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = service.getProductById(id);
        return product != null
                ? ResponseEntity.ok(product)
                : ResponseEntity.notFound().build();
    }

    @PostMapping(
            value = "/product",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = service.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/product/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> updateProduct(
            @PathVariable int id,
            @RequestBody Product product
    ) {
        Product updated = service.updateProduct(id, product);
        return updated != null
                ? ResponseEntity.ok("Product Updated Successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Product Not Found");
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product product = service.getProductById(id);

        if (product != null) {
            service.deleteProduct(id);
            return ResponseEntity.ok("Product Deleted Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product Not Found");
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(service.searchProducts(keyword));
    }
}
