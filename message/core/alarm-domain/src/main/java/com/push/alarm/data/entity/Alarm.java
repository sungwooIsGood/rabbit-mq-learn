package com.push.alarm.data.entity;

import com.push.alarm.data.converter.DeviceTypeConverter;
import com.push.alarm.data.enums.DeviceType;
import com.push.globalDomain.entity.DateTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Alarm extends DateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(10) unsigned")
    private Long alarmId;

    private Long userId;

    private String userFcmCode;

    @Convert(converter = DeviceTypeConverter.class)
    private DeviceType deviceType;

    private boolean bookmarkAlarmSelect;

    private boolean editorAlarmSelect;

    private boolean newsAlarmSelect;

    public void updateFcmCodeForErrorHandler() {
        userFcmCode = null;
    }
}
