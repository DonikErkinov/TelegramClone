package org.tg.domains.auth;

import lombok.*;
import org.tg.domains.Auditable;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingsEntity extends Auditable {
    private String localPassword;

    @Builder(builderMethodName = "childBuilder")
    public UserSettingsEntity(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, int deleted, String localPassword) {
        super(id, createdAt, updatedAt, deleted);
        this.localPassword = localPassword;
    }
}
