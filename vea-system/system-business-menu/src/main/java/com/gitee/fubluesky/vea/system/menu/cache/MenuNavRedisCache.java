package com.gitee.fubluesky.vea.system.menu.cache;

import com.gitee.fubluesky.kernel.cache.redis.AbstractRedisCacheOperator;
import com.gitee.fubluesky.vea.system.api.constants.SystemConstants;
import com.gitee.fubluesky.vea.system.menu.vo.MenuNavVO;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-18 11:03
 */
public class MenuNavRedisCache extends AbstractRedisCacheOperator<MenuNavVO> {

	public MenuNavRedisCache(RedisTemplate<String, MenuNavVO> redisTemplate) {
		super(redisTemplate);
	}

	@Override
	public void add(String key, MenuNavVO value) {
		super.add(key, value, 60 * 60L);
	}

	/**
	 * 获取缓存的前缀
	 * @return 缓存前缀
	 */
	@Override
	public String getKeyPrefix() {
		return SystemConstants.MENU_NAV_CACHE_KEY_PREFIX;
	}

}
