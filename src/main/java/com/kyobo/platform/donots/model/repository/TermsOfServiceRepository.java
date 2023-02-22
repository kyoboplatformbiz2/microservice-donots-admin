package com.kyobo.platform.donots.model.repository;

import com.kyobo.platform.donots.model.entity.TermsOfService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermsOfServiceRepository extends JpaRepository<TermsOfService, Long> {

    List<TermsOfService> findAllByOrderByCreatedDatetimeDesc();
    List<TermsOfService> findByTitleOrderByCreatedDatetimeDesc(String title);

    @Query(value = "SELECT * " +
            "FROM ( " +
            "   SELECT terms_of_service_key, admin_id, body, body_html_file_url, created_datetime, last_modified_datetime, posting_end_datetime, posting_start_datetime, title, version, " +
            "   RANK() OVER(PARTITION BY title ORDER BY created_datetime DESC) FROM terms_of_service " +
            ") a " +
            "WHERE rank = 1",
            nativeQuery = true
    )
    List<TermsOfService> findPartitionedByTitleMostRecent();
}
