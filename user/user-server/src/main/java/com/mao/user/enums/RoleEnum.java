package com.mao.user.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {

    BUYER(1,"买家"),
    SELLER(2,"卖家"),
    ;

    private  Integer code;

    private String messsage;

    RoleEnum(Integer code, String messsage) {
        this.code = code;
        this.messsage = messsage;
    }
}
