package com.mao.order.service.Impl;

import com.mao.order.dao.OrderDetailDao;
import com.mao.order.dao.OrderMasterDao;
import com.mao.order.dto.OrderDTO;
import com.mao.order.entity.OrderDetail;
import com.mao.order.entity.OrderMaster;
import com.mao.order.enums.OrderStatusEnum;
import com.mao.order.enums.PayStatusEnum;
import com.mao.order.service.OrderService;
import com.mao.order.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderDetailDao orderDetailRepository;

    @Autowired
    private OrderMasterDao orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        //TODO 查询商品信息（调用商品服务）
        //TODO 计算总价
        //TODO 扣库存（调用商品服务）

        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(KeyUtil.genUniqueKey());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(new BigDecimal(5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

}
