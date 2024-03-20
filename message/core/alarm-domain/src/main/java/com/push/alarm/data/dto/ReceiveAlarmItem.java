package com.push.alarm.data.dto;

import com.push.alarm.data.entity.Alarm;
import com.push.alarm.data.enums.DeviceType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReceiveAlarmItem {

    private Long alarmId;
    private Long userId;
    private DeviceType deviceType;
    private String fcmCode;

    @Builder
    public ReceiveAlarmItem(Long alarmId, Long userId, DeviceType deviceType, String fcmCode) {
        this.alarmId = alarmId;
        this.userId = userId;
        this.deviceType = deviceType;
        this.fcmCode = fcmCode;
    }

    public ReceiveAlarmItem(Alarm alarm) {
        this.alarmId = alarm.getAlarmId();
        this.userId = alarm.getUserId();
        this.deviceType = alarm.getDeviceType();
        this.fcmCode = alarm.getUserFcmCode();
    }
}
