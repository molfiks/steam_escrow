import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductRoutingModule } from './product-routing.module';
import { MainComponent } from './pages/main/main.component';
import { MenuComponent } from './components/menu/menu.component';
import { ProductListComponent } from './pages/product-list/product-list.component';
import { ProductCardComponent } from './components/product-card/product-card.component';
import { RatingComponent } from './components/rating/rating.component';
import { MyProductsComponent } from './pages/my-products/my-products.component';
import { ManageProductComponent } from './pages/manage-product/manage-product.component';
import {FormsModule} from '@angular/forms';


@NgModule({
  declarations: [
    MainComponent,
    MenuComponent,
    ProductListComponent,
    ProductCardComponent,
    RatingComponent,
    MyProductsComponent,
    ManageProductComponent
  ],
  imports: [
    CommonModule,
    ProductRoutingModule,
    FormsModule
  ]
})
export class ProductModule { }
