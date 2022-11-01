//package com.dev.segbaya.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import static springfox.documentation.builders.PathSelectors.regex;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket productApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .build();
////                .apis(RequestHandlerSelectors.basePackage("com.dev.segbaya"))
////                .paths(regex("/api.*"))
////                .apiInfo(metaInfo());
//    }
//
////    private ApiInfo metaInfo() {
////
////        ApiInfo apiInfo = new ApiInfo(
////                "Segbaya Swagger API",
////                "Segbaya Swagger API Documentation",
////                "1.0",
////                "Terms of Service",
////                new Contact("TechPrimers", "https://www.youtube.com/TechPrimers",
////                        "techprimerschannel@gmail.com"),
////                "Apache License Version 2.0",
////                "https://www.apache.org/licesen.html"
////        );
////
////        return apiInfo;
////    }
//
//}
