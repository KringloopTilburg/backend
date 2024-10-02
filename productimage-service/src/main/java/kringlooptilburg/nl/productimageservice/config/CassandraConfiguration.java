package kringlooptilburg.nl.productimageservice.config;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    @Value("${spring.cassandra.contact-points}")
    private String contactPoints;
    @Value("${spring.cassandra.port}")
    private int port;
    @Value("${spring.cassandra.keyspace-name}")
    private String keySpace;

    @Override
    @NonNull
    public String getContactPoints() {
        return contactPoints;
    }

    @Override
    @NonNull
    protected String getKeyspaceName() {
        return keySpace;
    }

    @Override
    protected int getPort() {
        return port;
    }
}