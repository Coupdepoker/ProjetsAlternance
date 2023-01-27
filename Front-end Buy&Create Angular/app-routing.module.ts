import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './common/auth.guard';
import { AllUsersComponent } from './container/all-users/all-users.component';
import { CreateProductComponent } from './container/create-product/create-product.component';
import { LoginComponent } from './container/login/login.component';
import { MyproductComponent } from './container/myproduct/myproduct.component';
import { ProductContainerComponent } from './container/product-container/product-container.component';
import { ProductComponent } from './container/product/product.component';
import { ProfileComponent } from './container/profile/profile.component';
import { RegisterComponent } from './container/register/register.component';


const routes: Routes = [
  {path : 'login', component : LoginComponent},
  {path : 'register', component : RegisterComponent},
  {path : 'products', component : ProductContainerComponent},
  {path : 'product/create', component : CreateProductComponent, canActivate: [AuthGuard] },
  {path : 'users', component : AllUsersComponent},
  {path : 'profile', component : ProfileComponent, canActivate: [AuthGuard]},
  {path : 'product/', component : MyproductComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
