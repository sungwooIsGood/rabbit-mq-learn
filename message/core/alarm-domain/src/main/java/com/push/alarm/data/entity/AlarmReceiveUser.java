package com.push.alarm.data.entity;

import com.push.globalDomain.entity.DateTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class AlarmReceiveUser extends DateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID alarmReceiveUserId; // Long Key 값은 saveAll 전략이 되지 않음.
    private Long alarmMessageLogId;
    private Long userId;

    @Builder
    public AlarmReceiveUser(UUID alarmReceiveUserId, Long userId, Long alarmMessageLogId) {
        this.alarmReceiveUserId = alarmReceiveUserId;
        this.alarmMessageLogId = alarmMessageLogId;
        this.userId = userId;
    }

    public static AlarmReceiveUser createAlarmReceiveUserList(Long alarmMessageLogId,Long userId) {
        return AlarmReceiveUser.builder()
                .userId(userId)
                .alarmMessageLogId(alarmMessageLogId)
                .build();
    }
}
