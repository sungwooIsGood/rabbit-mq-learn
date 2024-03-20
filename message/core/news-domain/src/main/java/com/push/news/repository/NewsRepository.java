package com.push.news.repository;

import com.push.news.repository.inf.NewsJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsRepository {

    private final NewsJPARepository newsJPARepository;

    public <T> T findById(Long id, Class<T> tClass){
        return newsJPARepository.findById(id,tClass);
    }
}
