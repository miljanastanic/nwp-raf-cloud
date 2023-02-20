import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginserviceService } from 'src/app/services/loginservice.service';
import { MachinesService } from 'src/app/services/machines.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  createForm: FormGroup
  scheduleForm: FormGroup
  constructor(private formBuilder: FormBuilder, private machinesService: MachinesService, private loginService: LoginserviceService) { 
    this.loginService.checkPermissions('can_create_machines');
    this.createForm = this.formBuilder.group({
      machineName: ['', Validators.required],
    });
    this.scheduleForm = this.formBuilder.group({
      machineName: ['', Validators.required],
      date: ['', Validators.required],
      operation: [],
    });
  }

  ngOnInit(): void {
  }

  createMachine(){
    let machineC = {
      machineName: this.createForm.get('machineName')?.value
    }
    this.machinesService.createMachine(machineC);
  }

  schedule(){
    let machineSchedule = {
      machineName: this.scheduleForm.get('machineName')?.value,
      date : this.scheduleForm.get('date')?.value,
      operation : this.scheduleForm.get('operation')?.value
    }
    this.machinesService.scheduleMachine(machineSchedule)
  }

  start(){
    this.loginService.checkPermissions('can_start_machines');
    let machineSchedule = {
      machineName: this.scheduleForm.get('machineName')?.value,
      date : this.scheduleForm.get('date')?.value,
      operation : this.scheduleForm.get('operation')?.value
    }
    this.machinesService.startMachine(machineSchedule)
  }

  stop(){
    let machineSchedule = {
      machineName: this.scheduleForm.get('machineName')?.value,
      date : this.scheduleForm.get('date')?.value,
      operation : this.scheduleForm.get('operation')?.value
    }
    this.machinesService.stopMachine(machineSchedule)
  }

  restart(){
    let machineSchedule = {
      machineName: this.scheduleForm.get('machineName')?.value,
      date : this.scheduleForm.get('date')?.value,
      operation : this.scheduleForm.get('operation')?.value
    }
    this.machinesService.restartMachine(machineSchedule)
  }

}
