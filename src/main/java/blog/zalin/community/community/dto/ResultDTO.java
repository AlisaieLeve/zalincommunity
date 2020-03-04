package blog.zalin.community.community.dto;

import blog.zalin.community.community.exception.CustomizeErrorCode;
import blog.zalin.community.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO errorOf(CustomizeErrorCode customizeErrorCode){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(customizeErrorCode.getCode());
        resultDTO.setMessage(customizeErrorCode.getMessage());
        return resultDTO;
    }
    public static ResultDTO errorOf(CustomizeException customizeException){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(customizeException.getCode());
        resultDTO.setMessage(customizeException.getMessage());
        return resultDTO;
    }
    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }
}
