package dobby.upscale.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        //SpringApplication.run(DemoApplication.class, args);
        ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
        // ...determine it's time to shut down...
        ctx.close();

    }

}
