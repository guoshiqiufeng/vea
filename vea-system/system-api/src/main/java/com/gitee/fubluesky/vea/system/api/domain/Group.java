package com.gitee.fubluesky.vea.system.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.gitee.fubluesky.kernel.db.api.pojo.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户组
 * </p>
 *
 * @author yanghq
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_group")
public class Group extends BaseDomain {

	private static final long serialVersionUID = 2507712538621053593L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	private Long ownerId;

	private String name;

	/**
	 * 组类型 0=System
	 */
	private Integer type;

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

}