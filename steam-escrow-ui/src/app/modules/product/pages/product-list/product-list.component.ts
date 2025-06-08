import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../../../services/services/product.service';
import {Router} from '@angular/router';
import {PageResponseProductResponse} from '../../../../services/models/page-response-product-response';
import { ProductResponse } from '../../../../services/models/product-response';
import {FavoritesService} from '../../../../services/services/favorites.service';

@Component({
  selector: 'app-product-list',
  standalone: false,
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit{

  productResponse: PageResponseProductResponse = {};
  page= 0;
  size= 5;
  message = '';
  level = 'success'

  constructor(
    private productService: ProductService,
    private router: Router,
    private favoritesService: FavoritesService
  ) {
  }

  ngOnInit(): void {
    this.findAllProducts();
    }

  private findAllProducts() {
    this.productService.findAllProducts({
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

  purchaseProduct(product: ProductResponse) {
    this.message = '';
    this.productService.purchaseProduct({
      'product-id': product.id as number
    }).subscribe({
      next: () => {
        this.level = 'success'
        this.message = 'Product Successfully added'
      },
      error: (err) => {
        console.log(err);
        this.level='error';
        this.message=err.error.error
      }
    })
  }

  handleAddToFavorites(product: ProductResponse) {
    this.message = '';
    this.level = 'success';
    this.message = 'Product successfully added to favorites';
  }

}
