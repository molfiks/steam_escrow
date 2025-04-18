import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from './pages/main/main.component';
import {ProductListComponent} from './pages/product-list/product-list.component';
import {MyProductsComponent} from './pages/my-products/my-products.component';
import {ManageProductComponent} from './pages/manage-product/manage-product.component';
import {PurchasedProductsListComponent} from './pages/purchased-products-list/purchased-products-list.component';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children:[
      {
        path: '',
        component: ProductListComponent
      },
      {
        path: 'my-products',
        component: MyProductsComponent
      },
      {
        path: 'manage',
        component: ManageProductComponent
      },
      {
        path: 'manage/:productId',
        component: ManageProductComponent
      },
      {
        path: 'my-purchased-products',
        component: PurchasedProductsListComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule { }
