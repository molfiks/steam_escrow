import {Component, OnInit} from '@angular/core';
import {PageResponseProductResponse} from '../../../../services/models/page-response-product-response';
import {ProductService} from '../../../../services/services/product.service';
import {Router} from '@angular/router';
import {ProductResponse} from '../../../../services/models/product-response';

@Component({
  selector: 'app-my-products',
  standalone: false,
  templateUrl: './my-products.component.html',
  styleUrl: './my-products.component.scss'
})
export class MyProductsComponent implements OnInit{

  productResponse: PageResponseProductResponse = {};
  page= 0;
  size= 5;


  constructor(
    private productService: ProductService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.findAllProducts();
  }

  private findAllProducts() {
    this.productService.findAllProductsByOwner({
      page: this.page,
      size: this.size
    }).subscribe({
      next:(products)=>{
        this.productResponse = products;
      }
    });
  }

  goToFirstPage() {
    this.page=0;
    this.findAllProducts();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllProducts();
  }

  goToPage(index: number) {
    this.page=index;
    this.findAllProducts();
  }

  goToNextPage() {
    this.page++;
    this.findAllProducts();
  }

  goToLastPage() {
    this.page=this.productResponse.totalPages as number - 1;
    this.findAllProducts();
  }

  get isLastPage(): boolean {
    return this.page == this.productResponse.totalPages as number -1;
  }

  archiveProduct(product: ProductResponse) {

  }

  shareProduct(product: ProductResponse) {
    this.productService.updateShareableStatus({
      'product-id': product.id as number
    }).subscribe({
      next: () => {
        product.shareable = !product.shareable
      }
    })
  }

  editProduct(product: ProductResponse) {
    this.router.navigate(['products','manage',product.id])
  }
}
