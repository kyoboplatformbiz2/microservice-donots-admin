package com.kyobo.platform.donots.service;

import com.kyobo.platform.donots.model.entity.FaqPost;
import com.kyobo.platform.donots.model.repository.FaqPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FaqPostService {
    private FaqPostRepository faqPostRepository;

    private EntityManager em;

    public FaqPostService(FaqPostRepository faqPostRepository, EntityManager em) {
        this.faqPostRepository = faqPostRepository;
        this.em = em;
    }

    public List<FaqPost> findAllFaqPostSummaries() {
        List<FaqPost> faqPosts = faqPostRepository.findAll();
        return faqPosts;
    }

    public FaqPost findFaqPostDetailsByKey(Long key) {
        FaqPost faqPost = faqPostRepository.findById(key).get();
        return faqPost;
    }

    @Transactional
    public Long registerFaqPost(FaqPost faqPost) {
        em.persist(faqPost);
        return faqPost.getKey();
    }

    @Transactional
    public void modifyFaqPost(Long key, FaqPost faqPost) {
        FaqPost foundFaqPost = faqPostRepository.findById(key).get();
        foundFaqPost.deepCopyAllExceptKeyFrom(faqPost);
        em.persist(foundFaqPost);
    }

    @Transactional
    public void deleteFaqPost(Long key) {
        faqPostRepository.deleteById(key);
    }
}
