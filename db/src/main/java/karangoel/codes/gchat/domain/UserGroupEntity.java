/*
    Group Entity
 */

package karangoel.codes.gchat.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user_group")
public class UserGroupEntity {
    // == columns ==
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 255)
    private String name;

    // == associations ==
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "groups")
    final private Set<UserAccountEntity> users = new HashSet<>();
}
