package com.push.application.inf;

import com.push.domain.dto.RequestPushAlarmItem;

public interface AlarmService {
    void makeAlarmMessageByContents(RequestPushAlarmItem requestPushAlarmItem);
}
