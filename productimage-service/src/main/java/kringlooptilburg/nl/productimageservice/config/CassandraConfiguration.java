package kringlooptilburg.nl.productimageservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    @Override
    public String getContactPoints() {
        return "127.0.0.1";
    }

    @Override
    protected String getKeyspaceName() {
        return "productimages";
    }

    @Override
    protected int getPort() {
        return 9042;
    }
}