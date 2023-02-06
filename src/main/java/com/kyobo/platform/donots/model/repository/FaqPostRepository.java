package com.kyobo.platform.donots.model.repository;

import com.kyobo.platform.donots.model.entity.FaqCategory;
import com.kyobo.platform.donots.model.entity.FaqPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaqPostRepository extends JpaRepository<FaqPost, Long> {
    List<FaqPost> findByFaqCategory(FaqCategory faqCategory);
    Page<FaqPost> findAllByOrderByCreatedDatetimeDesc(Pageable pageable);

    Page<FaqPost> findByQuestionContainingOrAnswerContainingOrderByCreatedDatetimeDesc(String searchQuestion, String searchAnswer, Pageable pageable);
}
