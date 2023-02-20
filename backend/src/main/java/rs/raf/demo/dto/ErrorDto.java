package rs.raf.demo.dto;

import rs.raf.demo.model.Operation;

import javax.persistence.Column;
import java.util.Date;

public class ErrorDto {

    private Long id;
    private Date date;
    private Long machineId;
    private Operation operation;
    private String message;

    public ErrorDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
