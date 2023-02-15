package com.kyobo.platform.donots.model.repository.searchcondition;

import com.kyobo.platform.donots.model.entity.service.parent.ParentGrade;
import com.kyobo.platform.donots.model.entity.service.parent.ParentType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ParentAccountSearchConditionAndTerm {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime joinDateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime joinDateTo;
    private ParentType type;
    private ParentGrade grade;
    private ParentAccountSearchCondition searchCondition;
    private String searchTerm;
    private OrderingCriterion orderingCriterion;
}
