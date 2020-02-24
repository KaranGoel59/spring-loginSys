/*
   POJO class for Group
 */

package karangoel.codes.gchat.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class UserGroup {

    // == fields ==
    private Long id;

    @NotNull(message = "{user.group.name}")
    private String name;

}
