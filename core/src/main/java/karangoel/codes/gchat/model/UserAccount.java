/*
   POJO class for User
 */

package karangoel.codes.gchat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import karangoel.codes.gchat.validator.email.UserEmailSignUp;
import karangoel.codes.gchat.validator.password.Password;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class UserAccount {

    // == fields ==
    private Long id;

    @NotNull(message = "${user.name}")
    private String firstName;

    @NotNull(message = "${user.name}")
    private String lastName;

    @UserEmailSignUp
    private String email;

    @Password
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
