package com.push.bookmark.repository;

import com.push.bookmark.data.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkJPARepository extends JpaRepository<Bookmark,Long> {

    @Query("SELECT b FROM Bookmark b WHERE b.bookmarkId = :id")
    <T> T findById(Long id, Class<T> tClass);
}
