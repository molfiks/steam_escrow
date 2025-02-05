package com.angular.angularnetwork.product;


import com.angular.angularnetwork.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@Tag(name = "product")
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Integer> saveProduct(
            @Valid @RequestBody ProductRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.save(request,connectedUser));
    }

    @GetMapping("{product-id}")
    public ResponseEntity<ProductResponse> findProductById(
            @PathVariable("product-id") Integer productId
    ){
        return ResponseEntity.ok(service.findById(productId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> findAllProducts(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllProducts(page,size,connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<ProductResponse>> findAllProductsByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllProductsByOwner(page,size,connectedUser));
    }
}
