import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/service/product/product-service.service';
import { saveAs } from 'file-saver'


@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent implements OnInit {
  name: string = '';
  price: number = 0;
  image: string='';

  constructor(private ps : ProductService) { }

  ngOnInit() {
  }

  async create(){
    this.ps.createProduct(this.name,this.price,this.image).subscribe(data => {
      console.log(data);
    }
    )
  }

}
