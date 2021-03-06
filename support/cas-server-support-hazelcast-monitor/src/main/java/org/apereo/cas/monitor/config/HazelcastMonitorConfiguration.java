package org.apereo.cas.monitor.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.monitor.HazelcastHealthIndicator;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This is {@link HazelcastMonitorConfiguration}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Configuration("hazelcastMonitorConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class HazelcastMonitorConfiguration {

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("casHazelcastInstance")
    private HazelcastInstance hazelcastInstance;

    @Bean
    @RefreshScope
    public HealthIndicator hazelcastHealthIndicator() {
        return new HazelcastHealthIndicator(
            casProperties.getMonitor().getWarn().getEvictionThreshold(),
            casProperties.getMonitor().getWarn().getThreshold(),
            hazelcastInstance
        );
    }
}
