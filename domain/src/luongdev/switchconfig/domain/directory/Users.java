package luongdev.switchconfig.domain.directory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Users extends JpaRepository<User, String> {

    @Query("select distinct u from User u left join fetch u.groups left join fetch u.settings")
    List<User> findAllIncludeSettingsAndGroups();

}
