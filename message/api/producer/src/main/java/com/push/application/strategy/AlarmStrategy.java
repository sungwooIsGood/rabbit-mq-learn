package com.push.application.strategy;

import com.push.alarm.data.dto.MessageItem;

public interface AlarmStrategy {

    MessageItem makeMessage(Long contentsId);
}
