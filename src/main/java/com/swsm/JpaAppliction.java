package com.swsm;

import com.swsm.platform.domain.jpa.repository.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.swsm")
@EnableJpaRepositories(basePackages = "com.swsm.linkmes.domain.repository",repositoryBaseClass = BaseRepositoryImpl.class)
public class JpaAppliction {

    public static void main(String[] args) {
        SpringApplication.run(JpaAppliction.class, args);
    }

}
