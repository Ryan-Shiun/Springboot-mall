# Springboot-mall

Springboot-mall 是一個以 Spring Boot 為基礎打造的線上商城後端 API 專案，支援商品管理、會員註冊/登入、訂單管理等功能，適合作為 RESTful API 架構學習、電商網站後端實作範例。

---

## 專案特色

- **商品管理**：支援商品新增、查詢（可依類別/關鍵字/排序/分頁）、修改、刪除。
- **會員系統**：提供會員註冊及登入功能。
- **訂單管理**：可依會員查詢訂單列表、詳細訂單資訊，並支援創建訂單。
- **分頁、排序、篩選**：查詢商品/訂單時可依照多種條件分頁、排序、篩選。
- **RESTful API 設計**：各功能皆以標準 RESTful API 風格設計，易於串接前端或第三方應用。
- **例外處理/驗證**：包含資料驗證與錯誤回應，提升系統穩定性。
- **事務處理**：訂單流程支援多表資料一致性（如庫存扣除等）。

---

## 技術棧

- **Spring Boot**：主框架，快速開發、易於維護。
- **Spring MVC**：RESTful API 架構。
- **Spring Data / JdbcTemplate**：資料處理、SQL 操作。
- **Hibernate Validator**：參數驗證（如分頁 Max/Min 驗證）。
- **MySQL**：資料庫（可依需求切換）。
- **Maven**：依賴管理與打包。
- **Lombok**：簡化 Java 程式碼。
---

## 主要功能說明

### 商品管理

- **查詢商品**：`GET /products`
  - 支援依類別（category）、關鍵字（search）、排序（orderBy/sort）、分頁（limit/offset）查詢。
- **查詢單一商品**：`GET /products/{productId}`
- **新增商品**：`POST /products`
- **更新商品**：`PUT /products/{productId}`
- **刪除商品**：`DELETE /products/{productId}`

### 會員系統

- **註冊**：`POST /users/register`
- **登入**：`POST /users/login`

### 訂單管理

- **查詢會員訂單**：`GET /users/{userId}/orders`
  - 支援分頁查詢。
- **創建新訂單**：`POST /users/{userId}/orders`
  - 需登入會員，並傳入購買商品明細。

---

## 專案架構

```
src/
  main/
    java/
      com.ryanshiun.springbootmall/
        controller/   // REST API 控制器
        service/      // 業務邏輯
        dao/          // 資料存取層
        model/        // 實體 (商品、訂單、會員)
        dto/          // 資料傳輸物件
    resources/
      application.properties // 參數設定
```
## 資料表設計（簡述）

- `product`：商品資料，包含名稱、分類、價格、庫存等。
- `user`：會員資料，包含帳號、密碼等。
- `order`：訂單資料，包含訂購人、總金額、訂單狀態等。
- `order_item`：訂單明細，包含商品、數量、單價等。

---
<img width="1554" height="694" alt="shop" src="https://github.com/user-attachments/assets/19371097-1f0d-474b-848b-d3e70e044b7c" />
<img width="1496" height="579" alt="cart" src="https://github.com/user-attachments/assets/88f3ce5c-d139-41a6-891d-bc5844424cab" />
<img width="1512" height="719" alt="myOrder" src="https://github.com/user-attachments/assets/c2960dd5-b849-4fd1-805d-bfce54c0a70e" />


---
