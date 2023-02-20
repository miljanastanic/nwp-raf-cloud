import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model';
import { LoginserviceService } from 'src/app/services/loginservice.service';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users: User[] = [
    {
      id: -1,
      firstName: '',
      lastName: '',
      username: '',
      permissions: [''],
    },
  ];

  constructor(
    private loginService: LoginserviceService,
    private userService: UsersService
  ) {
    this.loginService.checkPermissions('can_read_users');
    this.userService.loadAllUsers().subscribe((data) => {
      this.users = data;
    });
  }

  ngOnInit(): void {
  }

  deleteRow(userIds: any){
    console.log(userIds)
    this.userService.deleteUser(String(userIds))
  }

}
