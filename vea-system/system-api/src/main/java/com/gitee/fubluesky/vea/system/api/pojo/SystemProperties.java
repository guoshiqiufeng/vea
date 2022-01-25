package com.gitee.fubluesky.vea.system.api.pojo;

import lombok.Data;
import org.springframework.util.DigestUtils;

import java.io.Serializable;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-17 11:25
 */
@Data
public class SystemProperties implements Serializable {

	/**
	 * 默认用户密码
	 */
	private String userPassword = "123456";

	/**
	 * 默认密码 盐
	 */
	private String userPasswordSalt = "vea";

	/**
	 * 默认用户头像
	 */
	private String userIcon = "";

	public String getUserPassword() {
		String pass = userPassword + userPasswordSalt;
		return DigestUtils.md5DigestAsHex(pass.getBytes());
	}

}
