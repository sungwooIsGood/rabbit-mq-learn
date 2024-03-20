package com.push.application.strategy;

import com.push.alarm.data.enums.AlarmType;
import com.push.alarm.data.dto.MessageItem;
import com.push.news.data.dto.NewsDto;
import com.push.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsAlarmStrategy implements AlarmStrategy{

    private final NewsService newsService;

    @Override
    public MessageItem makeMessage(Long contentsId) {
        NewsDto newsDto = newsService.findById(contentsId);

        String url = String.format("https://testUrl/news?newsId=%s&newsTitle=%s",newsDto.getNewsId(),newsDto.getNewsTitle());
        String title = String.format("[핫이슈] %s!", newsDto.getNewsTitle());
        String body = String.format("%s 포착 후, 사람들이 어떤 움직임을 가질지 지켜보려면 눌러주세요.",newsDto.getNewsTitle());

        return MessageItem.of(url,title, body, newsDto.getNewsTitleImg(), AlarmType.NEWS);
    }
}
