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
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
@EnableDubboConfiguration
@EnableCaching
public class MoocClassServiceApplication implements ApplicationRunner {

    public static void main(String[] args) throws IOException {

        int port = 0;
        int defaultDubboPort = 20881;

        if(null!=args && 0!=args.length) {
            for (String arg : args) {
                if(arg.startsWith("port=")) {
                    String strPort= StrUtil.subAfter(arg, "port=", true);
                    if(NumberUtil.isNumber(strPort)) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }

        if(0==port) {
            Future<Integer> future = ThreadUtil.execAsync(() ->{
                int p = 0;
                System.out.printf("请于5秒钟内输入dubbo服务监听端口号, 推荐  %d ,超过5秒将默认使用 %d %n",defaultDubboPort,defaultDubboPort);
                Scanner scanner = new Scanner(System.in);
                while(true) {
                    String strPort = scanner.nextLine();
                    if(!NumberUtil.isInteger(strPort)) {
                        System.err.println("只能是数字");
                        continue;
                    }
                    else {
                        p = Convert.toInt(strPort);
                        scanner.close();
                        break;
                    }
                }
                return p;
            });
            try{
                port=future.get(5, TimeUnit.SECONDS);
            }
            catch (InterruptedException | ExecutionException | TimeoutException e){
                port = defaultDubboPort;
            }
        }
        new SpringApplicationBuilder(MoocClassServiceApplication.class).properties("spring.dubbo.protocol.port=" + port).run(args);


//        SpringApplication.run(MoocClassServiceApplication.class, args);

        args = new String[] { "spring" };
        com.alibaba.dubbo.container.Main.main(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("All MoocClass: {}");

    }
}
