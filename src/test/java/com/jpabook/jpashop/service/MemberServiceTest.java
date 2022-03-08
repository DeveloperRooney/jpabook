package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입")
    @Rollback(false)
    public void join() throws Exception {
        Member member = new Member();
        member.setName("hancoding");

        Long saveId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(saveId));

    }
    
    @Test(expected = IllegalStateException.class)
    @DisplayName("이름 중복 체크")
    public void duplicateMember() throws Exception {
        // given
        Member memberA = new Member();
        memberA.setName("MemberA");

        Member memberB = new Member();
        memberA.setName("MemberA");

        // when
        memberService.join(memberA);
        memberService.join(memberB);

        // then
        fail("예외가 발생해야 한다.");
    }
}