package com.gitee.fubluesky.vea.system.user.cache;

import com.gitee.fubluesky.kernel.cache.redis.AbstractRedisCacheOperator;
import com.gitee.fubluesky.vea.system.api.constants.SystemConstants;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-19 17:52
 */
public class UserPermissionsCache extends AbstractRedisCacheOperator<Set<String>> {

	public UserPermissionsCache(RedisTemplate<String, Set<String>> redisTemplate) {
		super(redisTemplate);
	}

	@Override
	public void add(String key, Set<String> value) {
		super.add(key, value, 60 * 60L);
	}

	/**
	 * 获取缓存的前缀
	 * @return 缓存前缀
	 */
	@Override
	public String getKeyPrefix() {
		return SystemConstants.USER_PERMISSIONS_CACHE_KEY_PREFIX;
	}

}
