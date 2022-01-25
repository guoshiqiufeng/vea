package com.gitee.fubluesky.vea.system.group.service;

import com.gitee.fubluesky.kernel.db.mybatisplus.pojo.IBaseService;
import com.gitee.fubluesky.vea.system.api.domain.GroupMenu;

import java.util.List;

/**
 * <p>
 * 组菜单 服务类
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface IGroupMenuService extends IBaseService<GroupMenu> {

	/**
	 * 获取菜单列表
	 * @param groupId 组id
	 * @return 菜单id数组
	 */
	List<Long> queryMenuIdList(Long groupId);

	/**
	 * 保存组菜单
	 * @param domain 组菜单对象
	 * @return 是否成功
	 */
	boolean saveGroupMenu(GroupMenu domain);

}
