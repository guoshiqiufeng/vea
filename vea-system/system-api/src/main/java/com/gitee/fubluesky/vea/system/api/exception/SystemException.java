package com.gitee.fubluesky.vea.system.api.exception;

import com.gitee.fubluesky.kernel.core.exception.AbstractExceptionEnum;
import com.gitee.fubluesky.kernel.core.exception.ServiceException;
import com.gitee.fubluesky.vea.system.api.constants.SystemConstants;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-13 15:27
 */
public class SystemException extends ServiceException {

	public SystemException(AbstractExceptionEnum exception) {
		super(SystemConstants.SYSTEM_MODULE_NAME, exception);
	}

	public SystemException(AbstractExceptionEnum exception, String message) {
		super(SystemConstants.SYSTEM_MODULE_NAME, exception.getCode(), message);
	}

	public SystemException(String code, String message) {
		super(SystemConstants.SYSTEM_MODULE_NAME, code, message);
	}

}
