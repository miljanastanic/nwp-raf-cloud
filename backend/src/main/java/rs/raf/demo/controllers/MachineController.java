package rs.raf.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.dto.MachineCreateDto;
import rs.raf.demo.dto.MachineScheduleDto;
import rs.raf.demo.dto.MachineSearchDto;
import rs.raf.demo.dto.UserCreateDto;
import rs.raf.demo.model.Machine;
import rs.raf.demo.model.Status;
import rs.raf.demo.services.MachineService;
import rs.raf.demo.services.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/machine")
public class MachineController {

    private MachineService machineService;
    private UserService userService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchAllMachines(@RequestBody MachineSearchDto machineSearchDto) throws Exception{

        try {
            System.out.println("search");
            return new ResponseEntity<>(machineService.searchMachines(machineSearchDto), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMachines() throws Exception{
        System.out.println("servis");
        return new ResponseEntity<>(machineService.getMachines(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createMachine(@RequestBody MachineCreateDto machineCreateDto) {
        try{
            System.out.println("kontroler");
            return new ResponseEntity<>(machineService.createMachine(machineCreateDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/schedule")
    public ResponseEntity<?> scheduleMachine(@RequestBody MachineScheduleDto machineScheduleDto) throws Exception{
        try {
            System.out.println("1");
            machineService.schedule(machineScheduleDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/start")
    public ResponseEntity<?> start(@RequestBody MachineScheduleDto machineScheduleDto) throws Exception{
        try {
            //userService.checkPrivileges("can_start_machines");
            System.out.println("Start");
            machineService.startMachine(machineScheduleDto.getMachineName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/stop")
    public ResponseEntity<?> stop(@RequestBody MachineScheduleDto machineScheduleDto) throws Exception{
        try {
            userService.checkPrivileges("can_stop_machines");
            System.out.println("Stop");
            machineService.stopMachine(machineScheduleDto.getMachineName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/restart")
    public ResponseEntity<?> restart(@RequestBody MachineScheduleDto machineScheduleDto) throws Exception{
        try {
            userService.checkPrivileges("can_restart_machines");
            System.out.println("Restart");
            machineService.restartMachine(machineScheduleDto.getMachineName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
