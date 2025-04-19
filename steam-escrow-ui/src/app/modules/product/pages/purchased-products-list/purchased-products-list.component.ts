import {Component, OnInit} from '@angular/core';
import {BoughtProductResponse} from '../../../../services/models/bought-product-response';
import {PageResponseBoughtProductResponse} from '../../../../services/models/page-response-bought-product-response';
import {ProductService} from '../../../../services/services/product.service';

@Component({
  selector: 'app-purchased-products-list',
  standalone: false,
  templateUrl: './purchased-products-list.component.html',
  styleUrl: './purchased-products-list.component.scss'
})
export class PurchasedProductsListComponent implements OnInit{
  purchasedProducts: PageResponseBoughtProductResponse = {};

  page=0;
  size = 5;
  selectedProduct: BoughtProductResponse = {};

  constructor(
    private productService: ProductService
  ) {
  }

  ngOnInit(): void {
      this.findAllPurchasedProducts();
  }


  returnPurchasedProduct(product: BoughtProductResponse) {
    this.selectedProduct = product;
  }

  private findAllPurchasedProducts() {
    this.productService.findAllBoughtProducts({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (response) => {
        this.purchasedProducts = response;
      }
    })
  }

  goToFirstPage() {
    this.page=0;
    this.findAllPurchasedProducts();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllPurchasedProducts();
  }

  goToPage(index: number) {
    this.page=index;
    this.findAllPurchasedProducts();
  }

  goToNextPage() {
    this.page++;
    this.findAllPurchasedProducts();
  }

  goToLastPage() {
    this.page=this.purchasedProducts.totalPages as number - 1;
    this.findAllPurchasedProducts();
  }

  get isLastPage(): boolean {
    return this.page == this.purchasedProducts.totalPages as number -1;
  }
}
