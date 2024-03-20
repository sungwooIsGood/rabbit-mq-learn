package com.push.alarm.data.dto;

import com.google.firebase.messaging.*;
import com.push.alarm.data.enums.AlarmType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageItem {

    private String url;
    private String title;
    private String body;
    private String bodyImg;
    private AlarmType alarmType;

    @Builder
    public MessageItem(String title, String url,
                       String body, String bodyImg, AlarmType alarmType) {
        this.title = title;
        this.url = url;
        this.body = body;
        this.bodyImg = bodyImg;
        this.alarmType = alarmType;
    }

    public static MessageItem of(String url, String title, String body, String bodyImg, AlarmType alarmType){
        return MessageItem.builder()
                .url(url)
                .title(title)
                .body(body)
                .bodyImg(bodyImg)
                .alarmType(alarmType)
                .build();
    }

    public Message buildMessage(String fcmCode) {
        return Message.builder()
                .setToken(fcmCode)
                .putData("url", this.url)
                .setFcmOptions(FcmOptions.builder()
                        .build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000)
                        .setPriority(AndroidConfig.Priority.NORMAL)
                        .setRestrictedPackageName("com.push")
                        .setNotification(AndroidNotification.builder()
                                .setTitle(this.title)
                                .setBody(this.body)
                                .setImage(this.bodyImg)
                                .build())
                        .build())
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setMutableContent(true)
                                .putCustomData("url", this.url)
                                .setAlert(ApsAlert.builder()
                                        .setTitle(this.title)
                                        .setBody(this.body)
                                        .setLaunchImage(this.bodyImg)
                                        .build())
                                .build())
                        .setFcmOptions(ApnsFcmOptions
                                .builder()
                                .setImage(this.bodyImg)
                                .build())
                        .build())
                .build();
    }
}
