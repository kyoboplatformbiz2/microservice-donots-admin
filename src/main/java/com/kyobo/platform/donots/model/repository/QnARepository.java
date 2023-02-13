package com.kyobo.platform.donots.model.repository;

import com.kyobo.platform.donots.model.entity.QnA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface QnARepository extends JpaRepository<QnA, Long> {
    Page<QnA> findByOpenDateBetweenAndEmailContaining(LocalDateTime start, LocalDateTime end, String search, Pageable pageable);

    Page<QnA> findByOpenDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<QnA> findByEmailContaining(String search, Pageable pageable);
}
