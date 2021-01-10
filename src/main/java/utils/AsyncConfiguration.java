package utils;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAsync
public class AsyncConfiguration {
    @Bean (name = "taskExecutor")
    public Executor taskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setThreadNamePrefix("ApiGeoMeliThread-");
        executor.initialize();
        return executor;
    }
}
