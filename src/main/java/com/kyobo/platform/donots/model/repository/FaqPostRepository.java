package com.kyobo.platform.donots.model.repository;

import com.kyobo.platform.donots.model.entity.FaqPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqPostRepository extends JpaRepository<FaqPost, Long> {
    Page<FaqPost> findAllByOrderByCreatedDatetimeDesc(Pageable pageable);

    Page<FaqPost> findByQuestionContainingOrAnswerContainingOrderByCreatedDatetimeDesc(String searchQuestion, String searchAnswer, Pageable pageable);
}
