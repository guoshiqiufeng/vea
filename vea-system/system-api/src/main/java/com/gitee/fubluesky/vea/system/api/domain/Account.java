package com.gitee.fubluesky.vea.system.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.gitee.fubluesky.kernel.db.api.pojo.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 账号库
 * </p>
 *
 * @author yanghq
 * @since 2021-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_account")
public class Account extends BaseDomain {

	private static final long serialVersionUID = 4457531235344415933L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	private String userName;

	/**
	 * 账号库使用状态 0=未分配；1=已经分配；2=保留
	 */
	private Integer state;

	/**
	 * 被使用的时间
	 */
	private LocalDateTime applyDate;

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

}