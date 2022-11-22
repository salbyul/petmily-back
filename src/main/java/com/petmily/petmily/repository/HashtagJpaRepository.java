package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Hashtag;
import com.petmily.petmily.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HashtagJpaRepository implements IHashtagRepository{

    private final EntityManager em;

    @Override
    public void save(Hashtag hashtag) {
        em.persist(hashtag);
    }

    @Override
    public List<Hashtag> findByPost(Post post) {
        return em.createQuery("select h from Hashtag h where h.post = :post", Hashtag.class)
                .setParameter("post", post)
                .getResultList();
    }

    @Override
    public List<Hashtag> findByHashtagName(String hashtagName) {
        return em.createQuery("select h from Hashtag h where h.hashtagName = :hashtagName", Hashtag.class)
                .setParameter("hashtagName", hashtagName)
                .getResultList();
    }
}
