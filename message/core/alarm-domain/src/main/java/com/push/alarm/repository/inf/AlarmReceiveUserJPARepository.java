package com.push.alarm.repository.inf;

import com.push.alarm.data.entity.AlarmReceiveUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmReceiveUserJPARepository extends JpaRepository<AlarmReceiveUser,Long> {
}
