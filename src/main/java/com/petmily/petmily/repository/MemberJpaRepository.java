package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.exception.member.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public List<Member> findByNickname(String nickname) {
        return em.createQuery("select m from Member m where m.nickname = :nickname", Member.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    @Override
    public List<Member> findAllByNicknameExceptMe(String nickname, String target) {
        return em.createQuery("select m from Member m where m.nickname like :target and m.nickname <> :nickname", Member.class)
                .setParameter("target", "%" + target + "%")
                .setParameter("nickname", nickname)
                .getResultList();
    }

    @Override
    public void modifyPassword(Member member, String password) {
        Query query = em.createQuery("update Member m set m.password = :password where m = :m")
                .setParameter("password", password)
                .setParameter("m", member);
        int row = query.executeUpdate();
        if (row != 1) {
            throw new MemberException("Member ERROR!!!");
        }
    }
}
