package com.push.news.data.entity;

import com.push.globalDomain.entity.DateTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class News extends DateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(10) unsigned")
    private Long newsId;
    private String newsTitle;
    private String newsTitleImg;
    private String newsBody;
}
