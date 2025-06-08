import {Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {ProductResponse} from '../../../../services/models/product-response';
import {FavoritesService} from '../../../../services/services/favorites.service';
import {FavoritesRequest} from '../../../../services/models/favorites-request';

@Component({
  selector: 'app-product-card',
  standalone: false,
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.scss'
})
export class ProductCardComponent {
  private _product: ProductResponse = {};
  private _manage = false;
  private _productCover: string | undefined;

  @ViewChild('imageContainer') imageContainer: ElementRef | undefined;

  get product(): ProductResponse {
    return this._product;
  }

  @Input()
  set product(value: ProductResponse) {
    this._product = value;
  }



  get manage(): boolean {
    return this._manage;
  }
  @Input()
  set manage(value: boolean) {
    this._manage = value;
  }



  get productCover(): string | undefined {
    if(this._product.cover){
      return 'data:image/jpg;base64,' + this._product.cover;
    }
    return '';
  }

  @Output() private share: EventEmitter<ProductResponse> = new EventEmitter<ProductResponse>();
  @Output() private archive: EventEmitter<ProductResponse> = new EventEmitter<ProductResponse>();
  @Output() private purchase: EventEmitter<ProductResponse> = new EventEmitter<ProductResponse>();
  @Output() private edit: EventEmitter<ProductResponse> = new EventEmitter<ProductResponse>();
  @Output() private details: EventEmitter<ProductResponse> = new EventEmitter<ProductResponse>();
  @Output() private addToFavorites: EventEmitter<ProductResponse> = new EventEmitter<ProductResponse>();
  @Input() isFavoritePage: boolean = false;
  @Output() removeFromFavorites: EventEmitter<ProductResponse> = new EventEmitter<ProductResponse>();

  constructor(private favoritesService: FavoritesService) {}



  onShowDetails() {
    this.details.emit(this._product);
  }

  onPurchase() {
    this.purchase.emit(this._product);
  }

  onAddtoFavoritesList() {
    if (this.isFavoritePage) {
      this.removeFromFavorites.emit(this._product);
    } else {
      this.onAddToFavorites();
    }
  }

  onAddToFavorites() {
    // Add to favorites functionality
    if (this._product.id) {
      const request: FavoritesRequest = {
        productId: this._product.id
      };

      this.favoritesService.addToFavorites({
        body: request
      }).subscribe({
        next: () => {
          console.log('Product added to favorites successfully');
          this.addToFavorites.emit(this._product);
        },
        error: (error) => {
          console.error('Error adding product to favorites:', error);
        }
      });
    }
  }

  onEdit() {
    this.edit.emit(this._product);
  }

  onShare() {
    this.share.emit(this._product);
  }

  onArchive() {
    this.archive.emit(this._product);
  }

  // Methods for scrolling the image gallery
  scrollGalleryLeft() {
    const container = document.querySelector('.image-container') as HTMLElement;
    if (container) {
      container.scrollBy({ left: -317, behavior: 'smooth' });
    }
  }

  scrollGalleryRight() {
    const container = document.querySelector('.image-container') as HTMLElement;
    if (container) {
      container.scrollBy({ left: 317, behavior: 'smooth' });
    }
  }
}
