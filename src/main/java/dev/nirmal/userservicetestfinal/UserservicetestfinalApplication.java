package dev.nirmal.userservicetestfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserservicetestfinalApplication {
  public static void main(String[] args) {
    SpringApplication.run(UserservicetestfinalApplication.class, args);
  }
}
