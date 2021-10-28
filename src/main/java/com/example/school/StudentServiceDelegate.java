package com.example.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;
import java.util.Date;

@Service
public class StudentServiceDelegate
{
    @Autowired
    RestTemplate restTemplate;

    @Value("${target.service.name}")
    private String targetServiceName;

    public String callStudentServiceAndGetData(String schoolname)
    {
        System.out.println("Target Service name: "+targetServiceName);
        System.out.println("Consul Demo - Getting School details for " + schoolname);
        //String serverUrl = "http://{targetServiceName}/getStudentDetailsForSchool/{schoolname}";
        String response = restTemplate.exchange("http://"+targetServiceName+"/getStudentDetailsForSchool/{schoolname}",HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, schoolname).getBody();
        System.out.println("Response Received as " + response + " -  " + new Date());
        return "School Name -  " + schoolname + " :::  Student Details " + response + " -  " + new Date();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}