package com.kyobo.platform.donots.model.repository.service.parent;

import com.kyobo.platform.donots.model.dto.response.ParentAccountResponse;
import com.kyobo.platform.donots.model.repository.searchcondition.ParentAccountSearchConditionAndTerm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParentRepositoryCustom {

    Page<ParentAccountResponse> search(ParentAccountSearchConditionAndTerm searchConditionAndTerm, Pageable pageable);
}
