package com.push.alarm.data.converter;

import com.push.alarm.data.enums.AlarmType;
import jakarta.persistence.AttributeConverter;

public class AlarmTypeConverter implements AttributeConverter<AlarmType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AlarmType attribute) {


        if(attribute.equals(AlarmType.EDITOR)){
            return 0;
        }

        if(attribute.equals(AlarmType.BOOKMARK)){
            return 1;
        }

        if(attribute.equals(AlarmType.NEWS)){
            return 2;
        }

        return null;
    }

    @Override
    public AlarmType convertToEntityAttribute(Integer dbData) {

        if(dbData == 0){
            return AlarmType.EDITOR;
        }

        if(dbData == 1){
            return AlarmType.BOOKMARK;
        }

        if(dbData == 2){
            return AlarmType.NEWS;
        }

        return null;
    }
}
