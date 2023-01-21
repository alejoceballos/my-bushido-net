package com.momo2x.mbdn.contacts.mq.config;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.util.ErrorHandler;

@Slf4j
@Configuration
public class JmsConfig {
    @Value("${mq.host}")
    private String host;

    @Value("${mq.port}")
    private Integer port;

    @Value("${mq.queueManager}")
    private String queueManager;

    @Value("${mq.channel}")
    private String channel;

    @Value("${mq.transportType}")
    private Integer transportType;

    @Value("${mq.user}")
    private String user;

    @Value("${mq.password}")
    private String password;

    @Bean(name = "jmsConnectionFactory")
    public ConnectionFactory jmsConnectionFactory() throws JMSException {
        final var factory = new MQQueueConnectionFactory();

        factory.setHostName(host);
        factory.setPort(port);
        factory.setChannel(channel);
        factory.setQueueManager(queueManager);
        factory.setTransportType(transportType);
        factory.setStringProperty(WMQConstants.USERID, user);
        factory.setStringProperty(WMQConstants.PASSWORD, password);

        return factory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(
            ErrorHandler errorHandler,
            @Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory) {
        final var factory = new DefaultJmsListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(true);
        factory.setErrorHandler(errorHandler);
        factory.setTaskExecutor(new SimpleAsyncTaskExecutor("mbdn-contacts-jms-"));

        return factory;
    }

}
