package com.mao.order.dao;

import com.mao.order.OrderApplicationTests;
import com.mao.order.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@Component
public class OrderDetailDaoTest extends OrderApplicationTests {

    @Autowired
    private  OrderDetailDao repository;

    @Test
    public void testSave() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("11111212312");
        orderDetail.setOrderId("11111121231");
        orderDetail.setProductIcon("htt.jpg");
        orderDetail.setProductId("111114");
        orderDetail.setProductName("馒头");
        orderDetail.setProductPrice(new BigDecimal(2));
        orderDetail.setProductQuantity(2);
        OrderDetail result = repository.save(orderDetail);
        Assert.assertTrue(result != null);
    }
}