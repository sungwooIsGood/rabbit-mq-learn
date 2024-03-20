package com.push.news.repository.inf;

import com.push.news.data.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsJPARepository extends JpaRepository<News, Long> {

    @Query("SELECT n FROM News n WHERE n.newsId = :id")
    <T> T findById(Long id, Class<T> tClass);
}
