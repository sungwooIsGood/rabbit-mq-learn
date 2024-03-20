package com.push.bookmark.repository;

import com.push.bookmark.data.dto.BookmarkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookmarkRepository {

    private final BookmarkJPARepository bookmarkJPARepository;

    public <T> T findById(Long id, Class<T> tClass) {
        return bookmarkJPARepository.findById(id,tClass);
    }
}
