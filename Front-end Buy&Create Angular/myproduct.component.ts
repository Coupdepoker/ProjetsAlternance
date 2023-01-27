import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'src/app/service/product/product-service.service';
//const url = require('url');

@Component({
  selector: 'app-myproduct',
  templateUrl: './myproduct.component.html',
  styleUrls: ['./myproduct.component.css']
})
export class MyproductComponent implements OnInit {

  name: string = '';
  price: number = 0;
  Id : number = 0;
  nameStart : string = '';
  image: string = '';

  constructor(private router : Router, private route :  ActivatedRoute,
    private productService : ProductService) { }

  async ngOnInit() {
    //const queryObject = url.parse(this.router.url, true).query;
    //console.log(this.router.url);
    const product = localStorage.getItem('product');
    //this.Id = JSON.parse()
    this.name = JSON.parse(product).name;
    this.price =  JSON.parse(product).price;
    this.nameStart = JSON.parse(product).name;
    this.image = JSON.parse(product).image;

  }

  delete(){
    this.productService.deleteProduct(this.name).subscribe(data => {
      this.router.navigate(['products']);
    })
  }

  modify(){

    this.productService.modifyProduct(this.nameStart,this.name,this.price, this.image).subscribe(data => {
      this.router.navigate(['products']);
    })
  }

}
