package idev.training.mstraining.web.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestModel {
    @Email
    private String email;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 15, message = "Password cannot be less then 8 and greater then 15 characters")
    private String password;
}
