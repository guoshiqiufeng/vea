package com.gitee.fubluesky.vea.system.menu.cache;

import com.gitee.fubluesky.kernel.cache.redis.AbstractRedisCacheOperator;
import com.gitee.fubluesky.vea.system.api.constants.SystemConstants;
import com.gitee.fubluesky.vea.system.api.domain.Menu;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-18 10:27
 */
public class GroupMenuRedisCache extends AbstractRedisCacheOperator<List<Menu>> {

	public GroupMenuRedisCache(RedisTemplate<String, List<Menu>> redisTemplate) {
		super(redisTemplate);
	}

	@Override
	public void add(String key, List<Menu> value) {
		super.add(key, value, 60 * 60L);
	}

	/**
	 * 获取缓存的前缀
	 * @return 缓存前缀
	 */
	@Override
	public String getKeyPrefix() {
		return SystemConstants.MENU_CACHE_KEY_PREFIX;
	}

}
