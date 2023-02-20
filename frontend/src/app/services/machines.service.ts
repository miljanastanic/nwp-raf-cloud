import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Machine, MachineC } from '../model';
import { LoginserviceService } from './loginservice.service';

@Injectable({
  providedIn: 'root'
})
export class MachinesService {

  private readonly apiUrl = environment.postApi

  headers: any = {
    Authorization: `Bearer ${this.loginService.getToken()}`,
    'Content-Type': 'application/json',
    Accept: 'application/json',
  };

  constructor(private httpClient: HttpClient, private loginService: LoginserviceService) { }

  createMachine(machineC: Object) {
    console.log("masine")
    let headers = new HttpHeaders()
    headers = this.headers;
    let params = machineC;
    this.httpClient
      .post<MachineC>(`${this.apiUrl}/machine`, params, { headers })
      .subscribe((data) => console.log(data));
    
  }

  loadAllMachines(): Observable<Machine[]> {
    let headers = new HttpHeaders()
    headers = this.headers;
    return this.httpClient.get<Machine[]>(`${this.apiUrl}/machine/all`, {
      headers,
    });
  }

  searchAllMachines(machineS: Object): Observable<Machine[]>{
    
    let params = machineS
    let headers = new HttpHeaders()
    headers = this.headers;
    console.log(`${this.apiUrl}/machine/search`, {headers: headers, params: params})
    return this.httpClient.post<Machine[]>(`${this.apiUrl}/machine/search`, params, {
      headers: headers
    });
    
  }

  scheduleMachine(machineSchedule: Object) {
    console.log(machineSchedule)
    let params = machineSchedule;
    let headers = new HttpHeaders()
    headers = this.headers;
    this.httpClient
      .post<Machine>(`${this.apiUrl}/machine/schedule`, params, { headers })
      .subscribe((data) => console.log(data));
  }

  startMachine(machineSchedule: Object) {
    console.log(machineSchedule)
    let params = machineSchedule;
    let headers = new HttpHeaders()
    headers = this.headers;
    this.httpClient
      .post<Machine>(`${this.apiUrl}/machine/start`, params, { headers })
      .subscribe((data) => console.log(data));
  }

  stopMachine(machineSchedule: Object) {
    console.log(machineSchedule)
    let params = machineSchedule;
    let headers = new HttpHeaders()
    headers = this.headers;
    this.httpClient
      .post<Machine>(`${this.apiUrl}/machine/stop`, params, { headers })
      .subscribe((data) => console.log(data));
  }

  restartMachine(machineSchedule: Object) {
    console.log(machineSchedule)
    let params = machineSchedule;
    let headers = new HttpHeaders()
    headers = this.headers;
    this.httpClient
      .post<Machine>(`${this.apiUrl}/machine/restart`, params, { headers })
      .subscribe((data) => console.log(data));
  }
}
