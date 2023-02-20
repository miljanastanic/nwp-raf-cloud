import { Component, OnInit } from '@angular/core';
import { ErrorMessage } from 'src/app/model';
import { ErrorService } from 'src/app/services/error.service';
import { LoginserviceService } from 'src/app/services/loginservice.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  errors: ErrorMessage[] = [
    {
      id: -1,
      date: '',
      machineId: -1,
      operation: '',
      message: '',

    },
  ];

  constructor(private loginService: LoginserviceService, private errorService: ErrorService) { 
    this.errorService.getHistory().subscribe((data) => {
      this.errors = data;
    });
  }

  ngOnInit(): void {
  }

}
