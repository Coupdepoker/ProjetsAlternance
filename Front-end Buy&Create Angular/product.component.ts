import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Product } from 'src/app/model/Product';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  @Input() item : Product = {Id : 0, name: '', price : 0, UserId : 0, image: '' };


  constructor(private router: Router, private httpClient : HttpClient, private route :ActivatedRoute) { }

  ngOnInit() {
  }

/*
onURLinserted() {
      this.getImage(this.myURL).subscribe(data => {
        this.createImageFromBlob(data);
      }, error => {
        console.log("Error occured",error);
      });
}

getImage(imageUrl: string): Observable<File> {
  let headers={ responseType: Blob }
  return this.httpClient.get(imageUrl).forEach((res: Response) => res.blob()));
      //.get(imageUrl, headers)
      //.map(;
}

createImageFromBlob(image: Blob) {
   let reader = new FileReader(); //you need file reader for read blob data to base64 image data.
   reader.addEventListener("load", () => {
      this.imageToShow = reader.result; // here is the result you got from reader
   }, false);

   if (image) {
      reader.readAsDataURL(image);
   }
}
*/

  myproduct(){
    this.router.navigateByUrl('product/',{ queryParams: {nameStart: this.item.name}});
    localStorage.setItem('product', JSON.stringify(this.item));
    //et params = new HttpParams().set('id',this.item.Id.toString());
    //return this.httpClient.get('product',{params});
    /*
    this.router.navigateByUrl(
      '',
     {relativeTo : this.route,
      queryParams : {id : 'w'},
     queryParamsHandling : 'merge',
      skipLocationChange : true

    }
     );*/
    //params = params.append('id',this.item.Id.toString());
  }

}
