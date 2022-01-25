package com.gitee.fubluesky.vea.system.api.enums;

import lombok.Getter;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-20 14:05
 */
@Getter
public enum UserTypeEnum {

	/**
	 * 管理员
	 */
	ADMIN(1, "管理员"),
	/**
	 * 普通用户
	 */
	TEACHER(2, "教师"),;

	/**
	 * 值
	 */
	private final int value;

	/**
	 * 名称
	 */
	private final String name;

	UserTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

}
