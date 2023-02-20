package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.demo.model.Permission;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission,Long> {

    public Optional<Permission> findPermissionByValue(String value);
}
