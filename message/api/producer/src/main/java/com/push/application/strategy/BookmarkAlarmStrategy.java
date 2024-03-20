package com.push.application.strategy;

import com.push.alarm.data.dto.MessageItem;
import com.push.alarm.data.enums.AlarmType;
import com.push.bookmark.data.dto.BookmarkDto;
import com.push.bookmark.service.BookmarkService;
import com.push.news.data.dto.NewsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookmarkAlarmStrategy implements AlarmStrategy{

    private final BookmarkService bookmarkService;

    @Override
    public MessageItem makeMessage(Long contentsId) {
        BookmarkDto bookmarkDto = bookmarkService.findById(contentsId);

        String url = String.format("https://testUrl/bookmark?bookmarkId=%s",bookmarkDto.getBookmarkId());
        String title = String.format("[북마크] %s와 관련한 알람이 발송되었습니다.", bookmarkDto.getBookmarkContents());
        String body = String.format("얼른 확인해보세요!! 클릭! 클릭!");

        return MessageItem.of(url,title, body, null, AlarmType.BOOKMARK);
    }
}
