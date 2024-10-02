package com.naveen.rentread.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.naveen.rentread.domain")
@EnableJpaRepositories("com.naveen.rentread.repos")
@EnableTransactionManagement
public class DomainConfig {
}
