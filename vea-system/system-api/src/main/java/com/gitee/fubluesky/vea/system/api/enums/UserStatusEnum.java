package com.gitee.fubluesky.vea.system.api.enums;

import lombok.Getter;

/**
 * 用户状态
 * @author yanghq
 * @version 1.0
 * @since 2021-08-16 14:54
 */
@Getter
public enum UserStatusEnum {

	/**
	 * 禁用
	 */
	DISABLE(0, "禁用"),
	/**
	 * 启用
	 */
	ENABLE(1, "启用"),
	/**
	 * 未激活
	 */
	UN_ACTIVE(2, "未激活"),;

	/**
	 * 值
	 */
	private final int value;

	/**
	 * 名称
	 */
	private final String name;

	UserStatusEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

}
