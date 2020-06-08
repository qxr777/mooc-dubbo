package edu.whut.cs.jee.mooc;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
@EnableDubboConfiguration
public class MoocClassServiceApplication implements ApplicationRunner {

    public static void main(String[] args) throws IOException {


        SpringApplication.run(MoocClassServiceApplication.class, args);
//        System.in.read(); // press any key to exit

        args = new String[] { "spring" };
        com.alibaba.dubbo.container.Main.main(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("All MoocClass: {}");

    }
}
