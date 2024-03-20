package com.push.news.service;

import com.push.news.data.dto.NewsDto;
import com.push.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsDto findById(Long id){
        return newsRepository.findById(id,NewsDto.class);
    }
}
