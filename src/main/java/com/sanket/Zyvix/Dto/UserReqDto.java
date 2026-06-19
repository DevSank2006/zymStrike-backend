package com.sanket.Zyvix.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserReqDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be positive")
    @Max(value = 120, message = "Age must be valid")
    private Integer age;
    @NotNull(message = "Height is required")
    @Positive(message = "Height must be positive")
    private Double height;
    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Double weight;
    @NotBlank(message = "Goal is required")
    private String goal;
}
