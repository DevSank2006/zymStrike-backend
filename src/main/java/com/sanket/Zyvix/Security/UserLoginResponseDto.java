package com.sanket.Zyvix.Security;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginResponseDto {
    private long id;
    private String token;
}
