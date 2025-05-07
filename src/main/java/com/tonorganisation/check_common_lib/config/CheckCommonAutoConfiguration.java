package com.tonorganisation.check_common_lib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.tonorganisation.check_common_lib.aspect.ExecutionTimeAspect;
import com.tonorganisation.check_common_lib.aspect.AuditAspect;
import com.tonorganisation.check_common_lib.aspect.RetryAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Configuration automatique des aspects disponibles dans la librairie check-common-lib.
 *
 * <p>Cette classe est annotée avec {@code @Configuration}, ce qui permet à Spring
 * de la reconnaître comme une source de définitions de beans.
 * Elle conditionne l'activation de certains aspects en fonction des propriétés
 * définies dans le fichier de configuration de l'application hôte.
 */
@Configuration
public class CheckCommonAutoConfiguration {

    /**
     * Crée un bean {@link AuditAspect} si la propriété
     * {@code check-common.audit-enabled=true} est présente.
     *
     * @return une instance de {@link AuditAspect}
     */
    @Bean
    @ConditionalOnProperty(prefix = "check-common", name = "audit-enabled", havingValue = "true")
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }

    /**
     * Crée un bean {@link ExecutionTimeAspect} si la propriété
     * {@code check-common.log-execution-time=true} est activée.
     *
     * @return une instance de {@link ExecutionTimeAspect}
     */
    @Bean
    @ConditionalOnProperty(prefix = "check-common", name = "log-execution-time", havingValue = "true")
    public ExecutionTimeAspect executionTimeAspect() {
        return new ExecutionTimeAspect();
    }

    /**
     * Crée un bean {@link RetryAspect} si la propriété
     * {@code check-common.retry.enabled=true} est activée.
     *
     * @return une instance de {@link RetryAspect}
     */
    @Bean
    @ConditionalOnProperty(prefix = "check-common.retry", name = "enabled", havingValue = "true")
    public RetryAspect retryAspect() {
        return new RetryAspect();
    }
}
