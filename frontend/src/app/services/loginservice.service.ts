import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { Token, User } from '../model';

@Injectable({
  providedIn: 'root'
})
export class LoginserviceService {

  private readonly apiUrl = environment.postApi
  jwt: string = '';
  flag = false
  currUser: User = {
    id: -1,
    firstName: '',
    lastName: '',
    username: '',
    permissions: [''],
  };

  constructor(private router: Router, private httpClient: HttpClient) { 
    if (localStorage.getItem('jwt') != null) {
      var newToken = localStorage.getItem('jwt');
      if (newToken) this.jwt = newToken;
      this.flag = true;
      var user = localStorage.getItem('currUser');
      if (user) this.currUser = JSON.parse(user);
    }
  }

  login(username: string, password: string) {
    let params = {
      username,
      password,
    };
    this.httpClient.post<Token>(`${this.apiUrl}/auth/login`, params).subscribe(
      (data) => {
        console.log(data.jwt)
        console.log(data.user)
        localStorage.setItem('jwt', data.jwt);
        localStorage.setItem('currUser', JSON.stringify(data.user));
        this.flag = true
        this.jwt = data.jwt;
        this.currUser = data.user
      },
      (error) => {
        console.log(error);
      }
    );
  }


  checkPermissions(permission: string) {
    if (!this.flag) {
      alert('error');
      this.router.navigate(['/']);
    } else if (!this.hasPermission(permission)) {
      alert('error');
      this.router.navigate(['/']);
    } else if (localStorage.getItem('jwt') != null) {
      let parts = this.jwt.split('.');
      let claims = JSON.parse(atob(parts[1]));
      if (new Date() < claims.exp) {
        alert('error');
      }
    }
  }

  hasPermission(permission: string) {
    return this.currUser.permissions.indexOf(permission) > -1;
  }

  getToken() {
    return this.jwt;
  }
}
