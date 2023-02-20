package rs.raf.demo.mapper;

import org.springframework.stereotype.Component;
import rs.raf.demo.dto.ErrorDto;
import rs.raf.demo.dto.MachineDto;
import rs.raf.demo.model.ErrorMessage;
import rs.raf.demo.model.Machine;
import rs.raf.demo.model.Operation;

import java.util.Date;

@Component
public class ErrorMapper {

    public ErrorDto toErrorDto(ErrorMessage errorMessage) throws Exception {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMachineId(errorMessage.getMachineId());
        errorDto.setDate(errorMessage.getDate());
        errorDto.setOperation(errorMessage.getOperation());
        errorDto.setMessage(errorMessage.getMessage());
        return errorDto;
    }

    public void mapError(Date date, Operation operation, Machine machine){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setDate(date);
        errorMessage.setMachineId(machine.getId());
        errorMessage.setOperation(operation);
        errorMessage.setMessage("Operation " + operation + " has failed at " + date + " with id " + machine.getId());
    }
}
