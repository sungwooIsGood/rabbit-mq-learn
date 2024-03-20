package com.push.consumer.application;

import com.push.alarm.data.dto.MessageItem;
import com.push.alarm.data.dto.ReceiveAlarmItem;
import com.push.alarm.data.entity.Alarm;
import com.push.alarm.data.entity.AlarmMessageLog;
import com.push.alarm.data.enums.DeviceType;
import com.push.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.firebase.messaging.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmConsumerService {

    private final AlarmRepository alarmRepository;

    private final int SEND_LIMIT_SIZE = 1000;

    @Transactional
    public void sendMessage(MessageItem messageItem) throws FirebaseMessagingException {

        Long seqId = 0L;

        while (true) {
            List<ReceiveAlarmItem> receiveAlarmItems = alarmRepository.findByAlarmType(messageItem.getAlarmType(), SEND_LIMIT_SIZE, seqId);

            if (receiveAlarmItems.isEmpty()) {
                break;
            }

            List<Message> messages = receiveAlarmItems.stream()
                    .map(receiveFcmAlarmItem -> messageItem.buildMessage(receiveFcmAlarmItem.getFcmCode()))
                    .collect(Collectors.toList());

            BatchResponse batchResponse = FirebaseMessaging.getInstance().sendAll(messages); // 메세지 전송

            sendAfterErrorHandler(receiveAlarmItems, batchResponse); // 예외 후처리
            saveAlarmLogAndReceiveUser(messageItem,receiveAlarmItems); // 알람 로그 저장 및 receive user 저장

            seqId = receiveAlarmItems.get(receiveAlarmItems.size() - 1).getAlarmId();
            messages.clear();
        }
    }

    private void sendAfterErrorHandler(List<ReceiveAlarmItem> receiveAlarmItems, BatchResponse batchResponse) {

        receiveAlarmItems.forEach(receiveFcmAlarmItem -> {

            SendResponse sendResponse = batchResponse.getResponses().get(receiveAlarmItems.indexOf(receiveFcmAlarmItem));
            DeviceType deviceType = receiveFcmAlarmItem.getDeviceType();
            String userFcmCode = receiveFcmAlarmItem.getFcmCode();

            if(Objects.nonNull(sendResponse)){
                FirebaseMessagingException exception = sendResponse.getException();

                if (Objects.nonNull(exception) && MessagingErrorCode.UNREGISTERED.equals(exception.getMessagingErrorCode())) { // 404 - Requested entity was not found.

                    log.info("Requested entity was not found. Code를 지웁니다. userId: {}", receiveFcmAlarmItem.getAlarmId());

                    Optional.ofNullable(alarmRepository.findById(receiveFcmAlarmItem.getAlarmId(), Alarm.class))
                            .ifPresent(Alarm::updateFcmCodeForErrorHandler);
                }

                log.info("[{}] fcm-code: {}, {}",
                        deviceType, userFcmCode == null ? "null" : userFcmCode, exception == null ? "fcm-code 정상" : exception.getMessage());
            }
        });
    }

    private void saveAlarmLogAndReceiveUser(MessageItem messageItem, List<ReceiveAlarmItem> receiveAlarmItems) {
        AlarmMessageLog alarmMessageLog = AlarmMessageLog.of(messageItem.getAlarmType(), messageItem.getTitle(), messageItem.getBody(), messageItem.getBodyImg());
        alarmRepository.saveAlarmLogAndReceiveUser(alarmMessageLog,receiveAlarmItems);
    }
}
