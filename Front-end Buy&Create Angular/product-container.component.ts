import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/service/product/product-service.service';
import { Product } from 'src/app/model/Product';
import { AuthService } from 'src/app/service/auth/auth.service';

@Component({
  selector: 'app-product-container',
  templateUrl: './product-container.component.html',
  styleUrls: ['./product-container.component.css']
})
export class ProductContainerComponent implements OnInit {
  products : Product[] = [];
  username : string = '';
  isLog : boolean = false;
  constructor(
    private _productService : ProductService,
    private _authService : AuthService,
  ) { }

  ngOnInit() {
    this._authService.user.subscribe( data => {
      //console.log(`isLog : ${data}`);
      this.isLog = data ? true :false;
    })
  }

  getAllProducts(){
    this._productService.getProducts().subscribe(data => {
      //this.products = [{Id : 0, name: 'eau', price : 0, UserId : 0 }, {Id : 0, name: 'feu', price : 0, UserId : 0 }];
      //console.log(data);
      const array = JSON.parse(JSON.stringify(data)).sql1;
      //console.log(array);
      this.products = array;
    })
  }

  getProductsOfUser(){
    this._productService.getProductsOfUser(this.username).subscribe(data => {
      //console.log(data);
      const array = JSON.parse(JSON.stringify(data)).sql1;
      //console.log(array);
      this.products = array;
    })
  }

  getProductsOfMe(){
    this._authService.getMe().subscribe(data => {
      const name = JSON.parse(JSON.stringify(data)).content.username;
      //console.log(data);
      //console.log(name);
      this._productService.getProductsOfUser(name).subscribe(data => {
        //console.log(data);
        const array = JSON.parse(JSON.stringify(data)).sql1;
        //console.log(array);
        this.products = array;
      })
    })

  }

}
