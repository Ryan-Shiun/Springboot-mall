package com.ryanshiun.springbootmall.controller;

import com.ryanshiun.springbootmall.constant.ProductCategory;
import com.ryanshiun.springbootmall.dao.ProductQueryParams;
import com.ryanshiun.springbootmall.dto.ProductRequest;
import com.ryanshiun.springbootmall.model.Product;
import com.ryanshiun.springbootmall.service.ProductService;
import com.ryanshiun.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private WebRequest webRequest;

    // 查詢商品 By 搜尋條件
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProduct(
            // 查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            // 排序 sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            // 分頁 Pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit, // 使用 Max Min 記的要加上 @Validated
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ) {
        // 創建一個 class 去接收前端傳來的值，可用於搜尋條件很多的時候
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // 取得 product list
        List<Product> productList = productService.getProducts(productQueryParams);

        // 取得 product 總數
        Integer total = productService.countProduct(productQueryParams);

        // 分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        // 將商品資訊改為放在 results 裡面回傳給前端
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    // 查詢商品名稱 By productId
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

    // 新增商品
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId =  productService.createProduct(productRequest);

        // 將新增的商品回傳
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    // 修改商品 By productId
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

    // 刪除商品 By productId
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?>deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);

        // 不需要區確認商品是否存在，只要確定商品消失不見就好
        // 就算刪除不存在的商品也回傳 204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
