import {Component, OnInit} from '@angular/core';
import {PageResponseBoughtProductResponse} from '../../../../services/models/page-response-bought-product-response';
import {FeedbackRequest} from '../../../../services/models/feedback-request';
import {BoughtProductResponse} from '../../../../services/models/bought-product-response';
import {ProductService} from '../../../../services/services/product.service';
import {FeedbackService} from '../../../../services/services/feedback.service';

@Component({
  selector: 'app-my-sold-products',
  standalone: false,
  templateUrl: './my-sold-products.component.html',
  styleUrl: './my-sold-products.component.scss'
})
export class MySoldProductsComponent implements OnInit{
  soldProducts: PageResponseBoughtProductResponse = {};

  page=0;
  size = 5;

  constructor(
    private productService: ProductService
  ) {
  }

  ngOnInit(): void {
    this.findAllSoldProducts();
  }



  private findAllSoldProducts() {
    this.productService.findAllSoldProducts({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (response) => {
        this.soldProducts = response;
      }
    })
  }

  goToFirstPage() {
    this.page=0;
    this.findAllSoldProducts();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllSoldProducts();
  }

  goToPage(index: number) {
    this.page=index;
    this.findAllSoldProducts();
  }

  goToNextPage() {
    this.page++;
    this.findAllSoldProducts();
  }

  goToLastPage() {
    this.page=this.soldProducts.totalPages as number - 1;
    this.findAllSoldProducts();
  }

  get isLastPage(): boolean {
    return this.page == this.soldProducts.totalPages as number -1;
  }
}
