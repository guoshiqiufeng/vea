package com.gitee.fubluesky.vea.system.api.enums;

import lombok.Getter;

/**
 * 角色
 * @author yanghq
 * @version 1.0
 * @since 2021-10-26 15:10
 */
@Getter
public enum RoleEnum {

	/**
	 * 校管
	 */
	SCHOOL_ADMIN(1, "SCHOOL_ADMIN", "校管"),
	/**
	 * 科任教师
	 */
	SUBJECT_TEACHER(2, "SUBJECT_TEACHER", "科任教师");

	/**
	 * id
	 */
	private int id;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	RoleEnum(int id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public static RoleEnum getValue(int id) {
		for (RoleEnum role : values()) {
			if (role.getId() == id) {
				return role;
			}
		}
		return null;
	}

}
