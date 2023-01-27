import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/service/auth/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  username : string = '';
  password : string = '';
  email : string = '';
  adresse : string = '';
  usernameUser : string = '';
  passwordUser : string = '';
  emailUser : string = '';
  adresseUser : string = '';
  usernameForAdmin : string = '';
  isAdmin = true;

  constructor(
    private auth : AuthService
  ) { }

  ngOnInit() {
    this.auth.getInfoUser().subscribe(data => {
      const tab = JSON.parse(JSON.stringify(data)).sql1[0];
      this.username = tab.username;
      this.email = tab.email;
      this.adresse = tab.Adresse;
      if(tab.role == 'user'){
        this.isAdmin = false;
      }
    })
  }

  getInfoUserAdmin(){
    this.auth.getInfoUserAdmin(this.usernameForAdmin).subscribe(data => {
      //console.log(data);
      const tab = JSON.parse(JSON.stringify(data)).sql1[0];
      this.usernameUser = tab.username;
      this.emailUser = tab.email;
      this.adresseUser = tab.Adresse;
    })
  }

  modify(){
    this.auth.modifyUser(this.username,this.password,this.email,this.adresse).subscribe(data => {
    });
  }

  modifyAdmin(){
    this.auth.modifyUserForAdmin(this.usernameUser,this.passwordUser,this.emailUser,this.adresseUser,this.usernameForAdmin).subscribe(data => {

    });
  }

  delete(){
    this.auth.deleteUser(this.username).subscribe(data => {
      this.auth.logout();
    })
  }

  deleteAdmin(){
    this.auth.deleteUser(this.usernameForAdmin).subscribe(data => {
    })
  }

}
