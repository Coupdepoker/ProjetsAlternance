import { Component, OnInit , Input} from '@angular/core';
import { User } from 'src/app/model/User';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  @Input() item : User = {Id : 0, username: '', password : '', role: '', email: '', Adresse :'' }
  constructor() { }

  ngOnInit() {
  }

}
