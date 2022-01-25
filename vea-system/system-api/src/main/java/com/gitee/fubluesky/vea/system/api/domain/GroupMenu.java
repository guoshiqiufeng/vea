package com.gitee.fubluesky.vea.system.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.gitee.fubluesky.kernel.db.api.pojo.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 组菜单
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_group_menu")
public class GroupMenu extends BaseDomain {

	private static final long serialVersionUID = -6524330913602355616L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 组id
	 */
	private Long groupId;

	/**
	 * 菜单id
	 */
	private Long menuId;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createDate;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime modifyDate;

	@TableField(exist = false)
	private List<Long> menuIdList;

}