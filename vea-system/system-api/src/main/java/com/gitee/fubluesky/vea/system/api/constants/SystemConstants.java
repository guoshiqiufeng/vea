package com.gitee.fubluesky.vea.system.api.constants;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-13 15:25
 */
public interface SystemConstants {

	/**
	 * 模块的名称
	 */
	String SYSTEM_MODULE_NAME = "vea-system";

	/**
	 * 异常枚举的步进值
	 */
	String SYSTEM_EXCEPTION_STEP_CODE = "50";

	/**
	 * 菜单缓存前缀
	 */
	String MENU_CACHE_KEY_PREFIX = "SYSTEM_MENU_CACHE_KEY";

	/**
	 * 菜单导航缓存前缀
	 */
	String MENU_NAV_CACHE_KEY_PREFIX = "SYSTEM_NAV_MENU_CACHE_KEY";

	/**
	 * 用户权限缓存前缀
	 */
	String USER_PERMISSIONS_CACHE_KEY_PREFIX = "USER_PERMISSIONS_CACHE_KEY";

	/**
	 * 系统管理员 id
	 */
	Long SUPER_ADMIN_ID = 1L;

	/**
	 * 角色前缀
	 */
	String ROLE = "ROLE_";

	/**
	 * APP_ID
	 */
	String APP_ID = "appId";

	/**
	 * APP_ID 手机端
	 */
	String APP_ID_MOBILE = "app_mobile";

	/**
	 * APP_ID h5
	 */
	String APP_ID_H5 = "app_h5";

	/**
	 * APP_ID web
	 */
	String APP_ID_WEB = "web";

}
