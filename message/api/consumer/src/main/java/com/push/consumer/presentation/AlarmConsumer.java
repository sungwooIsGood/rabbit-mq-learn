package com.push.consumer.presentation;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.push.alarm.data.dto.MessageItem;
import com.push.consumer.application.AlarmConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmConsumer {

    private final AlarmConsumerService alarmConsumerService;

    @RabbitListener(queues = "send.alarm")
    public void consume(MessageItem messageItem) throws FirebaseMessagingException {
        log.info("전달 된 메시지: {}", messageItem);
        alarmConsumerService.sendMessage(messageItem);
    }
}
