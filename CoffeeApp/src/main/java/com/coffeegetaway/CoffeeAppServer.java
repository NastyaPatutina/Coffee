package com.coffeegetaway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy  // act as zuul proxy.
@SpringBootApplication
public class CoffeeAppServer {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeAppServer.class, args);
    }
}
