package com.push.alarm.repository;

import com.push.alarm.data.dto.ReceiveAlarmItem;
import com.push.alarm.data.enums.AlarmType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.push.alarm.data.entity.QAlarm.alarm;

@Repository
@RequiredArgsConstructor
public class AlarmQRepository {

    private final JPAQueryFactory query;

    public List<ReceiveAlarmItem> findByAlarmType(AlarmType alarmType, int limitSize, Long seqId) {

        return query.select(Projections.constructor(ReceiveAlarmItem.class, alarm))
                .from(alarm)
                .where(
                        alarm.userFcmCode.isNotNull()
                                .and(alarmTypeEq(alarmType))
                                .and(alarm.alarmId.gt(seqId))
                )
                .orderBy(alarm.alarmId.asc())
                .limit(limitSize)
                .fetch();
    }

    private BooleanExpression alarmTypeEq(AlarmType type) {

        if(AlarmType.NEWS.equals(type)){
            return alarm.newsAlarmSelect.eq(true);
        }

        return null;

    }
}
