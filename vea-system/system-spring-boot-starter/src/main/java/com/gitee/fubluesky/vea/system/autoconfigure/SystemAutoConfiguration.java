package com.gitee.fubluesky.vea.system.autoconfigure;

import com.gitee.fubluesky.vea.system.api.pojo.SystemProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-08-17 13:33
 */
@Configuration
public class SystemAutoConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "system")
	@ConditionalOnMissingBean(SystemProperties.class)
	public SystemProperties systemProperties() {
		return new SystemProperties();
	}

}
