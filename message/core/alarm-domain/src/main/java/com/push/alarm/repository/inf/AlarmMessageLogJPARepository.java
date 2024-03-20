package com.push.alarm.repository.inf;

import com.push.alarm.data.entity.AlarmMessageLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmMessageLogJPARepository extends JpaRepository<AlarmMessageLog,Long> {
}
