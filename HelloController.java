package com.example.log4j2tomcattest.web;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {

    @RequestMapping("/")
    Map<String, String> printAllSystemProps(@RequestHeader Map<String, String> headers) {
        log.info("trustURLCodebase: " + System.getProperty("com.sun.jndi.ldap.object.trustURLCodebase"));

        headers.forEach((headerName, headerValue) -> {
            log.info("Header Name: " + headerName + ", Header Value: " + headerValue);
        });

        Map<String, String> systemMap = System.getProperties()
                .entrySet().stream()
                // .filter(s -> includes != null && includes.contains(s.getKey()))
                .sorted(Comparator.comparing(
                        Map.Entry::getKey, Comparator.comparing(e -> (String) e)))
                .collect(Collectors.toMap(
                        e -> (String) e.getKey(),
                        e -> (String) e.getValue(),
                        (e1, e2) -> e2,
                        LinkedHashMap::new));

        return systemMap;
    }
}
