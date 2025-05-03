package com.tonorganisation.check_common_lib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.tonorganisation.check_common_lib.aspect.ExecutionTimeAspect;
import com.tonorganisation.check_common_lib.aspect.AuditAspect;
import com.tonorganisation.check_common_lib.aspect.RetryAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
public class CheckCommonAutoConfiguration {


    @Bean
    @ConditionalOnProperty(prefix = "check-common", name = "audit-enabled", havingValue = "true")
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }


    @Bean
    @ConditionalOnProperty(prefix = "check-common", name = "log-execution-time", havingValue = "true")
    public ExecutionTimeAspect executionTimeAspect() {
        return new ExecutionTimeAspect();
    }


    @Bean
    @ConditionalOnProperty(prefix = "check-common.retry", name = "enabled", havingValue = "true")
    public RetryAspect retryAspect() {
        return new RetryAspect();
    }
}
