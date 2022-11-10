package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Like;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;
import com.petmily.petmily.exception.LikeException;
import com.petmily.petmily.exception.follow.FollowException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LikeJpaRepository implements ILikeRepository{

    private final EntityManager em;
    private final IPostRepository postRepository;

    @Override
    public void save(Like like) {
        em.persist(like);
    }

    @Override
    public void remove(Member member, Post post) {
        Query query = em.createQuery("delete from Like l where l.member = :member and l.post = :post")
                .setParameter("member", member)
                .setParameter("post", post);
        int rows = query.executeUpdate();
        if (rows != 1) {
            log.error("LIKE ERROR!!");
            throw new LikeException("like error");
        }
    }

    @Override
    public List<Like> findByMember(Member member) {
        return em.createQuery("select l from Like l where l.member = :member")
                .setParameter("member", member.getNickname())
                .getResultList();
    }

    @Override
    public List<Like> findByMemberPost(Member member, Post post) {
        return em.createQuery("select l from Like l where l.member = :member and l.post = :post")
                .setParameter("member", member)
                .setParameter("post", post)
                .getResultList();
    }

    @Override
    public boolean checkByMemberPost(Member member, Long postId) {
        Post findPost = postRepository.findById(postId);
        List resultList = em.createQuery("select l from Like l where l.member = :member and l.post = :post")
                .setParameter("member", member)
                .setParameter("post", findPost)
                .getResultList();
        if (resultList.size() > 1) {
            throw new LikeException("LIKE ERROR!!");
        }
        if (resultList.size() == 0) return false;
        else return true;
    }
}
