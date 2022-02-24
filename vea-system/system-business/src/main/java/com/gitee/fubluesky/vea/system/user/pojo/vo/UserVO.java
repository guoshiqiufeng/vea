package com.gitee.fubluesky.vea.system.user.pojo.vo;

import com.gitee.fubluesky.vea.system.api.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-17 10:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends User implements Serializable {

	private static final long serialVersionUID = -7134473305130660914L;

	private Long[] schoolIds;

}
