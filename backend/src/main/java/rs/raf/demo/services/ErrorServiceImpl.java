package rs.raf.demo.services;

import org.springframework.stereotype.Service;
import rs.raf.demo.dto.ErrorDto;
import rs.raf.demo.mapper.ErrorMapper;
import rs.raf.demo.model.ErrorMessage;
import rs.raf.demo.model.Machine;
import rs.raf.demo.model.Operation;
import rs.raf.demo.repositories.ErrorRepository;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ErrorServiceImpl implements ErrorService{

    private ErrorRepository errorRepository;
    private ErrorMapper errorMapper;

    public ErrorServiceImpl(ErrorRepository errorRepository, ErrorMapper errorMapper) {
        this.errorRepository = errorRepository;
        this.errorMapper = errorMapper;
    }

    @Override
    public List<ErrorDto> getErrors() throws Exception {
        List<ErrorDto> errors = new ArrayList<>();
        for(ErrorMessage errorMessage: errorRepository.findAll()){
            errors.add(errorMapper.toErrorDto(errorMessage));
        }
        return errors;
    }

}
