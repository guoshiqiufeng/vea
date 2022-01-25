package com.gitee.fubluesky.vea.system.autoconfigure;

import com.gitee.fubluesky.kernel.core.exception.ServiceException;
import com.gitee.fubluesky.kernel.core.exception.enums.defaults.DefaultBusinessExceptionEnum;
import com.gitee.fubluesky.kernel.core.exception.enums.http.ServletExceptionEnum;
import com.gitee.fubluesky.kernel.security.api.exception.enums.SecurityExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局业务异常处理
 *
 * @author yanghq
 */
@Slf4j
@ControllerAdvice
public class BusinessExceptionHandler {

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<?> noHandlerFoundException(HttpServletRequest request, Exception ex) {
		Map<String, Object> error = new HashMap<>(2);
		error.put("code", Integer.parseInt(ServletExceptionEnum.NOT_FOUND_ERROR.getCode()));
		error.put("message", ServletExceptionEnum.NOT_FOUND_ERROR.getMessage());
		return new ResponseEntity<>(error, HttpStatus.OK);
	}

	@ExceptionHandler({ ServiceException.class, Exception.class })
	public ResponseEntity<?> handlerException(HttpServletRequest request, Exception ex) {
		Map<String, Object> error = new HashMap<>(2);

		ex.printStackTrace();

		// 访问权限异常
		if (ex instanceof AccessDeniedException) {
			error.put("code", Integer.parseInt(SecurityExceptionEnum.PERMISSION_NO_ACCESS.getCode()));
			error.put("message", SecurityExceptionEnum.PERMISSION_NO_ACCESS.getMessage());
			log.warn("[访问权限异常]\r\n业务编码：{}\r\n异常记录：{}", error.get("code"), error.get("message"));
		}
		// 业务异常
		else if (ex instanceof ServiceException) {
			error.put("code", ((ServiceException) ex).getCode());
			error.put("message", ex.getMessage());
			log.warn("[全局业务异常]\r\n业务编码：{}\r\n异常记录：{}", error.get("code"), error.get("message"));
		}

		// 统一处理 JSON 参数验证
		else if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
			BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
			String msg = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).distinct()
					.collect(Collectors.joining(","));
			error.put("code", HttpStatus.BAD_REQUEST.value());
			error.put("message", msg);
		}

		// 统一处理表单绑定验证
		else if (ex instanceof BindException) {
			BindException bindException = (BindException) ex;
			BindingResult bindingResult = bindException.getBindingResult();
			String msg = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
					.distinct().collect(Collectors.joining(","));
			error.put("code", HttpStatus.BAD_REQUEST.value());
			error.put("message", msg);
		}

		// 未知错误
		else {
			error.put("code", Integer.parseInt(DefaultBusinessExceptionEnum.SYSTEM_UNKNOWN_ERROR.getCode()));
			error.put("message", DefaultBusinessExceptionEnum.SYSTEM_UNKNOWN_ERROR.getMessage());
			ex.printStackTrace();
			log.error(ex.getMessage());
		}

		return new ResponseEntity<>(error, HttpStatus.OK);
	}

}
