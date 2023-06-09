package dataart.workshop.dto.v1;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
