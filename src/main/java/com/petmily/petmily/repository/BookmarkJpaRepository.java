package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Bookmark;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;
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
public class BookmarkJpaRepository implements IBookmarkRepository{

    private final EntityManager em;

    @Override
    public void save(Bookmark bookmark) {
        em.persist(bookmark);
    }

    @Override
    public void remove(Long id) {
        Query query = em.createQuery("delete from Bookmark b where b.id = :id")
                .setParameter("id", id);
        int rows = query.executeUpdate();
        if (rows != 1) {
            log.error("UnBookmark ERROR!!");
            throw new FollowException("unbookmark error");
        }
    }

    @Override
    public List<Bookmark> findByMember(Member member) {
        return em.createQuery("select b from Bookmark b where b.member = :member", Bookmark.class)
                .setParameter("member", member)
                .getResultList();
    }

    @Override
    public void removeByMemberPost(Member member, Post post) {
        Query query = em.createQuery("delete from Bookmark b where b.member = :member and b.post = :post")
                .setParameter("member", member)
                .setParameter("post", post);
        int rows = query.executeUpdate();
        if (rows != 1) {
            log.error("UnBookmark ERROR!!");
            throw new FollowException("unbookmark error");
        }
    }

    @Override
    public List<Bookmark> findByMemberPost(Member member, Post post) {
        return em.createQuery("select b from Bookmark b where b.post = :post and b.member = :member", Bookmark.class)
                .setParameter("member", member)
                .setParameter("post", post)
                .getResultList();
    }
}
