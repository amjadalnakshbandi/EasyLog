package controller.order;

import com.example.plugin.persistence.order.OrderRepositoryBridge;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    public OrderRepositoryBridge orderRepositoryBridge() {
        return Mockito.mock(OrderRepositoryBridge.class);
    }
}
