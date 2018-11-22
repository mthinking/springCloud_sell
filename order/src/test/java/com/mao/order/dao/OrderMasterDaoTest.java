package com.mao.order.dao;

import com.mao.order.OrderApplicationTests;
import com.mao.order.entity.OrderMaster;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@Component
public class OrderMasterDaoTest extends OrderApplicationTests {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Test
    public void testsave() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234564");
        orderMaster.setBuyerName("是是a");
        orderMaster.setBuyerPhone("18263555544");
        orderMaster.setBuyerAddress("上海");
        orderMaster.setBuyerOpenid("110110");
        orderMaster.setOrderAmount(new BigDecimal(1.5));
        OrderMaster result = orderMasterDao.save(orderMaster);
        Assert.assertTrue(result != null);
    }
}