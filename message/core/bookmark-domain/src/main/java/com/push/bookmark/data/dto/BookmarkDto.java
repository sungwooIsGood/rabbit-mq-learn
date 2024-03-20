package com.push.bookmark.data.dto;

import com.push.bookmark.data.entity.Bookmark;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkDto {

    private Long bookmarkId;
    private Long postId;
    private String bookmarkContents;

    @Builder
    public BookmarkDto(Long bookmarkId, Long postId, String bookmarkContents) {
        this.bookmarkId = bookmarkId;
        this.postId = postId;
        this.bookmarkContents = bookmarkContents;
    }

    public BookmarkDto(Bookmark bookmark) {
        this.bookmarkId = bookmark.getBookmarkId();
        this.postId = bookmark.getPostId();
        this.bookmarkContents = bookmark.getBookmarkContents();
    }
}
