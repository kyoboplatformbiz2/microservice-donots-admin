package com.kyobo.platform.donots.model.repository.service.parent;

import com.kyobo.platform.donots.model.dto.response.ParentAccountResponse;
import com.kyobo.platform.donots.model.dto.response.QParentAccountResponse;
import com.kyobo.platform.donots.model.entity.service.parent.ParentGrade;
import com.kyobo.platform.donots.model.entity.service.parent.ParentType;
import com.kyobo.platform.donots.model.repository.searchcondition.ParentAccountSearchCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.kyobo.platform.donots.model.entity.service.account.QAccount.account;
import static com.kyobo.platform.donots.model.entity.service.parent.QParent.parent;
import static org.springframework.util.StringUtils.hasText;

public class ParentRepositoryImpl implements ParentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ParentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ParentAccountResponse> search(ParentAccountSearchCondition condition, Pageable pageable) {

        QueryResults<ParentAccountResponse> results = queryFactory
                .select(new QParentAccountResponse(
                        parent.type,
                        parent.grade,
                        parent.nickname,
                        account.id,
                        account.createdAt,
                        // TODO 복호화
                        account.phoneNumber,
                        parent.email,
                        account.lastSignInAt,
                        parent.key))
                .from(parent)
                .leftJoin(account).on(parent.accountKey.eq(account.accountKey))
                .where(
                        joinDateBetween(condition.getJoinDateFrom(), condition.getJoinDateTo()),
                        typeEq(condition.getType()),
                        gradeEq(condition.getGrade()),
                        nicknameLike(condition.getNickname()),
                        idLike(condition.getId()),
                        phoneNumberLike(condition.getPhoneNumber()),
                        emailLike(condition.getEmail()),
                        keyEq(condition.getKey())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ParentAccountResponse> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression joinDateBetween(LocalDateTime joinDateFrom, LocalDateTime joinDateTo) {
        if (joinDateFrom == null || joinDateTo == null)
            return null;

        return account.createdAt.between(joinDateFrom, joinDateTo.plusDays(1));
    }
    private BooleanExpression typeEq(ParentType type) {
        if (type == null)
            return null;

        return parent.type.eq(type);
    }
    private BooleanExpression gradeEq(ParentGrade grade) {
        if (grade == null)
            return null;

        return parent.grade.eq(grade);
    }
    private BooleanExpression nicknameLike(String nickname) {
        return hasText(nickname) ? parent.nickname.contains(nickname) : null;
    }
    private BooleanExpression idLike(String id) {
        return hasText(id) ? account.id.contains(id) : null;
    }
    private BooleanExpression phoneNumberLike(String phoneNumber) {
        return hasText(phoneNumber) ? account.phoneNumber.contains(phoneNumber) : null;
    }
    private BooleanExpression emailLike(String email) {
        return hasText(email) ? parent.email.contains(email) : null;
    }
    private BooleanExpression keyEq(Long key) {
        if (key == null || key == 0)
            return null;

        return parent.key.eq(key);
    }
}
