package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 디폴트를 읽기용으로!
//@AllArgsConstructor // 모든 필드로 생성자 만들어줌 (3)
@RequiredArgsConstructor // final 있는 필드로만 생성자 만들어준다!! 얘를 쓰자 (4)
public class MemberService {

    //@Autowired //필드 인젝션(주입)은 변경이 어려워서 잘 안씀.. (1)
    private final MemberRepository memberRepository;


////    @Autowired //생성자 인젝션(주입)을 쓰자!! // 파라미터 1개면 @Autowired 생략 가능 (2)
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional // 데이터를 변경하는 것에는 꼭 필요!
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        // save시에 동시에 여러명이 가입할 수도 있으니
        // 멀티쓰레드 등을 고려해서 DB에 name을 Unique key로 잡아주자!!
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
