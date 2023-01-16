package org.tg.domains;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auditable implements BaseEntity {
    protected Long id;
    protected LocalDateTime createdAt = LocalDateTime.now();
    protected LocalDateTime updatedAt;
    protected int deleted;
}
