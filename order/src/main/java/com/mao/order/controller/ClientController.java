package com.mao.order.controller;

import com.mao.order.client.ProductClient;
import com.mao.order.dto.CartDTO;
import com.mao.order.entity.ProductInfo;
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

    @GetMapping("/getProductMsg")
    public String getProductMsg(){

        String response = productClient.productMsg();
        log.info("response={}",response);
        return response;
    }

    @GetMapping("/getProductList")
    public String getProductList(){
       List<ProductInfo> productInfoList = productClient.listForOrder(Arrays.asList("123456"));
        log.info("response={}",productInfoList);
        return "ok";
    }

    @GetMapping("/productDereaseStock")
    public String productDereaseStock(){
        productClient.decreaseStock(Arrays.asList(new CartDTO("123456",10)));

       // log.info("response={}",productInfoList);
        return "ok";
    }
}
