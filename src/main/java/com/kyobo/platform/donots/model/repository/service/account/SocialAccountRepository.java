package com.kyobo.platform.donots.model.repository.service.account;


import com.kyobo.platform.donots.model.entity.service.account.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
    List<SocialAccount> findByAccountKey(Long accountKey);
}

