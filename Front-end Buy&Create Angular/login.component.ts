import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username : string = '';
  password : string = '';
  refresh : number = 0;

  constructor(
    private _authService : AuthService,
    private router: Router
  ) { }

  ngOnInit() {

  }

  login () {
    this._authService.login(this.username, this.password)
    .subscribe(data => {

      localStorage.setItem('UserToken',data.toString());
      this._authService.userSubject.next(data.toString())
      this.router.navigateByUrl('/products');
    }
      )
  }

}
