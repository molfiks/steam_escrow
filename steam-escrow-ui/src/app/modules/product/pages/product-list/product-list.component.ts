import {Component, OnInit} from '@angular/core';
import {ProductService} from '../../../../services/services/product.service';
import {Router} from '@angular/router';
import {PageResponseProductResponse} from '../../../../services/models/page-response-product-response';

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

  constructor(
    private productService: ProductService,
    private router: Router
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
        this.productResponse = products
      }
    })
  }
}
