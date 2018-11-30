package com.mao.user.vo;

import lombok.Data;

/**
 * Http请求返回最外层对象
 */
@Data
public class ResultVO<T>  {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据内容
     */
    private T data;
}
