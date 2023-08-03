package uz.bprodevelopment.logisticsapp.base.audit;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

    @Bean
    AuditorAware<Long> auditorProvider(){
        return new AuditorAwareImpl();
    }

}
