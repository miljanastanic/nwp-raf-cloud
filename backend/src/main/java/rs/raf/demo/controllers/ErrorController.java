package rs.raf.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.demo.services.ErrorService;

@RestController
@CrossOrigin
@RequestMapping(value = "/history")
public class ErrorController {


    private ErrorService errorService;

    public ErrorController(ErrorService errorService) {
        this.errorService = errorService;
    }

    @GetMapping
    public ResponseEntity<?> getHistory() throws Exception{
        return new ResponseEntity<>(errorService.getErrors(), HttpStatus.OK);
    }
}
