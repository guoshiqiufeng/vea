package com.gitee.fubluesky.vea.system.autoconfigure;

import com.gitee.fubluesky.kernel.cache.redis.utils.RedisCacheUtils;
import com.gitee.fubluesky.vea.system.api.domain.Menu;
import com.gitee.fubluesky.vea.system.menu.cache.GroupMenuRedisCache;
import com.gitee.fubluesky.vea.system.menu.cache.MenuNavRedisCache;
import com.gitee.fubluesky.vea.system.menu.vo.MenuNavVO;
import com.gitee.fubluesky.vea.system.user.cache.UserPermissionsCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-18 16:57
 */
@Configuration
public class SystemCacheAutoConfiguration {

	@Bean
	public RedisTemplate<String, List<Menu>> groupMenuRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheUtils.getObjectRedisTemplate(redisConnectionFactory);
	}

	@Bean
	@ConditionalOnMissingBean(GroupMenuRedisCache.class)
	public GroupMenuRedisCache groupMenuRedisCache(RedisTemplate<String, List<Menu>> groupMenuRedisTemplate) {
		return new GroupMenuRedisCache(groupMenuRedisTemplate);
	}

	@Bean
	public RedisTemplate<String, MenuNavVO> menuNavRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheUtils.getObjectRedisTemplate(redisConnectionFactory);
	}

	@Bean
	@ConditionalOnMissingBean(MenuNavRedisCache.class)
	public MenuNavRedisCache menuNavRedisCache(RedisTemplate<String, MenuNavVO> menuNavRedisTemplate) {
		return new MenuNavRedisCache(menuNavRedisTemplate);
	}

	@Bean
	public RedisTemplate<String, Set<String>> userPermissionsRedisTemplate(
			RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheUtils.getObjectRedisTemplate(redisConnectionFactory);
	}

	@Bean
	@ConditionalOnMissingBean(UserPermissionsCache.class)
	public UserPermissionsCache userPermissionsRedisCache(RedisTemplate<String, Set<String>> userPermissionsRedisTemplate) {
		return new UserPermissionsCache(userPermissionsRedisTemplate);
	}

}
