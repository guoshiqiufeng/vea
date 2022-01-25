package com.gitee.fubluesky.vea.system.api.enums;

import lombok.Getter;

/**
 * 菜单类型
 * @author yanghq
 * @version 1.0
 * @since 2021-08-14 15:24
 */
@Getter
public enum MenuTypeEnum {

	/**
	 * 目录
	 */
	CATALOG(0, "目录"),

	/**
	 * 菜单
	 */
	MENU(1, "目录"),
	/**
	 * 按钮
	 */
	BUTTON(2, "按钮"),;

	/**
	 * 值
	 */
	private final int value;

	/**
	 * 名称
	 */
	private final String name;

	MenuTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

}
