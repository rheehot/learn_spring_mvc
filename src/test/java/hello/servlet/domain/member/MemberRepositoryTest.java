package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = new Member("memberA", 20);

        //when
        Member save = memberRepository.save(member);


        //then
        Member findMember = memberRepository.findById(save.getId());
        assertThat(findMember).isEqualTo(save);
    }

    @Test
    void findAll() {
        Member member1 = new Member("memberA", 20);
        Member member2 = new Member("memberB", 25);

        Member save1 = memberRepository.save(member1);
        Member save2 = memberRepository.save(member2);

        List<Member> all = memberRepository.findAll();
        for (Member member : all) {
            System.out.println("member = " + member);
        }

        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0).getUsername()).isEqualTo("memberA");
        assertThat(all).contains(member1, member2);
    }
}