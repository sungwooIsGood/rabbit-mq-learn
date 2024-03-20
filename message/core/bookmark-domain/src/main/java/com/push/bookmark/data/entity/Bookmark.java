package com.push.bookmark.data.entity;

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
public class Bookmark extends DateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(10) unsigned")
    private Long bookmarkId;
    private Long postId;
    private String bookmarkContents;
}
