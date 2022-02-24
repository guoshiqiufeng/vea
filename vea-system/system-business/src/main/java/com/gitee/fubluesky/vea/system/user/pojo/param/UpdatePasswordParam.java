package com.gitee.fubluesky.vea.system.user.pojo.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-17 10:37
 */
@Data
public class UpdatePasswordParam implements Serializable {

	private static final long serialVersionUID = 2896090148082211138L;

	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	private String password;

	/**
	 * 新密码
	 */
	@NotBlank(message = "新密码不能为空")
	private String newPassword;

}
