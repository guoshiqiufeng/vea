package com.gitee.fubluesky.vea.system.group.service;

import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.IBaseService;
import com.gitee.fubluesky.vea.system.api.domain.Group;

import java.util.List;

/**
 * <p>
 * 用户组 服务类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface IGroupService extends IBaseService<Group> {

	/**
	 * 获取组
	 * @param userId 用户id
	 * @param type 组类别
	 * @return 组列表
	 */
	List<Group> findUserGroups(Long userId, int type);

	/**
	 * 获取组
	 * @param ownerId 第三方id
	 * @param type 组类别
	 * @return 组列表
	 */
	List<Group> findGroups(Long ownerId, int type);

}
