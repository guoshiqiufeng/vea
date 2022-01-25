package com.gitee.fubluesky.vea.system.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.gitee.fubluesky.kernel.db.api.pojo.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author
 * @since 2020-07-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("log")
public class LogDomain extends BaseDomain {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 用户操作
	 */
	private String operation;

	/**
	 * 请求方法
	 */
	private String method;

	/**
	 * 请求参数
	 */
	private String params;

	/**
	 * 执行时长(毫秒)
	 */
	private Long time;

	/**
	 * IP地址
	 */
	private String ip;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createDate;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 学校id
	 */
	private Long schoolId;

	/**
	 * 平台类型（1：后台端，2：班牌端，3：app端，4：小程序端）
	 */
	private Integer type;

	/**
	 * 操作模块
	 */
	private String module;

	/**
	 * 动作
	 */
	private String motion;

	@TableField(exist = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;

	@TableField(exist = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTime;

}