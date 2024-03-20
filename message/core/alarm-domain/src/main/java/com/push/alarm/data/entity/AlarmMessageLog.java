package com.push.alarm.data.entity;

import com.push.alarm.data.converter.AlarmTypeConverter;
import com.push.alarm.data.enums.AlarmType;
import com.push.globalDomain.entity.DateTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class AlarmMessageLog extends DateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(10) unsigned")
    private Long alarmMessageLogId;

    @Convert(converter = AlarmTypeConverter.class)
    private AlarmType alarmType;

    private String alarmTitle;


    private String alarmBody;

    private String alarmBodyImg;

    @Builder
    public AlarmMessageLog(Long alarmMessageLogId, AlarmType alarmType, String alarmTitle,
                           String alarmBody, String alarmBodyImg) {
        this.alarmMessageLogId = alarmMessageLogId;
        this.alarmType = alarmType;
        this.alarmTitle = alarmTitle;
        this.alarmBody = alarmBody;
        this.alarmBodyImg = alarmBodyImg;
    }

    public static AlarmMessageLog of(AlarmType alarmType, String alarmTitle,
                                     String alarmBody, String alarmBodyImg) {
        return AlarmMessageLog.builder()
                .alarmType(alarmType)
                .alarmTitle(alarmTitle)
                .alarmBody(alarmBody)
                .alarmBodyImg(alarmBodyImg)
                .build();
    }
}
