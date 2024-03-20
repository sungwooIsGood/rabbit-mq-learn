
## 모듈 별 역할

### producer 모듈

- 각 프로젝트에서 컨텐츠들이 저장된 후 **알람을 보내기 위해 message를 만드는 모듈**

```java
public class RequestPushAlarmItem {

    private Long alarmContentsId; // 각 컨텐츠들의 저장된 고유 id 값 - 저장된 후 alarm을 보냈다고 가정, events로 트랜잭션 분리 했다고 가정.
    private AlarmType alarmType;
}
```

- 팩토리 패턴과 전략 패턴을 통해 message 유연성을 확보하려고 노력
    - `NewsAlarmStrategy` 는 `AlarmStrategy` 인터페이스를 상속 받는다. 각 하위 구현체들은 메세지를 만드는 알고리즘, 역할이 다르다고 생각하여 전략 패턴으로 풀어보고자 함.

```java
@Component
@RequiredArgsConstructor
public class AlarmFactory {

    private final NewsAlarmStrategy newsAlarmStrategy;

    public AlarmStrategy createAlarmStrategy(RequestPushAlarmItem requestPushAlarmItem) {

        if(AlarmType.NEWS.equals(requestPushAlarmItem.getAlarmType())){
            return newsAlarmStrategy;
        }

        if(AlarmType.BOOKMARK.equals(requestPushAlarmItem.getAlarmType())){
            throw new RuntimeException("아직 구현하지 않았습니다.");
        }

        return null;
    }
}

@Component
@RequiredArgsConstructor
public class NewsAlarmStrategy implements AlarmStrategy{

    private final NewsService newsService;

    @Override
    public MessageItem makeMessage(Long contentsId) {
        NewsDto newsDto = newsService.findById(contentsId);

        String url = String.format("https://testUrl/news?newsId=%s&newsTitle=%s",newsDto.getNewsId(),newsDto.getNewsTitle());
        String title = String.format("[핫이슈] %s!", newsDto.getNewsTitle());
        String body = String.format("%s 포착 후, 사람들이 어떤 움직임을 가질지 지켜보려면 눌러주세요.",title);

        return MessageItem.of(url,newsDto.getNewsTitle(), body, newsDto.getNewsTitleImg(), AlarmType.NEWS);
    }
}
```

---

### Rabbit MQ

- 메세지 브로커 역할
- producer 모듈과 consumer 모듈 분리 목적
    - 메세지를 만드는 기능과 메세지를 보내는 기능을 분리하고 싶었음.

---

### consumer 모듈

- producer에서 만든 메세지를 실제 사용자들에게 메세지를 보내는 역할

```java
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
}
```

- `SEND_LIMIT_SIZE` 를 통해 필터에 맞는 데이터들을 한번에 메모리에 올라가는 것을 막기위해 페이징 역할을 하는 변수
- 알람 발송 후 처리 로직 - 예외 케이스 핸들링 & 알람 발송 후 데이터 저장

```java
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

private void saveAlarmLog(AlarmType alarmType, List<ReceiveAlarmItem> receiveAlarmItems) {
    AlarmMessageLog alarmMessageLog = AlarmMessageLog.of(messageItem.getAlarmType(), messageItem.getTitle(), messageItem.getBody(), messageItem.getBodyImg());
    alarmRepository.saveAlarmLogAndReceiveUser(alarmMessageLog,receiveAlarmItems);
}
```

---

### 각각의 domain 모듈

- core 디렉토리 하위에 domain 모듈들을 구현
    - 각 domain 모듈은 다른 domain 모듈을 참조하고 있지 않고 모듈만 따로 분리되어 동작할 수 있도록 하는 것이 목표(캡슐화)

---

### RabbitMQ consumer 옵션

```java
@Bean
public SimpleMessageListenerContainer messageListenerContainer() {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory());
    container.setQueueNames(queueName);
    container.setConcurrentConsumers(5); // 병렬 처리 수 설정 - default는 1개
    container.setMaxConcurrentConsumers(10); // 최대 병렬 처리 수 설정
    container.setReceiveTimeout(10000L); // 최대 시간
    return container;
}
```

- `SimpleMessageListener` 설정을 통해 Consumer 쓰레드 관리
    - Consumer 쓰레드는 기본 1개로, 동시에 처리할 메세지가 많아지면 Queue에서 대기중인 메세지들이 늘어나게 되는 것을 방지.

---
 
### 시퀀스 다이어그램

![Untitled (2)](https://github.com/sungwooIsGood/Today-I-Learn/assets/98163632/142a1682-8190-4bd3-bbbd-3d0ccf9d1a7d)
