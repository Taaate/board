package hello.board.controller.dto.res;

import hello.board.entity.member.Member;
import lombok.Data;

@Data
public class MemberResDto {

    private Long id;
    private String name;
    private int age;

    private String loginId;
    private String password;

    public MemberResDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.age = member.getAge();
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
    }
}
