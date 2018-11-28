package com.mao.order.controller;

import com.mao.product.client.ProductClient;
import com.mao.product.common.DecreaseStockInput;
import com.mao.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {


    @Autowired
    private ProductClient productClient;


    @GetMapping("/product/listForOrder")
    public String getProductList(){
       List<ProductInfoOutput> productInfoList = productClient.listForOrder(Arrays.asList("123456"));
        log.info("response={}",productInfoList);
        return "ok";
    }

    @GetMapping("/product/decreaseStock")
    public String productDereaseStock(){
        productClient.decreaseStock(Arrays.asList(new DecreaseStockInput("123456",10)));

       // log.info("response={}",productInfoList);
        return "ok";
    }
}
