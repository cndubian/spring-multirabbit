package com.mytaxi.spring.multirabbit.example;

import org.springframework.amqp.rabbit.config.AbstractRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.autoconfigure.amqp.MultiRabbitConnectionFactoryWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtendedConfiguration
{

    static final String EXTENDED_CONNECTION_A = "extendedConnectionNameA";
    static final String EXTENDED_CONNECTION_B = "extendedConnectionNameB";


    @Bean
    public MultiRabbitConnectionFactoryWrapper externalWrapper()
    {
        ConnectionFactory connA = newConnectionFactory();
        ConnectionFactory connB = newConnectionFactory();
        MultiRabbitConnectionFactoryWrapper wrapper = new MultiRabbitConnectionFactoryWrapper();
        wrapper.addConnectionFactory(EXTENDED_CONNECTION_A, connA, newContainerFactory(connA), newRabbitAdmin(connA));
        wrapper.addConnectionFactory(EXTENDED_CONNECTION_B, connB, newContainerFactory(connB), newRabbitAdmin(connB));
        wrapper.setDefaultConnectionFactory(connA);
        return wrapper;
    }


    private ConnectionFactory newConnectionFactory()
    {
        return new CachingConnectionFactory();
    }


    private AbstractRabbitListenerContainerFactory newContainerFactory(ConnectionFactory connectionFactory)
    {
        SimpleRabbitListenerContainerFactory container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        return container;
    }


    private RabbitAdmin newRabbitAdmin(ConnectionFactory connectionFactory)
    {
        return new RabbitAdmin(connectionFactory);
    }

}
