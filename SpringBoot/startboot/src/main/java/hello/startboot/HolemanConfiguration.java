package hello.startboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HolemanConfiguration {

    @Bean
    public Holeman holeman() {
        Holeman holeman = new Holeman();
        holeman.setHowLong(5);
        holeman.setName("Seongjae");
        return holeman;
    }
}
