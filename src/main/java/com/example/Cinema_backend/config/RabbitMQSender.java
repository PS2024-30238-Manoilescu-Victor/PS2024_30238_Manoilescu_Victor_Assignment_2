package com.example.Cinema_backend.config;

import com.example.Cinema_backend.constants.ConstantsEmailSender;
import com.example.Cinema_backend.dto.AccountCreationDTO;
import com.example.Cinema_backend.dto.AccountDeletionDTO;
import com.example.Cinema_backend.dto.OrderFinalisationDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAccountCreation(AccountCreationDTO accountCreationDTO){
        rabbitTemplate.convertAndSend(ConstantsEmailSender.AccountConfirmQueue,accountCreationDTO);
    }

    public void sendAccountDeletion(AccountDeletionDTO accountDeletionDTO){
        rabbitTemplate.convertAndSend(ConstantsEmailSender.DeleteAccountConfirmQueue,accountDeletionDTO);
    }

    public void sendOrderFinalisation(OrderFinalisationDTO orderFinalisationDTO){
        rabbitTemplate.convertAndSend(ConstantsEmailSender.FinaliseOrderConfirmQueue,orderFinalisationDTO);
    }

}
