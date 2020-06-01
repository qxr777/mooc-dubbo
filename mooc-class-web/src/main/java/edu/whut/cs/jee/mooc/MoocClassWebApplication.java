package edu.whut.cs.jee.mooc;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
//@EnableTransactionManagement
@SpringBootApplication
@EnableDubboConfiguration
public class MoocClassWebApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(MoocClassWebApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("All MoocClass: {}");

    }
}
