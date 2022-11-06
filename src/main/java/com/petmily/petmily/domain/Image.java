package com.petmily.petmily.domain;

import com.petmily.petmily.dto.image.ImageSaveDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Image {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String uploadedName;

    private String storedName;

    private Image(Member member, Post post, String uploadedName, String storedName) {
        this.member = member;
        this.post = post;
        this.uploadedName = uploadedName;
        this.storedName = storedName;
    }

    public static Image getImage(ImageSaveDto imageSaveDto) {
        return new Image(imageSaveDto.getMember(), imageSaveDto.getPost(), imageSaveDto.getUploadedName(), imageSaveDto.getStoredName());
    }
}
