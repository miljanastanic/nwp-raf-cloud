package rs.raf.demo.services;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.demo.dto.*;
import rs.raf.demo.mapper.ErrorMapper;
import rs.raf.demo.mapper.MachineMapper;
import rs.raf.demo.model.Machine;
import rs.raf.demo.model.Operation;
import rs.raf.demo.model.Status;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.MachineRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MachineServiceImpl implements MachineService{

    private MachineMapper machineMapper;
    private ErrorMapper errorMapper;
    private MachineRepository machineRepository;
    private UserServiceImpl userService;
    private TaskScheduler taskScheduler;

    public MachineServiceImpl(MachineMapper machineMapper, ErrorMapper errorMapper, MachineRepository machineRepository, UserServiceImpl userService, TaskScheduler taskScheduler) {
        this.machineMapper = machineMapper;
        this.errorMapper = errorMapper;
        this.machineRepository = machineRepository;
        this.userService = userService;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public List<MachineDto> getMachines() throws Exception{
        userService.checkPrivileges("can_search_machines");
        List<MachineDto> machines = new ArrayList<>();
        for (Machine machine: machineRepository.findAll()){
            machines.add(machineMapper.toMachineDto(machine));
        }
        return machines;
    }

    @Override
    public MachineDto getMachine(Long userId) {
        return null;
    }

    @Override
    public MachineDto createMachine(MachineCreateDto machineCreateDto) throws Exception {
        userService.checkPrivileges("can_create_machines");
        Machine machine = machineMapper.createMachine(machineCreateDto);
        try{
            machineRepository.save(machine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return machineMapper.toMachineDto(machine);
    }

    @Override
    public List<MachineDto> searchMachines(MachineSearchDto machineSearchDto) throws Exception {
        userService.checkPrivileges("can_search_machines");

        String machineName = machineSearchDto.getMachineName();

        Status status = null;
        List<Machine> statusi = new ArrayList<>();


        if(machineSearchDto.getStatus().size() == 0)
        {
            statusi.addAll(machineRepository.findAll());
        }
        else if (machineSearchDto.getStatus().size() == 1)
        {

            for(String s: machineSearchDto.getStatus()) {
                if (s.equals("RUNNING")) {
                    status = Status.RUNNING;
                } else {
                    status = Status.STOPPED;
                }
            }
            statusi = machineRepository.findMachinesByStatus(status);
        }
        else{
            statusi.addAll(machineRepository.findAll());
        }


        List<Machine> machines = new ArrayList<>();
        List<Machine> dates = new ArrayList<>();


        if(!machineName.equals("")){
            machines =  machineRepository.findMachinesByMachineNameLike(machineName);
        }else{
            machines.addAll(machineRepository.findAll());
        }

        if(!machineSearchDto.getCreatedAfter().equals("") && !machineSearchDto.getCreatedBefore().equals("")){
            Date createdAfter =new SimpleDateFormat("yyyy-MM-dd").parse(machineSearchDto.getCreatedAfter());
            Date createdBefore =new SimpleDateFormat("yyyy-MM-dd").parse(machineSearchDto.getCreatedBefore());

            dates = machineRepository.findMachinesByCreatedAtBetween(createdAfter, createdBefore);
        }else if(!machineSearchDto.getCreatedAfter().equals("")){
            Date createdAfter =new SimpleDateFormat("yyyy-MM-dd").parse(machineSearchDto.getCreatedAfter());

            dates = machineRepository.findMachinesByCreatedAtAfter(createdAfter);

        }else if(!machineSearchDto.getCreatedBefore().equals("")){
            Date createdBefore =new SimpleDateFormat("yyyy-MM-dd").parse(machineSearchDto.getCreatedBefore());

            dates = machineRepository.findMachinesByCreatedAtBefore(createdBefore);
        }else{
            dates.addAll(machineRepository.findAll());
        }

        machines.retainAll(dates);
        machines.retainAll(statusi);

        List<MachineDto> machinesDto = new ArrayList<>();
        for(Machine m: machines){
            machinesDto.add(machineMapper.toMachineDto(m));
        }

        return machinesDto;
    }

    @Override
    public void destroyMachine(Machine machine) throws Exception {
        if(machine.getStatus().equals(Status.STOPPED)) {
            userService.checkPrivileges("can_destroy_machines");
            machine.setActive(false);
            machineRepository.save(machine);
        }
    }

    @Override
    @Async
    @Transactional
    public void startMachine(String machineName) throws Exception {
        Machine machine = machineRepository.findMachineByMachineName(machineName);
        if(machine.getStatus().equals(Status.STOPPED)) {
            long plus = (long) ((Math.random() * (8000 - 5000)) + 5000);
            try {
                Thread.sleep(10000 + plus);
            } catch (Exception e) {
                e.printStackTrace();
            }
            machine.setStatus(Status.RUNNING);
            machineRepository.save(machine);
        }
    }

    @Override
    @Async
    @Transactional
    public void stopMachine(String machineName) throws Exception {
        Machine machine = machineRepository.findMachineByMachineName(machineName);
        if(machine.getStatus().equals(Status.RUNNING)) {
            long plus = (long) ((Math.random() * (8000 - 5000)) + 5000);
            try {
                Thread.sleep(10000 + plus);
            } catch (Exception e) {
                e.printStackTrace();
            }
            machine.setStatus(Status.STOPPED);
            machineRepository.save(machine);
        }
    }

    @Override
    @Async
    @Transactional
    public void restartMachine(String machineName) throws Exception {
        Machine machine = machineRepository.findMachineByMachineName(machineName);
        if(machine.getStatus().equals(Status.RUNNING)){

            long threadTime = (long) ((Math.random() * (8000 - 5000)) + 5000) + 10000;

            try {
                Thread.sleep(threadTime/2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            machine.setStatus(Status.STOPPED);

            try {
                Thread.sleep(threadTime/2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            machine.setStatus(Status.RUNNING);
            machineRepository.save(machine);
        }
    }


    @Override
    @Async
    public void schedule(MachineScheduleDto machineScheduleDto) throws Exception {

        Date scheduleAt = new SimpleDateFormat("yyyy-MM-dd").parse(machineScheduleDto.getDate());
        Operation operation = Operation.DESTROY;
        if(machineScheduleDto.getOperation().equals("STOP")){
            operation = Operation.STOP;
        }
        else if(machineScheduleDto.getOperation().equals("START")){
            operation = Operation.START;
        }
        else if(machineScheduleDto.getOperation().equals("RESTART")){
            operation = Operation.RESTART;
        }

        Machine machine = machineRepository.findMachineByMachineName(machineScheduleDto.getMachineName());

        Operation finalOperation = operation;
        taskScheduler.schedule(() -> {
            try {
                if (finalOperation.equals(Operation.START)) {
                    this.startMachine(machineScheduleDto.getMachineName());
                }
                else if (finalOperation.equals(Operation.STOP)) {
                    this.stopMachine(machineScheduleDto.getMachineName());
                }
                else if (finalOperation.equals(Operation.RESTART)) {
                    this.restartMachine(machineScheduleDto.getMachineName());
                }
            }
            catch (Exception e) {
                errorMapper.mapError(scheduleAt, finalOperation, machine);
                e.printStackTrace();
            }
        }, scheduleAt);
    }

}



