import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Machine } from 'src/app/model';
import { LoginserviceService } from 'src/app/services/loginservice.service';
import { MachinesService } from 'src/app/services/machines.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  machines: Machine[] = [
    {
     id: -1,
     machineName: '',
     status: '',
     active: '',
     createdAt: '',
     createdBy: 
      {
        id: -1,
        firstName: '',
        lastName: '',
        username: '',
        permissions: [''],
      }
   
    },
  ];

  searchForm: FormGroup
  constructor(private formBuilder: FormBuilder, private machineService: MachinesService, private loginService: LoginserviceService) { 
    this.loginService.checkPermissions('can_search_machines');
    this.machineService.loadAllMachines().subscribe((data) => {
      this.machines = data;
      console.log(this.machines)
    });
    this.searchForm = this.formBuilder.group({
      machineName: [''],
      createdAfter: [''],
      createdBefore: [''],
      RUNNING: [],
      STOPPED: [],
    });
  }

  ngOnInit(): void {
  }

  searchAllMachines(){

    let status1 = [];

    if (this.searchForm.get('STOPPED')?.value == true)
      status1.push('STOPPED');
    
    if (this.searchForm.get('RUNNING')?.value == true)
      status1.push('RUNNING');

    let machineS = {
      machineName: this.searchForm.get('machineName')?.value,
      status: status1,
      createdAfter: this.searchForm.get('createdAfter')?.value,
      createdBefore: this.searchForm.get('createdBefore')?.value
    }

    this.machineService.searchAllMachines(machineS).subscribe((data) => {
                                          this.machines = data;
                                            console.log(this.machines)
                                          });
  }

}
