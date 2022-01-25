package com.gitee.fubluesky.vea.system.user.pojo.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录参数
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-05-10 17:50
 */
@Data
@Accessors(chain = true)
public class LoginParam implements Serializable {

	private static final long serialVersionUID = -2885440127846265229L;

	/**
	 * 账号
	 */
	@ApiParam(value = "账号", required = true)
	@NotBlank(message = "账号不能为空")
	private String username;

	/**
	 * 密码
	 */
	@ApiParam(value = "密码", required = true)
	@NotBlank(message = "密码不能为空")
	private String password;

}
