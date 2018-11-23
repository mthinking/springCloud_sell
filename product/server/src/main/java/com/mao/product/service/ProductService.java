package com.mao.product.service;


import com.mao.product.common.DecreaseStockInput;
import com.mao.product.common.ProductInfoOutput;
import com.mao.product.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 */
public interface ProductService {
    ProductInfo getOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<DecreaseStockInput> cartDTOList);

    //减库存
    void  decreaseStock(List<DecreaseStockInput> cartDTOList);

    List<ProductInfoOutput> findList(List<String> productIdList);
}
