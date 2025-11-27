package com.rabbitmq.registration.user.messaging;

import com.rabbitmq.registration.user.dto.UserDTO;
import com.rabbitmq.registration.user.entity.User;
import com.rabbitmq.registration.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserConsumer.class);
    private final UserRepository userRepository;

    public UserConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveUser(UserDTO userDTO) {
        try {
            User entity = userDTO.toEntity();

            // Evita duplicidade pelo email
            if (userRepository.findByEmail(entity.getEmail()).isEmpty()) {
                userRepository.save(entity);
                log.info("✅ Usuário salvo com sucesso: id={}, name={}, email={}",
                        entity.getId(), entity.getName(), entity.getEmail());
            } else {
                log.warn("⚠️ Usuário já existe com email: {}", entity.getEmail());
            }

        } catch (Exception e) {
            log.error("❌ Erro ao processar mensagem para usuário: {}", userDTO, e);
            throw e; // mensagem vai para a DLQ
        }
    }
}
