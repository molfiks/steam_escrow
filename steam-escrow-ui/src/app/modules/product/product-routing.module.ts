import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from './pages/main/main.component';
import {ProductListComponent} from './pages/product-list/product-list.component';
import {MyProductsComponent} from './pages/my-products/my-products.component';
import {ManageProductComponent} from './pages/manage-product/manage-product.component';
import {PurchasedProductsListComponent} from './pages/purchased-products-list/purchased-products-list.component';
import {MySoldProductsComponent} from './pages/my-sold-products/my-sold-products.component';
import {authGuard} from '../../services/guard/auth.guard';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    canActivate: [authGuard],
    children:[
      {
        path: '',
        component: ProductListComponent,
        canActivate: [authGuard]
      },
      {
        path: 'my-products',
        component: MyProductsComponent,
        canActivate: [authGuard]
      },
      {
        path: 'manage',
        component: ManageProductComponent,
        canActivate: [authGuard]
      },
      {
        path: 'manage/:productId',
        component: ManageProductComponent,
        canActivate: [authGuard]
      },
      {
        path: 'my-purchased-products',
        component: PurchasedProductsListComponent,
        canActivate: [authGuard]
      },
      {
        path: 'my-sold-products',
        component: MySoldProductsComponent,
        canActivate: [authGuard]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule { }
