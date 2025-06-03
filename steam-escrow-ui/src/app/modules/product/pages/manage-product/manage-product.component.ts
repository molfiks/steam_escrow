import {Component, OnInit} from '@angular/core';
import {ProductRequest} from '../../../../services/models/product-request';
import {ProductService} from '../../../../services/services/product.service';
import {ActivatedRoute, Router} from '@angular/router';

interface AdditionalImage {
  file: File;
  preview: string;
}


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
  additionalImages: AdditionalImage[] = [];
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
          // Load additional images if available
          if (product.id && product.imagePaths && product.imagePaths.length > 0) {
            this.loadProductImages(product.id);
          }
        }
      })
    }
  }

  loadProductImages(productId: number): void {
    this.productService.getProductImages({
      'product-id': productId
    }).subscribe({
      next: (images) => {
        // We don't need to create file objects for existing images
        // Just display them in the UI
        this.additionalImages = [];
      }
    });
  }

  onCoverFileSelected(event: any) {
    this.selectedCover = event.target.files[0];
    if(this.selectedCover){
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;
      }
      reader.readAsDataURL(this.selectedCover);
    }
  }

  onAdditionalImagesSelected(event: any) {
    const files = event.target.files;
    if (files && files.length > 0) {
      for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();

        reader.onload = () => {
          this.additionalImages.push({
            file: file,
            preview: reader.result as string
          });
        };

        reader.readAsDataURL(file);
      }
    }
  }

  removeAdditionalImage(index: number): void {
    this.additionalImages.splice(index, 1);
  }


  // anasını amısını skm file neden yok diyor savelerken
  saveProduct() {
    this.productService.saveProduct({
      body: this.productRequest
    }).subscribe({
      next: (productId) => {
        // Upload cover image
        if (this.selectedCover) {
          this.productService.uploadProductCoverPicture({
            'product-id': productId,
            body: {
              file: this.selectedCover
            }
          }).subscribe();
        }

        // Upload additional images if any
        if (this.additionalImages.length > 0) {
          const files = this.additionalImages.map(img => img.file);
          this.productService.uploadProductImages({
            'product-id': productId,
            body: {
              files: files
            }
          }).subscribe({
            next: () => {
              this.router.navigate(['/products/my-products']);
            }
          });
        } else {
          // Navigate if no additional images to upload
          this.router.navigate(['/products/my-products']);
        }
      },
      error: (err) => {
        this.errorMsg = err.error.validationErrors;
      }
    });
  }
}
