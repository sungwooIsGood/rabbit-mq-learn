package com.push.producer.presentation;

import com.push.application.inf.AlarmService;
import com.push.domain.dto.RequestPushAlarmItem;
import com.push.globalDomain.dto.BasicResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class ProducerAlarmController {

    private final AlarmService alarmService;

    public ProducerAlarmController(@Qualifier("alarmServiceImpl") AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @PostMapping("/api/alarm")
    public BasicResponse makeAlarmMessageByContents(@RequestBody RequestPushAlarmItem requestPushAlarmItem){
        alarmService.makeAlarmMessageByContents(requestPushAlarmItem);
        return BasicResponse.builder()
                .data(Collections.EMPTY_LIST)
                .build();
    }
}
