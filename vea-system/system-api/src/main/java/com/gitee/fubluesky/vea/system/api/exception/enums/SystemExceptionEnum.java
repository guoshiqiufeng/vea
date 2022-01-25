package com.gitee.fubluesky.vea.system.api.exception.enums;

import com.gitee.fubluesky.kernel.core.enums.ErrorType;
import com.gitee.fubluesky.kernel.core.exception.AbstractExceptionEnum;
import com.gitee.fubluesky.vea.system.api.constants.SystemConstants;
import lombok.Getter;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-13 15:30
 */
@Getter
public enum SystemExceptionEnum implements AbstractExceptionEnum {

	/**
	 * 账号创建失败
	 */
	ACCOUNT_CREATED_ERROR(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "10",
			"账号创建失败{}条！"),

	/**
	 * 菜单更新失败
	 */
	MENU_UPDATE_ERROR(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "20", "菜单更新失败"),

	/**
	 * 角色保存失败
	 */
	ROLE_SAVE_ERROR(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "30", "角色保存失败"),

	/**
	 * 角色更新失败
	 */
	ROLE_UPDATE_ERROR(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "31", "角色更新失败"),

	/**
	 * 角色权限越界
	 */
	ROLE_PERMISSION_OUT(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "32",
			"角色权限越界"),

	/**
	 * 角色不存在
	 */
	ROLE_UN_EXIST(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "33", "{}角色不存在"),
	/**
	 * 账号不存在或密码错误
	 */
	USER_LOGIN_ERROR(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "40",
			"账号不存在或密码错误"),

	/**
	 * 密码错误
	 */
	USER_PASSWORD_ERROR(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "40", "密码错误"),

	/**
	 * 账号已被禁用
	 */
	USER_ACCOUNT_FORBIDDEN(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "41",
			"账号已被禁用"),

	/**
	 * 用户未激活
	 */
	USER_UN_ACTIVE(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "42", "用户未激活"),

	/**
	 * 当前用户不存在
	 */
	CURRENT_USER_UN_EXIST(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "43",
			"当前用户不存在"),

	/**
	 * 用户不存在
	 */
	USER_UN_EXIST(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "43", "用户不存在"),

	/**
	 * 用户更新失败
	 */
	USER_UPDATE_ERROR(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "44", "用户更新失败"),

	/**
	 * 不能删除当前用户
	 */
	UN_DELETE_CURRENT_USER(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "45",
			"不能删除当前用户"),

	/**
	 * 用户账号权限不足
	 */
	USER_ACCOUNT_PERMISSIONS_ERROR(ErrorType.BUSINESS_ERROR.getCode(),
			SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "49", "用户账号权限不足"),

	/**
	 * 手机号已存在
	 */
	MOBILE_HAS_EXISTED(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "50", "手机号已存在"),

	/**
	 * 组已存在
	 */
	GROUP_HAS_EXISTED(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "50", "组已存在"),

	/**
	 * 组不存在
	 */
	GROUP_UN_EXIST(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "51", "组不存在"),

	/**
	 * 上传文件不能为空
	 */
	UPLOAD_FILE_NOT_EMPTY(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "70",
			"上传文件不能为空"),

	/**
	 * 上传文件名不能为空
	 */
	UPLOAD_FILE_NAME_NOT_EMPTY(ErrorType.BUSINESS_ERROR.getCode(), SystemConstants.SYSTEM_EXCEPTION_STEP_CODE + "71",
			"上传文件名不能为空"),

	;

	/**
	 * 异常分类 用户端异常: 1 业务异常: 2 第三方异常: 3
	 */
	private final String typeCode;

	/**
	 * 错误编码
	 */
	private final String code;

	/**
	 * 错误信息
	 */
	private final String message;

	SystemExceptionEnum(String typeCode, String code, String message) {
		this.typeCode = typeCode;
		this.code = code;
		this.message = message;
	}

}
