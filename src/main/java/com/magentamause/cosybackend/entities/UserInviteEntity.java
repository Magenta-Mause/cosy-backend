package com.magentamause.cosybackend.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInviteEntity {
    @Id @GeneratedValue private String uuid;

    @Column(nullable = false)
    private String inviteKey;

    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invited_by_user_id")
    private UserEntity invitedBy;

    // duplicate here, because we want to be able to access it without joining the user table
    @Column(nullable = false)
    private String invitedByUserUuid;
}
