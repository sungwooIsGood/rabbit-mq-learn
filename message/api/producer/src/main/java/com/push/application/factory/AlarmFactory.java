package com.push.application.factory;

import com.push.alarm.data.enums.AlarmType;
import com.push.application.strategy.AlarmStrategy;
import com.push.application.strategy.BookmarkAlarmStrategy;
import com.push.application.strategy.NewsAlarmStrategy;
import com.push.domain.dto.RequestPushAlarmItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmFactory {

    private final NewsAlarmStrategy newsAlarmStrategy;
    private final BookmarkAlarmStrategy bookmarkAlarmStrategy;

    public AlarmStrategy createAlarmStrategy(RequestPushAlarmItem requestPushAlarmItem) {

        if(AlarmType.NEWS.equals(requestPushAlarmItem.getAlarmType())){
            return newsAlarmStrategy;
        }

        if(AlarmType.BOOKMARK.equals(requestPushAlarmItem.getAlarmType())){
            return bookmarkAlarmStrategy;
        }

        return null;
    }
}
