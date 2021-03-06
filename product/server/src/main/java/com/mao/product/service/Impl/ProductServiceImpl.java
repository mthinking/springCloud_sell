package com.mao.product.service.Impl;

import com.mao.product.common.DecreaseStockInput;
import com.mao.product.common.ProductInfoOutput;
import com.mao.product.dao.ProductInfoDao;
import com.mao.product.entity.ProductInfo;
import com.mao.product.enums.ProductStatusEnum;
import com.mao.product.enums.ResultEnum;
import com.mao.product.exception.ProductException;
import com.mao.product.service.ProductService;
import com.mao.product.utils.JsonUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoDao repository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public ProductInfo getOne(String productId) {
        return repository.getOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<DecreaseStockInput> cartDTOList) {
        for (DecreaseStockInput cartDTO : cartDTOList){
            ProductInfo productInfo = repository.getOne(cartDTO.getProductId());
            if (productInfo == null){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> cartDTOList) {
        List<ProductInfo> productInfoList = decreaseStockProcess(cartDTOList);

        //发送mq消息
        List<ProductInfoOutput> productInfoOutputs = productInfoList.stream().map(e ->{
            ProductInfoOutput output = new ProductInfoOutput();
            BeanUtils.copyProperties(e,output);
            return output;
        }).collect(Collectors.toList());
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutputs));
    }
    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> cartDTOList) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (DecreaseStockInput cartDTO : cartDTOList){
            ProductInfo productInfo = repository.getOne(cartDTO.getProductId());
            if (productInfo == null){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0){
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }


    @Override
    public List<ProductInfoOutput> findList(List<String> productIdList) {
        return repository.findByProductIdIn(productIdList).stream()
                .map(e -> {
                    ProductInfoOutput output = new ProductInfoOutput();
                    BeanUtils.copyProperties(e, output);
                    return output;
                })
                .collect(Collectors.toList());
    }
}
