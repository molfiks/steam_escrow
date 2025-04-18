import { Component } from '@angular/core';
import {BoughtProductResponse} from '../../../../services/models/bought-product-response';
import {PageResponseBoughtProductResponse} from '../../../../services/models/page-response-bought-product-response';

@Component({
  selector: 'app-purchased-products-list',
  standalone: false,
  templateUrl: './purchased-products-list.component.html',
  styleUrl: './purchased-products-list.component.scss'
})
export class PurchasedProductsListComponent {
  purchasedProducts: PageResponseBoughtProductResponse = {};

}
