package com.kyobo.platform.donots.model.repository;

import com.kyobo.platform.donots.model.entity.TermsOfService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermsOfServiceRepository extends JpaRepository<TermsOfService, Long> {

    List<TermsOfService> findAllByOrderByCreatedDatetimeDesc();
    List<TermsOfService> findByTitleOrderByCreatedDatetimeDesc(String title);
}
