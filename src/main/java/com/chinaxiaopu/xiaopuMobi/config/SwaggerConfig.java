package com.chinaxiaopu.xiaopuMobi.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.*;
import static springfox.documentation.builders.PathSelectors.*;


/**
 * Created by ellien
 * date: 16/9/13
 */
@Configuration
@EnableSwagger2
@ComponentScan("com.chinaxiaopu.xiaopuMobi.controller")
public class SwaggerConfig {

    @Bean
    public Docket helloApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("full-petstore-api")
                .apiInfo(apiInfo())
                .select()
                .paths(paths())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("校普API接口")
                .description("校普api接口调用。")
                .version("1.0")
                .build();
    }

    private Predicate<String> paths() {
        return or(
                regex("/user/.*"),
                regex("/school/.*"),
                regex("/event/.*"),
                regex("/group/.*"),
                regex("/app/.*"),
                regex("/appVersion/.*"),
                regex("/partner/.*"),
                regex("/channel/.*"),
                regex("/comment/.*"),
                regex("/prize/.*"),
                regex("/ranking/.*"),
                regex("/topic/.*"),
                regex("/topicPk/.*"),
                regex("/vote/.*"),
                regex("/voteResult/.*"),
                regex("/topicGroup/.*"),
                regex("/vrActivity/.*"),
                regex("/file/.*"),
                regex("/ticket/.*"),
                regex("/tipoff/.*"),
                regex("/userFan/.*"),
                regex("/audio/.*"),
                regex("/eventLottery/.*")

        );
    }
}
