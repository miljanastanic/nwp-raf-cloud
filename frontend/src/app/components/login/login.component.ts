import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginserviceService } from 'src/app/services/loginservice.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  
  constructor(private loginService: LoginserviceService, private fromBuilder: FormBuilder) { 
    this.loginForm = this.fromBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
  }
  
  ngOnInit(): void {
      
  }

  login() {
    this.loginService.login(
      this.loginForm.get('username')?.value,
      this.loginForm.get('password')?.value
    );
  }

}
