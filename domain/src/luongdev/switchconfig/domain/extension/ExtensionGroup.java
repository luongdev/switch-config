package luongdev.switchconfig.domain.extension;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "extension_groups")
public class ExtensionGroup {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "extension_group_map",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "extension") }
    )
    private Set<Extension> extensions;
}
