import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { User } from 'src/app/model';
import { LoginserviceService } from 'src/app/services/loginservice.service';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  updateForm: FormGroup
 
  constructor(private formBuilder: FormBuilder, private loginService: LoginserviceService, private usersService: UsersService) { 
    this.loginService.checkPermissions('can_update_users');
    this.updateForm = this.formBuilder.group({
      id: ['', Validators.required],
      firstName: [],
      lastName: [],
      username: ['', Validators.email],
      password: [],
      can_read_users: [],
      can_create_users: [],
      can_update_users: [],
      can_delete_users: [],
    });
  }

  ngOnInit(): void {
  }

  update(){
    let permissions = [];

    if (this.updateForm.get('can_read_users')?.value == true)
      permissions.push('can_read_users');
    
    if (this.updateForm.get('can_create_users')?.value == true)
      permissions.push('can_create_users');
    
    if (this.updateForm.get('can_update_users')?.value == true)
      permissions.push('can_update_users');
    
    if (this.updateForm.get('can_delete_users')?.value == true)
      permissions.push('can_delete_users');
    
    let user = {
      firstName: this.updateForm.get('firstName')?.value,
      lastName: this.updateForm.get('lastName')?.value,
      username: this.updateForm.get('username')?.value,
      password: this.updateForm.get('password')?.value,
      permissions,
    };
    console.log("bla");
    this.usersService.updateUser(user, this.updateForm.get('id')?.value);
    this.updateForm.reset();
  }


}
