package controller.order;

import com.example.plugin.controller.order.OrderController;
import com.example.plugin.persistence.order.OrderRepositoryBridge;
import order.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@Import(TestConfig.class)
@ContextConfiguration(classes = {OrderController.class, TestConfig.class, com.example.plugin.PluginApplication.class})

class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepositoryBridge orderRepositoryBridge;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private OrderDto orderDto;

    @BeforeEach
    void setUp() {
        orderDto = new OrderDto();
        orderDto.setPhoneId("P123");
        orderDto.setPhoneName("Galaxy S21");
        orderDto.setBranchId("B001");
        orderDto.setBranchName("Berlin");
        orderDto.setQuantity(2);
    }

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {
        doNothing().when(orderRepositoryBridge).addOrder(Mockito.any());

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer test-token")
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Bestellung erfolgreich erstellt"));
    }

    @Test
    void shouldReturnBadRequestWhenAuthHeaderMissing() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Security Error"));
    }


    @TestConfiguration
    static class MockConfig {
        @Bean
        public OrderRepositoryBridge orderRepositoryBridge() {
            return Mockito.mock(OrderRepositoryBridge.class);
        }
    }
}