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
 * 部门用户表
 * </p>
 *
 * @author yanghq
 * @since 2020-07-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_department_user")
public class DepartmentUser extends BaseDomain {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 部门id
	 */
	private Long departmentId;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 是否为管理员
	 */
	private Boolean isAdmin;

	/**
	 * 创建日期
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

	@TableField(exist = false)
	private String userName;

	@TableField(exist = false)
	private String userNickName;

	@TableField(exist = false)
	private String email;

	@TableField(exist = false)
	private String mobile;

	/**
	 * 状态 0：禁用 1：正常
	 */
	@TableField(exist = false)
	private Integer status;

	@TableField(exist = false)
	private String departmentName;

	@TableField(exist = false)
	private List<Long> roleIdList;

}