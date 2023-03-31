package com.aihs.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
public class DruidFaqApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private FooService fooService;

    public static void main(String[] args) {
        SpringApplication.run(DruidFaqApplication.class,args);
    }


    @Override
    public void run(String... args) throws Exception {
        log.info(dataSource.toString());
        new Thread(() -> fooService.selectForUpdate()).start();
        new Thread(() -> fooService.selectForUpdate()).start();
    }
}