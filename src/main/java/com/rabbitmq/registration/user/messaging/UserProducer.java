package com.rabbitmq.registration.user.messaging;

import com.rabbitmq.registration.user.config.RabbitMQConfig;
import com.rabbitmq.registration.user.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    private static final Logger log = LoggerFactory.getLogger(UserProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

        // Configura callback de confirmação
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("✅ Mensagem confirmada pelo broker. CorrelationId={}",
                        correlationData != null ? correlationData.getId() : "N/A");
            } else {
                log.error("❌ Falha no envio da mensagem. Causa={}", cause);
            }
        });
    }

    public void sendUser(UserDTO userDTO) {
        log.info("📤 Enviando usuário: id={}, name={}, email={}",
                userDTO.getId(), userDTO.getName(), userDTO.getEmail());

        // Usa CorrelationData para rastrear a mensagem
        CorrelationData correlationData = new CorrelationData(
                userDTO.getId() != null ? userDTO.getId().toString() : null
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                userDTO,
                correlationData
        );
    }
}
