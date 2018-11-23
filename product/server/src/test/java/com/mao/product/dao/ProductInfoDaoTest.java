package com.mao.product.dao;

import com.mao.product.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest {

    @Autowired
    private ProductInfoDao repository;

    @Test
    public void saveTest(){
        ProductInfo p = new ProductInfo();
        p.setProductId("123456");
        p.setProductName("皮蛋");
        p.setProductPrice(new BigDecimal(3.2));
        p.setProductStock(100);
        p.setProductDescription("要少吃");
        p.setProductIcon("app.jpg");
        p.setProductStatus(0);
        p.setCategoryType(1);

        repository.save(p);
    }

    @Test
    public void findByProductsStatusTest(){
        List<ProductInfo> pros = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,pros.size());
    }
}