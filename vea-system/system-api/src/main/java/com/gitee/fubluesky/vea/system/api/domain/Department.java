package com.gitee.fubluesky.vea.system.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.gitee.fubluesky.kernel.db.api.pojo.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 部门
 * </p>
 *
 * @author yanghq
 * @since 2020-07-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_department")
public class Department extends BaseDomain {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 名称
	 */
	private String name;

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

}