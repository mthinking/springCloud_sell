package com.mao.order.utils;


import com.mao.order.vo.ResultVO;

public class ResultVOUtil {
    /**
     * 成功返回结果
     * @param object
     * @return
     */
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setMsg("成功");
        resultVO.setCode(0);
        return  resultVO;
    }

    /**
     * 数据为空
     * @return
     */
    public  static ResultVO success(){
        return success(null);
    }

    /**
     * 返回失败或者异常
     * @param code
     * @param msg
     * @return
     */
    public static ResultVO error(Integer code, String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return  resultVO;
    }
}
