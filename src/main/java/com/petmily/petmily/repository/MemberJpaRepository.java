package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository implements IMemberRepository{

    private final EntityManager em;

    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    @Override
    public List<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email")
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public List<Member> findByNickname(String nickname) {
        return em.createQuery("select m from Member m where m.nickname = :nickname")
                .setParameter("nickname", nickname)
                .getResultList();
    }

    @Override
    public List<Member> findAllByNicknameExceptMe(String nickname, String target) {
        return em.createQuery("select m from Member m where m.nickname like :target and m.nickname <> :nickname")
                .setParameter("target", "%" + target + "%")
                .setParameter("nickname", nickname)
                .getResultList();
    }
}
