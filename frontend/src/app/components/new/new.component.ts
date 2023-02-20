import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginserviceService } from 'src/app/services/loginservice.service';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-new',
  templateUrl: './new.component.html',
  styleUrls: ['./new.component.css']
})
export class NewComponent implements OnInit {

  newForm: FormGroup
  constructor(private formBuilder: FormBuilder, private loginService: LoginserviceService, private usersService: UsersService) { 
    this.loginService.checkPermissions('can_create_users');
    this.newForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      can_read_users: [],
      can_create_users: [],
      can_update_users: [],
      can_delete_users: [],
    });
  }

  ngOnInit(): void {
  }

  createUser(){
    let permissions = [];

    if (this.newForm.get('can_read_users')?.value == true)
      permissions.push('can_read_users');
    
    if (this.newForm.get('can_create_users')?.value == true)
      permissions.push('can_create_users');
    
    if (this.newForm.get('can_update_users')?.value == true)
      permissions.push('can_update_users');
    
    if (this.newForm.get('can_delete_users')?.value == true)
      permissions.push('can_delete_users');
    
      let user = {
      firstName: this.newForm.get('firstName')?.value,
      lastName: this.newForm.get('lastName')?.value,
      username: this.newForm.get('username')?.value,
      password: this.newForm.get('password')?.value,
      permissions,
    };
    console.log("bla");
    this.usersService.createUser(user);
    this.newForm.reset();
  }

}
