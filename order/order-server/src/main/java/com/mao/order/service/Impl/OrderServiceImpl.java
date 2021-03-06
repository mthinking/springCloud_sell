package com.mao.order.service.Impl;

import com.mao.order.dao.OrderDetailDao;
import com.mao.order.dao.OrderMasterDao;
import com.mao.order.dto.OrderDTO;
import com.mao.order.entity.OrderDetail;
import com.mao.order.entity.OrderMaster;
import com.mao.order.enums.OrderStatusEnum;
import com.mao.order.enums.PayStatusEnum;
import com.mao.order.enums.ResultEnum;
import com.mao.order.exception.OrderException;
import com.mao.order.service.OrderService;
import com.mao.order.utils.KeyUtil;
import com.mao.product.client.ProductClient;
import com.mao.product.common.DecreaseStockInput;
import com.mao.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private ProductClient productClient;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        // 查询商品信息（调用商品服务）
        String orderId = KeyUtil.genUniqueKey();
        List<String> productIdList = orderDTO.getOrderDetailList().stream().map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfoOutput> ProductInfoList = productClient.listForOrder(productIdList);
        //计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            for (ProductInfoOutput productInfo : ProductInfoList){
                if (productInfo.getProductId().equals(orderDetail.getProductId())){
                   orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
                BeanUtils.copyProperties(productInfo,orderDetail);
                orderDetail.setOrderId(orderId);
                orderDetail.setDetailId(KeyUtil.genUniqueKey());
                //订单详情入库
                orderDetailDao.save(orderDetail);
                }
            }
        }
        // 扣库存（调用商品服务）
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e ->new DecreaseStockInput(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);
        return orderDTO;
    }

    /**
     * 完结订单(只能卖家操作)
     *
     * @param orderId
     */
    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        //1.查询订单
        Optional<OrderMaster> orderMasterOptional = orderMasterDao.findById(orderId);
        if (!orderMasterOptional.isPresent()){
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.判断订单状态
        OrderMaster orderMaster = orderMasterOptional.get();
        if (OrderStatusEnum.NEW.getCode() != orderMaster.getOrderStatus()){
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3.修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterDao.save(orderMaster);

        //查询订单详情
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

}
