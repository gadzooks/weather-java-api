package com.github.gadzooks.weather.configuration;

import java.io.IOException;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoTestConfig {

    @Bean
    public MongodExecutable mongodExecutable() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodExecutable mongodExecutable = starter.prepare(MongodConfig.builder()
            .version(Version.Main.PRODUCTION)
            .build());
        mongodExecutable.start();
        return mongodExecutable;
    }
}
