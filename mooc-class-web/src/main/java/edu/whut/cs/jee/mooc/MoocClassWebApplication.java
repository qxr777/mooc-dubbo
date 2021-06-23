package edu.whut.cs.jee.mooc;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@SpringBootApplication
@EnableDubboConfiguration
public class MoocClassWebApplication implements ApplicationRunner {

    public static void main(String[] args) {
        int dubboPort = 0;
        int tomcatPort = 0;
        int defaultDubboPort = 10881;
        int defaultTomcatPort = 8080;


        System.out.printf("请于5秒钟内输入dubbo服务监听端口号, 推荐  %d %n", defaultDubboPort);
        Scanner scanner = new Scanner(System.in);
        String strPort = scanner.nextLine();
        dubboPort = Convert.toInt(strPort);

        System.out.printf("请于5秒钟内输入tomcat监听端口号, 推荐  %d %n", defaultTomcatPort, defaultTomcatPort);
        strPort = scanner.nextLine();
        tomcatPort = Convert.toInt(strPort);
        scanner.close();

        new SpringApplicationBuilder(MoocClassWebApplication.class)
                .properties("spring.dubbo.protocol.port=" + dubboPort)
                .properties("server.port=" + tomcatPort)
                .run(args);


//        SpringApplication.run(MoocClassWebApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("All MoocClass: {}");

    }
}
