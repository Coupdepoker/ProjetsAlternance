import { HttpClient, HttpHeaders , HttpEvent, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private API_URL = environment.API_URL;

  optionRequete = {
    headers : new HttpHeaders ({
      'Content-Type' : 'application/json'
    }), responseType: 'text' as 'json'
  }

  public user : Observable<string>;
  public userSubject : BehaviorSubject<string>

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.userSubject =  new BehaviorSubject<string>(localStorage.getItem('UserToken'));
    this.user = this.userSubject.asObservable();
  }

  login(username : string, password : string){
    //user : User = {}
    return this.http.post(`${this.API_URL}/login`,{username: username, password : password}, this.optionRequete)
  }

  register(username : string, password : string, email : string, adresse : string){
    //user : User = {}
    return this.http.post(`${this.API_URL}/register`,{username: username, password : password, email: email, Adresse : adresse})
  }

  logout(){
    localStorage.removeItem('UserToken');
    this.userSubject.next('');
    this.router.navigate(['/']);
  }

  getUsers(){
    return this.http.get(`${this.API_URL}/users`);
  }

  getInfoUser(){
    return this.http.get(`${this.API_URL}/user/id`);
  }

  getInfoUserAdmin(username : string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("username",username);
    return this.http.get(`${this.API_URL}/user/id`,{params: queryParams});
  }

  getMe(){
    return this.http.get(`${this.API_URL}/me`);
  }

  modifyUser(username : string, password : string, email : string, adresse : string){
    return this.http.put(`${this.API_URL}/user/id`,{username: username, password : password, email: email, Adresse : adresse});
  }

  modifyUserForAdmin(username : string, password : string, email : string, Adresse : string, usernameForAdmin:string){
    return this.http.put(`${this.API_URL}/user/id`,{username: username, password : password, email: email, Adresse : Adresse, usernameForAdmin: usernameForAdmin});
  }

  deleteUser(username: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("username",username);
    return this.http.delete(`${this.API_URL}/user/id`,{params: queryParams});
  }
}
