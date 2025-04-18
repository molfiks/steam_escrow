import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ProductResponse} from '../../../../services/models/product-response';

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
  @Output() private addToWaitingList: EventEmitter<ProductResponse> = new EventEmitter<ProductResponse>();
  @Output() private purchase: EventEmitter<ProductResponse> = new EventEmitter<ProductResponse>();
  @Output() private edit: EventEmitter<ProductResponse> = new EventEmitter<ProductResponse>();
  @Output() private details: EventEmitter<ProductResponse> = new EventEmitter<ProductResponse>();


  onShowDetails() {
    this.details.emit(this._product);
  }

  onPurchase() {
    this.purchase.emit(this._product);
  }

  onAddtoWaitingList() {
    this.addToWaitingList.emit(this._product);
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
}
