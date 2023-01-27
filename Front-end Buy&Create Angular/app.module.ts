import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClient, HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './container/login/login.component';
import { FormsModule } from '@angular/forms';
import { NavBarComponent } from './container/nav-bar/nav-bar.component';
import { RegisterComponent } from './container/register/register.component';
import { ProductComponent } from './container/product/product.component';
import { ProductContainerComponent } from './container/product-container/product-container.component';
import { JwtInterceptor } from './common/jwt.interceptor';
import { CreateProductComponent } from './container/create-product/create-product.component';
import { AllUsersComponent } from './container/all-users/all-users.component';
import { UserComponent } from './container/user/user.component';
import { ProfileComponent } from './container/profile/profile.component';
import { MyproductComponent } from './container/myproduct/myproduct.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavBarComponent,
    RegisterComponent,
    ProductComponent,
    ProductContainerComponent,
    CreateProductComponent,
    AllUsersComponent,
    UserComponent,
    ProfileComponent,
    MyproductComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    HttpClient,
    { provide: HTTP_INTERCEPTORS, useClass : JwtInterceptor, multi : true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
