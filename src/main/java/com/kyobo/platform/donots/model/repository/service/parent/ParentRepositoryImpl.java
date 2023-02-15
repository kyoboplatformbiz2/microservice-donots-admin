package com.kyobo.platform.donots.model.repository.service.parent;

import com.kyobo.platform.donots.common.exception.DefaultException;
import com.kyobo.platform.donots.model.dto.response.ParentAccountResponse;
import com.kyobo.platform.donots.model.dto.response.QParentAccountResponse;
import com.kyobo.platform.donots.model.entity.service.parent.ParentGrade;
import com.kyobo.platform.donots.model.entity.service.parent.ParentType;
import com.kyobo.platform.donots.model.repository.searchcondition.ParentAccountSearchConditionAndTerm;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.kyobo.platform.donots.model.entity.service.account.QAccount.account;
import static com.kyobo.platform.donots.model.entity.service.parent.QParent.parent;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
public class ParentRepositoryImpl implements ParentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ParentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ParentAccountResponse> search(ParentAccountSearchConditionAndTerm searchConditionAndTerm, Pageable pageable) {

        // TODO 추후 PageRequest 사용하도록 개선
        if (searchConditionAndTerm.getSearchCondition() == null)
            throw new DefaultException("검색조건이 null입니다");

        BooleanExpression booleanExpressionOfSelectedSearchConditionAndTerm = null;
        switch (searchConditionAndTerm.getSearchCondition()) {
            case NICKNAME -> booleanExpressionOfSelectedSearchConditionAndTerm = nicknameLike(searchConditionAndTerm.getSearchTerm());
            case ID -> booleanExpressionOfSelectedSearchConditionAndTerm = idLike(searchConditionAndTerm.getSearchTerm());
            case PHONE_NUMBER -> booleanExpressionOfSelectedSearchConditionAndTerm = phoneNumberLike(searchConditionAndTerm.getSearchTerm());
            case EMAIL -> booleanExpressionOfSelectedSearchConditionAndTerm = emailLike(searchConditionAndTerm.getSearchTerm());
            case MEMBER_KEY -> booleanExpressionOfSelectedSearchConditionAndTerm = keyEq(Long.parseLong(searchConditionAndTerm.getSearchTerm()));
        }

        OrderSpecifier<LocalDateTime> orderSpecifier = account.createdAt.desc();
        switch (searchConditionAndTerm.getOrderingCriterion()) {
            case CREATED_AT_DESC -> orderSpecifier = account.createdAt.desc();
            case LAST_LOGIN_AT_DESC -> orderSpecifier = account.lastSignInAt.desc();
        }

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
                        joinDateBetween(searchConditionAndTerm.getJoinDateFrom(), searchConditionAndTerm.getJoinDateTo()),
                        typeEq(searchConditionAndTerm.getType()),
                        gradeEq(searchConditionAndTerm.getGrade()),
                        booleanExpressionOfSelectedSearchConditionAndTerm
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
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
