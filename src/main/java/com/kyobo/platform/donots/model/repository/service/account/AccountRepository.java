package com.kyobo.platform.donots.model.repository.service.account;

import com.kyobo.platform.donots.model.entity.service.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findById(String id);
    Account findByIdAndCi(String id, String ci);
    Account findByAccountKey(Long key);
    Account findByCiAndPhoneNumber(String ci, String phoneNumber);
    Account findByCi(String ci);
    boolean existsByCi(String ci);
    boolean existsById(String id);

    boolean existsByIdAndCi(String id, String ci);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account a SET a.password = :password where a.id = :id and a.ci = :ci")
    int updateAccountPassword(@Param(value="password") String password, @Param(value = "id") String id, @Param(value="ci") String ci );

}
