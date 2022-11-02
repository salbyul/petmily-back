package com.petmily.petmily.dto;

import com.petmily.petmily.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberProfileDto {

    private String email;
    private String nickname;
    private String statusMessage;

    public static MemberProfileDto getMemberProfileDto(Member member) {
        MemberProfileDto dto = new MemberProfileDto();
        dto.setEmail(member.getEmail());
        dto.setNickname(member.getNickname());
        dto.setStatusMessage(member.getStatusMessage());
        return dto;
    }
}
