package com.push.bookmark.service;

import com.push.bookmark.data.dto.BookmarkDto;
import com.push.bookmark.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository newsRepository;

    public BookmarkDto findById(Long id){
        return newsRepository.findById(id,BookmarkDto.class);
    }
}
