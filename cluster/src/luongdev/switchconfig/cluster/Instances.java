package luongdev.switchconfig.cluster;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Instances extends JpaRepository<Instance, String> {
}
