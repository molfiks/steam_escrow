import {Component, OnInit} from '@angular/core';
import {ProductRequest} from '../../../../services/models/product-request';
import {ProductService} from '../../../../services/services/product.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-manage-product',
  standalone: false,
  templateUrl: './manage-product.component.html',
  styleUrl: './manage-product.component.scss'
})
export class ManageProductComponent implements OnInit{

  errorMsg: Array<string> = [];
  selectedCover: any;
  selectedPicture: string | undefined;
  productRequest: ProductRequest = {price: 0, description: "", title: ""};

  constructor(
    private productService: ProductService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    const productId = (this.activatedRoute.snapshot.params['productId']);
    if(productId){
      this.productService.findProductById({
        'product-id': productId
      }).subscribe({
        next: (product) => {
          this.productRequest = {
            id: product.id,
            title: product.title as string,
            description: product.description as string,
            shareable: product.shareable,
            price: product.price
          }
          if(product.cover){
            this.selectedPicture = 'data:image/jpg;base64,' + product.cover;
          }
        }
      })
    }
  }

  onFileSelected(event: any) {
    this.selectedCover = event.target.files[0];
    console.log(this.selectedCover);
    if(this.selectedCover){
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;
      }
      reader.readAsDataURL(this.selectedCover);
    }
  }

  // anasını amısını skm file neden yok diyor savelerken
  saveProduct() {
    this.productService.saveProduct({
      body: this.productRequest
    }).subscribe({
      next: (productId) => {
        this.productService.uploadProductCoverPicture({
          'product-id': productId,
          body: {
            file: this.selectedCover
          }
        }).subscribe({
          next: () => {
            this.router.navigate(['/products/my-products']);
          }
        })
      },
      error: (err) => {
        this.errorMsg = err.error.validationErrors;
      }
    });
  }
}
