package com.catale.backend.domain.member.entity;

import com.catale.backend.domain.base.BaseEntity;
import com.catale.backend.domain.image.entity.Image;
import com.catale.backend.domain.member.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/* 인덱스테이블 생성*/
@Table(name = "member", indexes = {
        @Index(name = "unique_index_email", columnList = "email"),
        @Index(name = "unique_index_nickname", columnList = "nickname")
})
/* soft delete 관련 */
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE member SET is_deleted = TRUE WHERE member_id = ?")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;

    @Column(name = "nickname", length = 10, nullable = false, unique = true)
    private String nickname;

    @Column(name = "email", length = 50, updatable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_social", nullable = false)
    private boolean isSocial;

    @Column(name = "alc")
    private int alc;

    @Column(name = "sweet")
    private int sweet;

    @Column(name = "sour")
    private int sour;

    @Column(name = "bitter")
    private int bitter;

    @Column(name = "sparking")
    private int sparking;


    public static Member of(SignupRequestDto requestDto, String encodedPassword, boolean isSocial) {
        return Member.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .nickname(requestDto.getNickname())
                .isSocial(isSocial)
                .build();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void uploadProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }


    /**
     * 양방향 관계 - 팔로잉/팔로워, 알림 발신자/수신자, 채팅참여, 메시지, 좋아요
     */

//    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
//    private List<Like> likeList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
//    private List<Comment> commentList = new ArrayList<>();


}