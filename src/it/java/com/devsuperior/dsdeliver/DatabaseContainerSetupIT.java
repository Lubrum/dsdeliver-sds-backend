package com.devsuperior.dsdeliver;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = DatabaseContainerSetupIT.Initializer.class)
//@PropertySource("classpath:application-test.properties")
//@ActiveProfiles("test")
public class DatabaseContainerSetupIT
{

    private static final int DB_PORT = 5432;

    private static final String DB_NAME = "com/devsuperior/dsdeliver";

    private static final String DB_USERNAME = "root";

    private static final String DB_PASSWORD = "";

    private static final String DB_IMAGE_VERSION_15_1 = "postgres:15.1";

    private static final PostgreSQLContainer<?> DB_CONTAINER;

    static {
        DB_CONTAINER = new PostgreSQLContainer<>(DB_IMAGE_VERSION_15_1);
        DB_CONTAINER.withExposedPorts(DB_PORT);
        DB_CONTAINER.setWaitStrategy(Wait.forListeningPort());
        DB_CONTAINER.withUsername(DB_USERNAME);
        DB_CONTAINER.withPassword(DB_PASSWORD);
        DB_CONTAINER.withDatabaseName(DB_NAME);
        DB_CONTAINER.withEnv("POSTGRES_HOST_AUTH_METHOD", "trust");
        DB_CONTAINER.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
    {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            final var host = DB_CONTAINER.getHost();
            final var port = DB_CONTAINER.getMappedPort(DB_PORT);
            System.setProperty("system.containerUrl", host + ":" + port);
        }
    }
}
