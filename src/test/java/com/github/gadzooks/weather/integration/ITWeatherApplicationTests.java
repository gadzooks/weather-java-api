package com.github.gadzooks.weather.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//By default, the Failsafe Plugin will automatically include all test classes with the following wildcard patterns
//"**/IT*.java" - includes all of its subdirectories and all Java filenames that start with "IT".
//"**IT.java" - includes all of its subdirectories and all Java filenames that end with "IT".
//"**/*ITCase.java" - includes all of its subdirectories and all Java filenames that end with "ITCase".
class ITWeatherApplicationTests {

    @Test
    void contextLoads() {
    }

    //FIXME : add true integration tests, which test the http layer, service layer and db layers
}
