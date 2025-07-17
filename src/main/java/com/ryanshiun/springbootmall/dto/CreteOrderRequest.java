package com.ryanshiun.springbootmall.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreteOrderRequest {

    @NotEmpty
    private List<BuyItem> buyItemList;
}
