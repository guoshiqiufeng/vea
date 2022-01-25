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
 * 角色
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
public class Role extends BaseDomain {

	private static final long serialVersionUID = 5527911779899221080L;

	/**
	 * 角色id
	 */
	@TableId(value = "role_id", type = IdType.AUTO)
	private Long roleId;

	/**
	 * 组id
	 */
	private Long groupId;

	/**
	 * 角色编码
	 */
	private String code;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 系统默认的角色（=0）还是管理员自定义的角色（=1）如果是系统默认的角色，则不允许管理员进行修改
	 */
	private Integer defaultRole;

	/**
	 * 用户类别：：1 管理员
	 */
	private Integer userType;

	/**
	 * 创建者ID
	 */
	private Long createUserId;

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

	@TableField(exist = false)
	private List<Long> menuIdList;

	@TableField(exist = false)
	private String type;

	@TableField(exist = false)
	private String groupName;

}