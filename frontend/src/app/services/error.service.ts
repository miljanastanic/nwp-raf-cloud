import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ErrorMessage } from '../model';
import { LoginserviceService } from './loginservice.service';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  private readonly apiUrl = environment.postApi
  
  headers: any = {
    Authorization: `Bearer ${this.loginService.getToken()}`,
    'Content-Type': 'application/json',
    Accept: 'application/json',
  };
  
  constructor(private httpClient: HttpClient, private loginService: LoginserviceService) { }

  getHistory(): Observable<ErrorMessage[]>{
    let headers = new HttpHeaders()
    headers = this.headers;
    console.log(`${this.apiUrl}/users`)
    return this.httpClient.get<ErrorMessage[]>(`${this.apiUrl}/history`, {
      headers,
    });
  }
}
