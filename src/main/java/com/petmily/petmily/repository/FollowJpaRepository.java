package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Follow;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.exception.FollowException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FollowJpaRepository implements IFollowRepository {

    private final EntityManager em;

    @Override
    public void save(Member member, Member targetMember) {
        Follow follow = new Follow(member, targetMember);
        em.persist(follow);
    }

    @Override
    public List<Follow> findAll(Member member) {
        return em.createQuery("select f from Follow f where f.member = :member")
                .setParameter("member", member)
                .getResultList();
    }

    @Override
    public void remove(Member member, Member targetMember) {
        Query query = em.createQuery("delete from Follow f where f.member = :member and f.targetMember = :targetMember")
                .setParameter("member", member)
                .setParameter("targetMember", targetMember);
        int rows = query.executeUpdate();
        if (rows != 1) {
            log.error("UnFollow ERROR!!");
            throw new FollowException("UnFollow error!!");
        }
    }
}
