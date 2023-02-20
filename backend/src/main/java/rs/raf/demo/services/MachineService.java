package rs.raf.demo.services;

import org.springframework.stereotype.Service;
import rs.raf.demo.dto.MachineCreateDto;
import rs.raf.demo.dto.MachineDto;
import rs.raf.demo.dto.MachineScheduleDto;
import rs.raf.demo.dto.MachineSearchDto;
import rs.raf.demo.model.Machine;
import rs.raf.demo.model.Operation;
import rs.raf.demo.model.Status;

import java.util.Date;
import java.util.List;

@Service
public interface MachineService {

    List<MachineDto> getMachines() throws Exception;
    MachineDto getMachine(Long userId);
    MachineDto createMachine(MachineCreateDto machineCreateDto) throws Exception;
    List<MachineDto> searchMachines(MachineSearchDto machineSearchDto) throws Exception;
    void destroyMachine(Machine machine) throws Exception;
    void startMachine(String machineName) throws Exception;
    void stopMachine(String machineName) throws Exception;
    void restartMachine(String machineName) throws Exception;
    void schedule(MachineScheduleDto machineScheduleDto) throws Exception;




}
