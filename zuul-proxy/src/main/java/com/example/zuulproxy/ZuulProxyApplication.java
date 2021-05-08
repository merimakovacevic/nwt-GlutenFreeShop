package com.example.zuulproxy;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulProxyApplication.class, args);
    }




    @Component
    static class TestCommandLineRunner implements CommandLineRunner {

        @Autowired
        private EurekaClient eurekaClient;

        @Override
        public void run(String... args) throws Exception {
            EurekaClientConfig eurekaClientConfig = eurekaClient.getEurekaClientConfig();
            Applications applications = eurekaClient.getApplications();
            List<InstanceInfo> instances = eurekaClient.getInstancesById("order-microservice");
            System.out.println("test");
        }
    }
}
