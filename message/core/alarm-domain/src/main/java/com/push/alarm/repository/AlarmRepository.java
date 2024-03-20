package com.push.alarm.repository;

import com.push.alarm.data.dto.ReceiveAlarmItem;
import com.push.alarm.data.entity.Alarm;
import com.push.alarm.data.entity.AlarmMessageLog;
import com.push.alarm.data.entity.AlarmReceiveUser;
import com.push.alarm.data.enums.AlarmType;
import com.push.alarm.repository.inf.AlarmJPARepository;
import com.push.alarm.repository.inf.AlarmMessageLogJPARepository;
import com.push.alarm.repository.inf.AlarmReceiveUserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlarmRepository {

    private final AlarmJPARepository alarmJPARepository;
    private final AlarmQRepository alarmQRepository;
    private final AlarmMessageLogJPARepository alarmMessageLogJPARepository;
    private final AlarmReceiveUserJPARepository alarmReceiveUserJPARepository;

    public List<ReceiveAlarmItem> findByAlarmType(AlarmType alarmType, int limitSize, Long seqId) {
        return alarmQRepository.findByAlarmType(alarmType,limitSize,seqId);
    }


    public <T> T findById(Long alarmId, Class<T> alarmClass) {
        return alarmJPARepository.findById(alarmId,alarmClass);
    }

    public void saveAlarmLogAndReceiveUser(AlarmMessageLog alarmMessageLog, List<ReceiveAlarmItem> receiveAlarmItems) {
        Long alarmMessageLogId = alarmMessageLogJPARepository.save(alarmMessageLog).getAlarmMessageLogId();

        List<AlarmReceiveUser> alarmReceiveUserList = new ArrayList<>();
        receiveAlarmItems.forEach(receiveAlarmItem ->
                alarmReceiveUserList.add(AlarmReceiveUser.createAlarmReceiveUserList(alarmMessageLogId,receiveAlarmItem.getUserId()))
        );

        alarmReceiveUserJPARepository.saveAll(alarmReceiveUserList);
    }
}
