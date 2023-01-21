package com.momo2x.mbdn.contacts.mq.config;

import com.momo2x.mbdn.contacts.mq.QueueCreationException;
import jakarta.annotation.PostConstruct;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.InvalidDestinationException;
import jakarta.jms.JMSException;
import jakarta.jms.QueueSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class IbmMqConfig {

    private final ConnectionFactory connectionFactory;
    @Value("${mq.queue.contact.save}")
    private String queueName;
    @Value("${docker.container.mq}")
    private String containerName;

    @PostConstruct
    public void checkIbmQueue() throws JMSException, IOException {
        log.info("Queue health check for %s".formatted(queueName));

        try (final var connection = connectionFactory.createConnection()) {
            log.info("Queue health check: Connection established");

            try (final var session = connection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE)) {
                log.info("Queue health check: Session created");

                final var queue = session.createQueue(queueName);

                try {
                    session.createConsumer(queue);

                    log.info("Queue health check: %s queue found".formatted(queueName));

                } catch (final InvalidDestinationException e) {
                    log.warn("Queue health check: %s ".formatted(e.getMessage()));

                    final var cmd = "docker exec %s sh /temp/mq-setup.sh".formatted(containerName);

                    log.warn("Queue health check: Running command '%s'".formatted(cmd));

                    final var process = Runtime.getRuntime().exec(cmd);
                    final var pid = process.pid();

                    log.warn("Queue health check: Process %d for command '%s' started".formatted(pid, cmd));

                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                    String s;

                    while ((s = stdInput.readLine()) != null) {
                        log.warn("Queue health check: Process '%d' >> %s".formatted(pid, s));
                    }

                    while ((s = stdError.readLine()) != null) {
                        log.error("Queue health check: Process '%d' >> %s".formatted(pid, s));
                    }

                    final var msg = "Process %d for command '%s' finished with value %s".formatted(pid, cmd, process.exitValue());

                    if (process.exitValue() != 0) {
                        throw new QueueCreationException(msg, e);
                    }

                    log.warn(msg);
                }
            }
        }
    }

}
