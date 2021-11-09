package com.tjv.semesterproject.controller;


import com.tjv.semesterproject.entity.ProductEntity;
import com.tjv.semesterproject.exceptions.OrderNotExistException;
import com.tjv.semesterproject.exceptions.ProductExistException;
import com.tjv.semesterproject.exceptions.ProductNotExistException;
import com.tjv.semesterproject.model.ProductDto;
import com.tjv.semesterproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity registerProduct(@RequestBody ProductEntity product) {
        try {
            productService.registerProduct(product);
            return ResponseEntity.ok("Product has been saved");
        } catch (ProductExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product Posting Error");
        }
    }

    @GetMapping("/")
    public ResponseEntity getProduct(@RequestParam(name = "product_id") String id) {
        try {
            return ResponseEntity.ok(productService.getProduct(id));
        } catch (ProductNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product Customer Getting Error");
        }
    }

    @GetMapping("/orders/{product_id}/")
    public ResponseEntity getProductOrder(@PathVariable("product_id") String product_id,
                                          @RequestParam(name = "order_id") Integer order_id) {
        try {
            return ResponseEntity.ok(productService.getProductOrder(product_id, order_id));
        } catch (ProductNotExistException | OrderNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product Customer Getting Error");
        }
    }

    @GetMapping
    public ResponseEntity getAllProducts() {
        try {
            return ResponseEntity.ok(productService.readAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product Getting Error");
        }
    }

    @GetMapping("/orders/{product_id}")
    public ResponseEntity getAllProductOrders(@PathVariable("product_id") String product_id) {
        try {
            return ResponseEntity.ok(productService.readAllOrders(product_id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product Getting Error");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@RequestBody ProductDto productDto,
                                        @PathVariable("id") String id) {
        try {
            productService.updateProduct(productDto, id);
            return ResponseEntity.ok("Product has been saved");
        } catch (ProductNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product Putting Error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") String id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(id + " has been deleted");
        } catch (ProductNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product Deleting Error");
        }
    }

}
