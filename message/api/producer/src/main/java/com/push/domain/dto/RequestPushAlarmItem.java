package com.push.domain.dto;

import com.push.alarm.data.enums.AlarmType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RequestPushAlarmItem {

    private Long alarmContentsId; // 각 컨텐츠들의 저장된 고유 id 값 - 저장된 후 alarm을 보냈다고 가정, events로 트랜잭션 분리 했다고 가정.
    private AlarmType alarmType;
}
