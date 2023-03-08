package dat3.car.api;

import dat3.car.dto.MemberRequest;
import dat3.car.dto.MemberResponse;
import dat3.car.entity.Member;
import dat3.car.repository.MemberRepository;
import dat3.car.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/members")
class MemberController {

    MemberService memberService;

    public MemberController(MemberService memberService){

        this.memberService = memberService;
    }


    //ADMIN ONLY
    @GetMapping
    List<MemberResponse> getMembers(){
        return memberService.getMembers(false);
                }

    //ADMIN ONLY
    @GetMapping(path = "/{username}")
    MemberResponse getMemberById(@PathVariable String username) throws Exception {
        return memberService.findMemberByUsername(username);
    }

    //ANONYMOUS
    //@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping()
    MemberResponse addMember(@RequestBody MemberRequest body){
        return memberService.addMember(body);
    }

    //MEMBER
    @PutMapping("/{username}")
    public ResponseEntity<Boolean> editMember(@RequestBody MemberRequest body, @PathVariable String username) {

        memberService.editMember(body, username);
        return ResponseEntity.ok(true);
    }

    //ADMIN ONLY
    @PatchMapping("/ranking/{username}/{value}")
    public MemberResponse setRankingForUser(@PathVariable String username, @PathVariable int value) {
        return memberService.setRankingForUsers(username, value);
    }

    // ADMIN ONLY
    @DeleteMapping("/{username}")
    void deleteMemberByUsername(@PathVariable String username) {
        memberService.deleteMemberByUsername(username);
    }


}
