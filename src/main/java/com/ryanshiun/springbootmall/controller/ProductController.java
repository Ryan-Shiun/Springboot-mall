package com.ryanshiun.springbootmall.controller;

import com.ryanshiun.springbootmall.constant.ProductCategory;
import com.ryanshiun.springbootmall.dao.ProductQueryParams;
import com.ryanshiun.springbootmall.dto.ProductRequest;
import com.ryanshiun.springbootmall.model.Product;
import com.ryanshiun.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // 查詢條件不能設為必要參數
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProduct(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search
            ) {
        // 創建一個 class 去接收前端傳來的值，可用於搜尋條件很多的時候
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);

        List<Product> productList = productService.getProducts(productQueryParams);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }


    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            // build() 回傳一個不帶任何 body 的 ResponseEntity
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId =  productService.createProduct(productRequest);

        // 將新增的商品回傳
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {
        // 檢查 product 是否存在
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 修改商品的數據
        productService.updateProduct(productId, productRequest);

        // 將更新後的商品回傳
        Product updateProduct = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?>deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);

        // 不需要區確認商品是否存在，只要確定商品消失不見就好
        //就算刪除不存在的商品也回傳 204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
