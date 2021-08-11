package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

public class MemberService {

	private final MemberRepository memberRepository;

	public MemberService(MemoryMemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	/**
	 * 회원가입
	 */
	public Long join(Member member) {
		
		/* 1번째 방법
		 * Optional<Member> result = memberRepository.findByName(member.getName());
		 * result.ifPresent(m -> { throw new IllegalStateException("이미 존재하는 회원입니다.");
		 * });
		 */
		
		/* 2번째 방법
		 * memberRepository.findByName(member.getName()) .ifPresent(m -> { throw new
		 * IllegalStateException("이미 존재하는 회원입니다."); });
		 */
		
		validateDuplicateMember(member); // 중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		memberRepository.findByName(member.getName()).ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		});
	}

	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Optional<Member> findOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
}