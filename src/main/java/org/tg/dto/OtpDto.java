package org.tg.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpDto {
    private String phone;
    private String otp;
    private LocalDateTime expires;
}
