package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        //지정안하면 제일 위줄 hello.core부터 그 하위 다 뒤져서 찾는다.
        //basePackages = "hello.core.member",
        //AppConfig클래스에 있는거 제외시켜주기 위해서(복습을 위해 남겨두기위해..)
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
