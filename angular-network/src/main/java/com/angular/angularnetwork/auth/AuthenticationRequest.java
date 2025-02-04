package com.angular.angularnetwork.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email name is mandatory")
    @NotBlank(message = "Email name is mandatory")
    private String email;
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    @NotEmpty(message = "Password name is mandatory")
    @NotBlank(message = "Password name is mandatory")
    private String password;
}
