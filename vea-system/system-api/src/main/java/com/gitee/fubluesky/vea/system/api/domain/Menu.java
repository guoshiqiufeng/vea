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
 * 菜单管理
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_menu")
public class Menu extends BaseDomain {

	private static final long serialVersionUID = -3842773910845372698L;

	@TableId(value = "menu_id", type = IdType.AUTO)
	private Long menuId;

	/**
	 * 父菜单ID，一级菜单为0
	 */
	private Long parentId;

	private String name;

	private String url;

	private String perms;

	/**
	 * 类型 0：目录 1：菜单 2：按钮 3: app资源
	 */
	private Integer type;

	private String icon;

	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createDate;

	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime modifyDate;

	@TableField(fill = FieldFill.INSERT)
	private Boolean isDeleted;

	/**
	 * ztree属性
	 */
	@TableField(exist = false)
	private Boolean open;

	@TableField(exist = false)
	private List<?> children;

}