package blog.zalin.community.community.advice;

import blog.zalin.community.community.dto.ResultDTO;
import blog.zalin.community.community.exception.CustomizeErrorCode;
import blog.zalin.community.community.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandle {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                  Throwable e,
                  Model model,
                  HttpServletResponse response){
        String contentType = request.getContentType();

        if("application/json".equals(contentType)){
            //fanhui json
            ResultDTO resultDTO=null;


            if (e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }else{
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }

            try {
                response.setContentType("applacation/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();


            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }else{
            //error page
            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else{
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }

        }
        return new ModelAndView("error");

    }

    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
