package com.push.alarm.repository.inf;

import com.push.alarm.data.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlarmJPARepository extends JpaRepository<Alarm,Long> {

    @Query("SELECT a FROM Alarm a WHERE f.alarmId = :alarmId")
    <T> T findById(Long alarmId, Class<T> tClass);
}
