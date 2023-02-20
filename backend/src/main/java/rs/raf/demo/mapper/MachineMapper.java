package rs.raf.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import rs.raf.demo.dto.MachineCreateDto;
import rs.raf.demo.dto.MachineDto;
import rs.raf.demo.model.Machine;
import rs.raf.demo.model.Status;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.UserRepository;

import java.util.Date;

@Component
public class MachineMapper {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    public Machine createMachine(MachineCreateDto machineCreateDto) throws Exception{
        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User loggedUser = userRepository.findUserByUsername(email);
        Date date = new Date();
        Machine machine = new Machine();
        machine.setMachineName(machineCreateDto.getMachineName());
        machine.setCreatedBy(loggedUser);
        machine.setCreatedAt(date);
        machine.setStatus(Status.STOPPED);
        machine.setActive(true);
        return machine;
    }

    public MachineDto toMachineDto(Machine machine) throws Exception{
        MachineDto machineDto = new MachineDto();
        machineDto.setMachineName(machine.getMachineName());
        machineDto.setActive(machine.getActive());
        machineDto.setStatus(machine.getStatus());
        machineDto.setCreatedAt(machine.getCreatedAt());
        machineDto.setCreatedBy(userMapper.userToUserDto(machine.getCreatedBy()));
        return machineDto;

    }
}
