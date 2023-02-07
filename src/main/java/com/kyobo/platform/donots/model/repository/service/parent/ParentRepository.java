package com.kyobo.platform.donots.model.repository.service.parent;

import com.kyobo.platform.donots.model.entity.service.parent.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long>, ParentRepositoryCustom {
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    Parent findByAccountKey(Long accountKey);
    Parent findByNickname(String nickname);
    void deleteByAccountKey(Long accountKey);
}
