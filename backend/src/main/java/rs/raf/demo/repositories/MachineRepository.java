package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.demo.model.Machine;
import rs.raf.demo.model.Status;

import java.util.Date;
import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    public List<Machine> findMachinesByMachineNameLike(String machineName);
    public List<Machine> findMachinesByCreatedAtBetween(Date createdAfter, Date createdBefore);
    public List<Machine> findMachinesByCreatedAtAfter(Date createdAfter);
    public List<Machine> findMachinesByCreatedAtBefore(Date createdBefore);
    public List<Machine> findMachinesByStatus(Status status);
    public Machine findMachineByMachineName(String machineName);
}
