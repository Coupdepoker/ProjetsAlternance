import { HttpClient , HttpHeaders, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private API_URL = environment.API_URL;

  optionRequete = {
    headers : new HttpHeaders ({
      'Content-Type' : 'application/json'
    }), responseType: 'text' as 'json'
  }

  constructor(private http : HttpClient) {

   }

   getProducts(){
    return this.http.get(`${this.API_URL}/products`);
   }

   createProduct(name : string, price : number, image:string){

    return this.http.post(`${this.API_URL}/product/create`,{name: name,price : price,image :image});

   }

   getProductsOfUser(username : string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("username",username);
    return this.http.get(`${this.API_URL}/products/id`,{params:queryParams});
   }

   deleteProduct(name: string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("name",name);
    return this.http.delete(`${this.API_URL}/product`,{params: queryParams});
   }

   modifyProduct(nameStart : string, name: string, price : number, image :string){
    return this.http.put(`${this.API_URL}/product`,{nameStart: nameStart, name:name, price: price, image : image});
   }

}
