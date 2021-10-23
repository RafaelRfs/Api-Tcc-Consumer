package br.com.pucminas.apiconsumer.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource getDataSource(@Value("${postgresql.datasource.url}") String url) throws URISyntaxException, SQLException {
        URI dbUri = new URI(url);
        String user = dbUri.getUserInfo().split(":")[0];
        String pass = dbUri.getUserInfo().split(":").length > 1 ?
                dbUri.getUserInfo().split(":")[1] : "";
        String driver = "postgres".equalsIgnoreCase(dbUri.getScheme())
                ? "postgresql" : dbUri.getScheme().trim().toLowerCase();
        String options = "?useUnicode=true&characterEncoding=utf-8&useTimezone=true&serverTimezone=UTC";

        return DataSourceBuilder.create()
                .url(obterEndpoint(dbUri, driver, options))
                .username(user)
                .password(pass)
                .build();
    }

    private String obterEndpoint(URI dbUri, String driver, String options) {
        StringBuilder sbEndpoint = new StringBuilder();
        sbEndpoint.append("jdbc:");
        sbEndpoint.append(driver);
        sbEndpoint.append("://");
        sbEndpoint.append(dbUri.getHost());
        sbEndpoint.append(":");
        sbEndpoint.append(dbUri.getPort());
        sbEndpoint.append(dbUri.getPath());
        sbEndpoint.append(options);
        return sbEndpoint.toString();
    }

}