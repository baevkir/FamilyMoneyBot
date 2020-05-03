package com.familymoney.telegrambot;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.BaseStream;

@Slf4j
@SpringBootApplication
public class TelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }

    @Bean
    public ApplicationRunner seeder(DatabaseClient client) {
        return args -> getSchema()
                .flatMap(sql -> executeSql(client, sql))
                .subscribe(count -> log.info("Schema created"));
    }

    private Mono<String> getSchema() {
        return Mono.fromSupplier(() -> {
            try (InputStream schemaStream = getClass().getResourceAsStream("/schema.sql")) {
                return schemaStream.readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException("Cannot read schema", e);
            }
        }).map(String::new);
    }

    private Mono<Integer> executeSql(DatabaseClient client, String sql) {
        return client.execute(sql).fetch().rowsUpdated();
    }
}
