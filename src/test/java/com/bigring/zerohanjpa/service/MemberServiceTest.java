package com.bigring.zerohanjpa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.bigring.zerohanjpa.domain.Member;
import com.bigring.zerohanjpa.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test
    void 중복_회원_예외() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        Member member1 = new Member();
        member1.setName("kim");
        //when
        memberService.join(member);

        assertThrows(IllegalStateException.class, () -> memberService.join(member1));
    }

}