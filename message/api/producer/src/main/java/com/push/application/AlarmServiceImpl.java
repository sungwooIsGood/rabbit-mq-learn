package com.push.application;

import com.push.application.factory.AlarmFactory;
import com.push.application.inf.AlarmService;
import com.push.application.strategy.AlarmStrategy;
import com.push.alarm.data.dto.MessageItem;
import com.push.domain.dto.RequestPushAlarmItem;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmFactory alarmFactory;
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String rabbitMQKey;

    @Transactional
    public void makeAlarmMessageByContents(RequestPushAlarmItem requestPushAlarmItem) {

        AlarmStrategy alarmStrategy = alarmFactory.createAlarmStrategy(requestPushAlarmItem);
        MessageItem messageItem = alarmStrategy.makeMessage(requestPushAlarmItem.getAlarmContentsId());

        rabbitTemplate.convertAndSend(String.format("send.alarm.%s",messageItem.getAlarmType()),rabbitMQKey,messageItem);
    }
}
