package luongdev.switchconfig.configuration.acl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AccessControls extends JpaRepository<AccessControl, UUID> {

    @Query("select ac from AccessControl ac " +
            "left join fetch AccessControlDetail acd on ac.id = acd.accessControl.id where ac.id = ?1")
    AccessControl findIncludeDetails(UUID uuid);
}
