package com.push.alarm.data.converter;

import com.push.alarm.data.enums.DeviceType;
import jakarta.persistence.AttributeConverter;

public class DeviceTypeConverter implements AttributeConverter<DeviceType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DeviceType attribute) {

        if(attribute.equals(DeviceType.NON)){
            return 0;
        }

        if(attribute.equals(DeviceType.AOS)){
            return 1;
        }

        if(attribute.equals(DeviceType.IOS)){
            return 2;
        }

        return null;
    }

    @Override
    public DeviceType convertToEntityAttribute(Integer dbData) {

        if(dbData == 0){
            return DeviceType.NON;
        }

        if(dbData == 1){
            return DeviceType.AOS;
        }

        if(dbData == 2){
            return DeviceType.IOS;
        }

        return null;
    }
}
