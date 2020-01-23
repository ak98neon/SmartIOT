package com.smart.iot.web;

import com.smart.iot.code.BarCodeNotFoundException;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

  private Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

  @ExceptionHandler(value = BarCodeNotFoundException.class)
  @ResponseBody
  public String barCodNotFoundException(HttpServletRequest request,
      BarCodeNotFoundException exception) {
    return exception.getMessage();
  }


  @ExceptionHandler(value = Exception.class)
  public String handleException(HttpServletRequest request, Exception e, Model model) {
    logger.info(e.getMessage());
    HttpStatus status = getStatus(request);
    model.addAttribute("errorMsg", "Error: " + e.getMessage() + " status code: " + status);
    return "error/errorPage";
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return HttpStatus.valueOf(statusCode);
  }
}
