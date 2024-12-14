package config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
public class Config {
    @Profile("dev")
    @Bean(initMethod = "start") // uses the start() method of the bean to start the web server
    public Server h2WebServer() throws SQLException {
        return Server.createWebServer("-web", "-webPort", "8082");
    }
}
