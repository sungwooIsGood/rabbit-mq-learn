package com.push.news.data.dto;

import com.push.news.data.entity.News;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NewsDto {

    private Long newsId;
    private String newsTitle;
    private String newsTitleImg;
    private String newsBody;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public NewsDto(Long newsId, String newsTitle, String newsTitleImg, String newsBody,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsTitleImg = newsTitleImg;
        this.newsBody = newsBody;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public NewsDto(News news) {
        this.newsId = news.getNewsId();
        this.newsTitle = news.getNewsTitle();
        this.newsTitleImg = news.getNewsTitleImg();
        this.newsBody = news.getNewsBody();
        this.createdAt = news.getCreatedAt();
        this.updatedAt = news.getUpdatedAt();
    }
}
