import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from './pages/main/main.component';
import {ProductListComponent} from './pages/product-list/product-list.component';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children:[
      {
        path: '',
        component: ProductListComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule { }
