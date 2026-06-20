package com.common.eventbus;

import com.common.eventbus.impl.KafkaEventBus;
import com.common.eventbus.impl.NoOpEventBus;
import com.common.eventbus.impl.RabbitEventBus;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Lớp kiểm thử tự động cho cấu hình điều kiện nạp Bean của Event Bus (Spring Boot ApplicationContextRunner).<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
class EventBusConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(MockDependenciesConfiguration.class, EventBusConfiguration.class);

    @Configuration
    static class MockDependenciesConfiguration {
        @Bean
        @SuppressWarnings("unchecked")
        public KafkaTemplate<String, Object> kafkaTemplate() {
            return mock(KafkaTemplate.class);
        }

        @Bean
        public AmqpTemplate amqpTemplate() {
            return mock(AmqpTemplate.class);
        }
    }

    @Test
    void testKafkaEventBusEnabled() {
        this.contextRunner
                .withPropertyValues("common.event-bus.type=kafka")
                .run(context -> {
                    assertThat(context).hasSingleBean(IEventBus.class);
                    assertThat(context.getBean(IEventBus.class)).isInstanceOf(KafkaEventBus.class);
                });
    }

    @Test
    void testRabbitEventBusEnabled() {
        this.contextRunner
                .withPropertyValues("common.event-bus.type=rabbitmq")
                .run(context -> {
                    assertThat(context).hasSingleBean(IEventBus.class);
                    assertThat(context.getBean(IEventBus.class)).isInstanceOf(RabbitEventBus.class);
                });
    }

    @Test
    void testNoOpEventBusEnabled_ByExplicitNone() {
        this.contextRunner
                .withPropertyValues("common.event-bus.type=none")
                .run(context -> {
                    assertThat(context).hasSingleBean(IEventBus.class);
                    assertThat(context.getBean(IEventBus.class)).isInstanceOf(NoOpEventBus.class);
                });
    }

    @Test
    void testNoOpEventBusEnabled_ByDefault() {
        this.contextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(IEventBus.class);
                    assertThat(context.getBean(IEventBus.class)).isInstanceOf(NoOpEventBus.class);
                });
    }
}
