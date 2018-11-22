package com.mao.order.dao;


import com.mao.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderDetailDao extends JpaRepository<OrderDetail,String> {

    /**
     * 查询订单详情 - 根据订单id
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);

}
