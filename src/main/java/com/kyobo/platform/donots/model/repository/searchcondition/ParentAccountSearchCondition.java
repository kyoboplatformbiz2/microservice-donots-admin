package com.kyobo.platform.donots.model.repository.searchcondition;

import com.kyobo.platform.donots.model.entity.service.parent.ParentGrade;
import com.kyobo.platform.donots.model.entity.service.parent.ParentType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ParentAccountSearchCondition {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE/*, pattern = "yyyy-MM-dd HH:mm"*/)
    private LocalDateTime joinDateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime joinDateTo;
    private ParentType type;
    private ParentGrade grade;
    private String nickname;
    private String id;
    private String phoneNumber;
    private String email;
    private Long key;
}
