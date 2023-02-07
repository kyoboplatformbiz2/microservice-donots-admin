package com.kyobo.platform.donots.service;

import com.kyobo.platform.donots.common.exception.ParentNotFoundException;
import com.kyobo.platform.donots.model.dto.response.ParentAccountDetailsResponse;
import com.kyobo.platform.donots.model.dto.response.ParentAccountResponse;
import com.kyobo.platform.donots.model.entity.service.account.Account;
import com.kyobo.platform.donots.model.entity.service.account.SocialAccount;
import com.kyobo.platform.donots.model.entity.service.parent.Parent;
import com.kyobo.platform.donots.model.entity.service.parent.ParentType;
import com.kyobo.platform.donots.model.repository.searchcondition.ParentAccountSearchCondition;
import com.kyobo.platform.donots.model.repository.service.account.AccountRepository;
import com.kyobo.platform.donots.model.repository.service.account.SocialAccountRepository;
import com.kyobo.platform.donots.model.repository.service.parent.ParentRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Slf4j
public class MemberManagementService {
    private final ParentRepository parentRepository;
    private final AccountRepository accountRepository;
    private final SocialAccountRepository socialAccountRepository;

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberManagementService(ParentRepository parentRepository, AccountRepository accountRepository, SocialAccountRepository socialAccountRepository, EntityManager em) {
        this.parentRepository = parentRepository;
        this.accountRepository = accountRepository;
        this.socialAccountRepository = socialAccountRepository;
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional
    public Page<ParentAccountResponse> findAllMembersWithAccount(ParentAccountSearchCondition condition, Pageable pageable) {

        Page<ParentAccountResponse> parentWithAccountResponses = parentRepository.search(condition, pageable);
        log.info("parentWithAccountResponses: "+ parentWithAccountResponses);
        for (ParentAccountResponse parentAccountResponse : parentWithAccountResponses) {
            log.info(parentAccountResponse.toString());
        }

        return parentWithAccountResponses;
    }

    @Transactional
    public ParentAccountDetailsResponse findParentAccountDetails(Long parentKey) throws Exception {
        log.info("MemberManagementService.findParentAccountDetails");

        Parent foundParent = parentRepository.findById(parentKey)
                                .orElseThrow(() -> new ParentNotFoundException());

        // TODO 예외처리
        Account foundAccount = accountRepository.findByAccountKey(foundParent.getAccountKey());

        List<SocialAccount> foundSocialAccounts = socialAccountRepository.findByAccountKey(foundParent.getAccountKey());

        return ParentAccountDetailsResponse.from(foundParent, foundAccount, foundSocialAccounts);
    }

    @Transactional
    public void modifyParentType(Long key, ParentType parentType) {
        log.info("MemberManagementService.modifyParentType");

        Parent foundParent = parentRepository.findById(key)
                                .orElseThrow(() -> new ParentNotFoundException());

        foundParent.setType(parentType);
    }
}
