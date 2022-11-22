package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Hashtag;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostJpaRepository implements IPostRepository{

    private final EntityManager em;

    @Override
    public void save(Post post) {
        em.persist(post);
    }

    @Override
    public void delete(Post post) {
    }

    @Override
    public List<Post> findAllByMember(Member member) {
        return em.createQuery("select p from Post p where p.member = :member", Post.class)
                .setParameter("member", member)
                .getResultList();
    }

    @Override
    public Post findById(Long id) {
        List<Post> findPosts = em.createQuery("select p from Post p where p.id = :id", Post.class)
                .setParameter("id", id)
                .getResultList();
        if (findPosts.size() != 1) {
            throw new IllegalArgumentException("FIND BY ID ERROR!!");
        }
        return findPosts.get(0);
    }

    @Override
    public List<Post> findAllFriend(Member member) {
        return null;
    }
}
