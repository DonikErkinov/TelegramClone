package org.tg.domains.auth;

import lombok.*;
import org.tg.domains.Auditable;
import org.tg.enums.Language;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class UserEntity extends Auditable {
    private String username;
    private String password;
    private String phone;
    private String firstName;
    private String lastName;
    private String language = Language.RU.name();

    @Builder(builderMethodName = "childBuilder")
    public UserEntity(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, int deleted, String username, String password, String phone, String firstName, String lastName, String language) {
        super(id, createdAt, updatedAt, deleted);
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
    }
}

