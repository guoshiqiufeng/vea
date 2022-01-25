package com.gitee.fubluesky.vea.system.api;

import com.gitee.fubluesky.vea.system.api.domain.Group;

/**
 * 组
 *
 * @author yanghq
 * @version 1.0
 * @since 2021-10-26 14:52
 */
public interface GroupServiceApi {

	/**
	 * 保存组
	 * @param name 组名
	 * @param ownerId 第三方id
	 */
	Group saveGroup(String name, Long ownerId);

}
