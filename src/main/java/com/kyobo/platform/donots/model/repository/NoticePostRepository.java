package com.kyobo.platform.donots.model.repository;

import com.kyobo.platform.donots.model.entity.NoticePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticePostRepository extends JpaRepository<NoticePost, Long> {

    NoticePost findByNoticePostKey(Long noticePostKey);
    List<NoticePost> findAllByOrderByCreatedDateDesc();
}
