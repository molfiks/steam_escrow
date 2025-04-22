import {Component, OnInit} from '@angular/core';
import {BoughtProductResponse} from '../../../../services/models/bought-product-response';
import {PageResponseBoughtProductResponse} from '../../../../services/models/page-response-bought-product-response';
import {ProductService} from '../../../../services/services/product.service';
import {FeedbackRequest} from '../../../../services/models/feedback-request';
import {FeedbackService} from '../../../../services/services/feedback.service';

@Component({
  selector: 'app-purchased-products-list',
  standalone: false,
  templateUrl: './purchased-products-list.component.html',
  styleUrl: './purchased-products-list.component.scss'
})
export class PurchasedProductsListComponent implements OnInit{
  purchasedProducts: PageResponseBoughtProductResponse = {};
  feedbackRequest: FeedbackRequest = {comment: "", productId: 0, note: 0};

  page=0;
  size = 5;
  selectedProduct: BoughtProductResponse | undefined= undefined;

  constructor(
    private productService: ProductService,
    private feedbackService : FeedbackService
  ) {
  }

  ngOnInit(): void {
      this.findAllPurchasedProducts();
  }


  approvePurchasedProduct(product: BoughtProductResponse) {
    this.selectedProduct = product;
    this.feedbackRequest.productId = product.id as number;
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

  approvePurchase(withFeedback: boolean) {
    this.productService.approvePurchaseProduct({
      'product-id': this.selectedProduct?.id as number
    }).subscribe({
      next: () => {
        if(withFeedback){
          this.giveFeedback();
        }
        this.selectedProduct = undefined;
        this.findAllPurchasedProducts()
      }
    });
  }

  private giveFeedback() {
    this.feedbackService.saveFeedback({
      body: this.feedbackRequest
    }).subscribe({
      next: () => {}
    });
  }
}
