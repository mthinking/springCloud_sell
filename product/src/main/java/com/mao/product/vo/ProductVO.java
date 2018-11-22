package com.mao.product.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品（包含类目）
 */
@Data
public class ProductVO {
    /**
     * 类目名称
     */
    @JsonProperty("name")
    private String categoryName;
    /**
     *类目区别
     */
    @JsonProperty("type")
    private Integer categoryType;

    /**
     * 商品列表
     */
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
