package br.com.ist.job.security.dto;

import br.com.ist.job.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequestDTO {
    private String email;
    private String password;

    public static SigninRequestDTO convert(User user, String password) {
        return SigninRequestDTO.builder()
                .email(user.getEmail())
                .password(password)
                .build();
    }
}