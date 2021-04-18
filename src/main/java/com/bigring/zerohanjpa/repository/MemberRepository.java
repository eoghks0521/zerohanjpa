package com.bigring.zerohanjpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.bigring.zerohanjpa.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //Spring data Jpa 라이브러리가 @PersistenceContext 대신 @AutoWired를 사용할 수 있게 해준다.
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();
    }
}
