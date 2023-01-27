import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/User';
import { AuthService } from 'src/app/service/auth/auth.service';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {

  users : User[] = []
  constructor(
    private _auth: AuthService
  ) { }

  ngOnInit() {
    this._auth.getUsers().subscribe(data => {
      //this.products = [{Id : 0, name: 'eau', price : 0, UserId : 0 }, {Id : 0, name: 'feu', price : 0, UserId : 0 }];
      //console.log(data);
      const array = JSON.parse(JSON.stringify(data)).sql1;
      console.log(array);
      this.users = array;
    })
  }

}
