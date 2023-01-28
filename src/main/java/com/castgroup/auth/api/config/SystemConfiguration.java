package com.castgroup.auth.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.castgroup.auth.api.repository")
public class SystemConfiguration {
    
}
