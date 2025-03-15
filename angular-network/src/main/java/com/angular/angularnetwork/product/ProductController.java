package com.angular.angularnetwork.product;


import com.angular.angularnetwork.common.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
        return ResponseEntity.ok(service.save(request, connectedUser));
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
        return ResponseEntity.ok(service.findAllProducts(page, size, connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<ProductResponse>> findAllProductsByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllProductsByOwner(page, size, connectedUser));
    }

    @GetMapping("/bought")
    public ResponseEntity<PageResponse<BoughtProductResponse>> findAllBoughtProducts(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBoughtProducts(page, size, connectedUser));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BoughtProductResponse>> findAllReturnedProducts(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllReturnedProducts(page, size, connectedUser));
    }

    @PatchMapping("/shareable/{product-id}")
    public ResponseEntity<Integer> updateShareableStatus(
            @PathVariable("product-id") Integer productId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.updateShareableStatus(productId, connectedUser));
    }

    @PatchMapping("/archived/{product-id}")
    public ResponseEntity<Integer> updateArchivedStatus(
            @PathVariable("product-id") Integer productId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.updateArchivedStatus(productId, connectedUser));
    }

    // en son balance işlemleri eklenecek
    @PostMapping("/purchase/{product-id}")
    public ResponseEntity<Integer> purchaseProduct(
            @PathVariable("product-id") Integer productId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.purchaseProduct(productId, connectedUser));
    }

    @PatchMapping("/purchase/approve/{product-id}")
    public ResponseEntity<Integer> approvePurchaseProduct(
            @PathVariable("product-id") Integer productId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.approvePurchaseProduct(productId, connectedUser));
    }

    @PatchMapping("/purchase/return/{product-id}")
    public ResponseEntity<Integer> returnPurchaseProduct(
            @PathVariable("product-id") Integer productId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.returnPurchasedProduct(productId, connectedUser));
    }

    // en son balance işlemleri eklenecek
    @PatchMapping("/purchase/return/approve/{product-id}")
    public ResponseEntity<Integer> approveReturnPurchaseProduct(
            @PathVariable("product-id") Integer productId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.approveReturnPurchasedProduct(productId, connectedUser));
    }

    @PostMapping(value = "/cover/{product-id}",consumes = "multipart/form-data")
    public ResponseEntity<?> uploadProductCoverPicture(
            @PathVariable("product-id") Integer productId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ){
        service.uploadProductCoverPicture(file, connectedUser, productId);
        return ResponseEntity.accepted().build();
    }
}
