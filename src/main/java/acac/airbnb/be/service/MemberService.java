package acac.airbnb.be.service;

import acac.airbnb.be.domain.MemberDto;
import acac.airbnb.be.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository; //의존성 주입

    @Transactional
    public void createUser(MemberDto memberDto){
        validateDuplicateMemberEmail(memberDto.getEmail()); //이메일로 중복 회원 검사
        MemberDto savedMemberDto = memberRepository.save(memberDto);
    }

    public void validateDuplicateMemberEmail(String email){
        memberRepository.findByEmail(email)
                .ifPresent(result ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다."); //동일한 이메일 발견시 예외 발생
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<MemberDto> findMembers(){
        return memberRepository.findAll();
    }//반환하는 값이 List이기 때문에 findAll 수행 후 그냥 반환해주면 됨.

    public Optional<MemberDto> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
