package com.kyobo.platform.donots.model.repository;

import com.kyobo.platform.donots.model.entity.FaqCategory;
import com.kyobo.platform.donots.model.entity.FaqPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqPostRepository extends JpaRepository<FaqPost, Long> {
    List<FaqPost> findByFaqCategory(FaqCategory faqCategory);
}
