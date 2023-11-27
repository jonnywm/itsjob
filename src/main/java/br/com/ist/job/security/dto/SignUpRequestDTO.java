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
public class SignUpRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public static SignUpRequestDTO convert(User admin, String password) {
        return SignUpRequestDTO.builder()
                .email(admin.getEmail())
                .password(password)
                .build();
    }
}
