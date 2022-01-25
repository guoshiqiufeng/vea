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
 * 系统用户
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
public class User extends BaseDomain {

	private static final long serialVersionUID = -4059009976728282757L;

	/**
	 * 用户id
	 */
	@TableId(value = "user_id", type = IdType.AUTO)
	private Long userId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 用户昵称
	 */
	private String nickname;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 用户头像
	 */
	private String userIcon;

	/**
	 * 用户类别 ：1 管理员 2普通用户
	 */
	private Integer userType;

	/**
	 * 状态 0：禁用 1：正常 2 : 未激活
	 */
	private Integer status;

	/**
	 * 创建人id
	 */
	private Long createUserId;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createDate;

	/**
	 * 修改人id
	 */
	private Long updateUserId;

	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime modifyDate;

	@TableField(exist = false)
	private List<Long> roleIdList;

	@TableField(exist = false)
	private Long roleId;

	@TableField(exist = false)
	private String token;

	@TableField(exist = false)
	private String deptName;

}