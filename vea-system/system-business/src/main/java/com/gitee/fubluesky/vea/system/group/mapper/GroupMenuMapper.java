package com.gitee.fubluesky.vea.system.group.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.fubluesky.vea.system.api.domain.GroupMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组菜单 Mapper 接口
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
public interface GroupMenuMapper extends BaseMapper<GroupMenu> {

	/**
	 * 获取组菜单
	 * @param groupId 组id
	 * @return 菜单id 数组
	 */
	List<Long> queryMenuIdList(@Param("groupId") Long groupId);

	/**
	 * 删除组菜单
	 * @param groupId 组id
	 * @return 影响记录行
	 */
	int deleteBatch(Long[] groupId);

}
