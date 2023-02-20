package rs.raf.demo.services;

import org.springframework.stereotype.Service;
import rs.raf.demo.dto.ErrorDto;
import rs.raf.demo.dto.MachineDto;
import rs.raf.demo.model.Machine;
import rs.raf.demo.model.Operation;

import java.util.Date;
import java.util.List;

@Service
public interface ErrorService {

    List<ErrorDto> getErrors() throws Exception;
}
