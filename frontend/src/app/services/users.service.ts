import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../model';
import { LoginserviceService } from './loginservice.service';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private readonly apiUrl = environment.postApi

  headers: any = {
    Authorization: `Bearer ${this.loginService.getToken()}`,
    'Content-Type': 'application/json',
    Accept: 'application/json',
  };

  constructor(private httpClient: HttpClient, private loginService: LoginserviceService) { }

  loadAllUsers(): Observable<User[]> {
    let headers = new HttpHeaders()
    headers = this.headers;
    return this.httpClient.get<User[]>(`${this.apiUrl}/users`, {
      headers,
    });
  }

  createUser(user: Object) {
    console.log("bla")
    let params = user;
    let headers = new HttpHeaders()
    headers = this.headers;
    this.httpClient
      .post<User>(`${this.apiUrl}/users`, params, { headers })
      .subscribe((data) => console.log(data));
    
  }

  deleteUser(id: string) {
    console.log(id)
    let headers = new HttpHeaders()
    headers = this.headers;
    this.httpClient
      .delete<User>(`${this.apiUrl}/users/${id}`, { headers })
      .subscribe((data) => console.log(data));
  }

  updateUser(user: Object, id: string) {
    let params = user;
    let headers = new HttpHeaders()
    headers = this.headers;
    this.httpClient
      .put<User>(`${this.apiUrl}/users/${id}/update`, params, { headers })
      .subscribe((data) => console.log(data));
  }

}
