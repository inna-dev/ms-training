package idev.training.mstraining.web.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestModel {
    @NotNull(message = "First Name cannot be null")
    @Size(min = 2, max = 50, message = "First Name cannot be less then 2 and greater then 50 characters")
    public String firstName;
    @NotNull(message = "Last Name cannot be null")
    @Size(min = 2, max = 50, message = "Last Name cannot be less then 2 and greater then 50 characters")
    public String lastName;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 15, message = "Password cannot be less then 8 and greater then 15 characters")
    public String password;
    @NotNull(message = "Email cannot be null")
    @Email()
    public String email;
}
