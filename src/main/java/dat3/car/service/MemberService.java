package dat3.car.service;

import dat3.car.dto.MemberRequest;
import dat3.car.dto.MemberResponse;
import dat3.car.entity.Member;
import dat3.car.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public MemberResponse addMember(MemberRequest memberRequest){
        //Later you should add error checks --> Missing arguments, email taken etc.
        Member newMember = MemberRequest.getMemberEntity(memberRequest);
        newMember = memberRepository.save(newMember);

        if(memberRepository.existsById(memberRequest.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Member with this ID already exist");
        }
        if(memberRepository.existsByEmail(memberRequest.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Member with this Email already exist");
        }
        return new MemberResponse(newMember, false);
    }


    public List<MemberResponse> getMembers(boolean includeAll) {

        List<Member> members = memberRepository.findAll();
        List<MemberResponse>memberResponses = members.stream().map(m->new MemberResponse(m,includeAll)).toList();

        return memberResponses;
    }


    public MemberResponse findMemberByUsername(String m1){
        Member m = memberRepository.findById(m1).orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Member with this ID does not exist"));
        MemberResponse mr = new MemberResponse(m, true);
        return mr;
    }

    public ResponseEntity<Boolean> editMember(MemberRequest body, String username){
        Member editedMember = memberRepository.findById(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with this ID does not exist"));

        editedMember.setEmail(body.getEmail());
        editedMember.setPassword(body.getPassword());
        editedMember.setFirstName(body.getFirstName());
        editedMember.setLastName(body.getLastName());
        editedMember.setStreet(body.getStreet());
        editedMember.setCity(body.getCity());
        editedMember.setZip(body.getZip());

        //Member editedMember = MemberRequest.getMemberEntity(body);
        memberRepository.save(editedMember);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    public MemberResponse setRankingForUsers(String username, int value){

        Optional<Member> m = memberRepository.findById(username);
        Member member = m.orElse(null);
        member.setRanking(value);
        memberRepository.save(member);
        MemberResponse mr = new MemberResponse(member,true);
        return mr;
    }
    public void deleteMemberByUsername(String username){

        Optional<Member> m = memberRepository.findById(username);
        Member member = m.orElse(null);
        memberRepository.delete(member);

    }

}
