package com.mao.user.utils;


import com.mao.user.enums.ResultEnum;
import com.mao.user.vo.ResultVO;

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
     * 成功返回结果
     * @return
     */
    public static ResultVO success(){
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg("成功");
        resultVO.setCode(0);
        return  resultVO;
    }


    /**
     * 返回失败或者异常
     * @return
     */
    public static ResultVO error(ResultEnum resultEnum){
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(resultEnum.getMessage());
        resultVO.setCode(resultEnum.getCode());
        return  resultVO;
    }
}
