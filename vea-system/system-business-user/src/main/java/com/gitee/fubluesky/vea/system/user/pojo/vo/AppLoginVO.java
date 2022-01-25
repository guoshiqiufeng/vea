package com.gitee.fubluesky.vea.system.user.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * app登录返回信息
 *
 * @author yhq
 * @since 2021-03-13 11:39
 */
@Data
public class AppLoginVO implements Serializable {

	private static final long serialVersionUID = -4069056524601744729L;

	private Long id;

	private String userName;

	private String nickName;

	private String userIcon;

	private Integer userType;

	private Long schoolId;

	private String schoolName;

	private String token;

}