import {Component, OnInit} from '@angular/core';
import {PageResponseProductResponse} from '../../../../services/models/page-response-product-response';
import {PageResponseFavoritesResponse} from '../../../../services/models/page-response-favorites-response';
import {ProductService} from '../../../../services/services/product.service';
import {Router} from '@angular/router';
import {FavoritesService} from '../../../../services/services/favorites.service';
import {removeFromFavorites} from '../../../../services/fn/favorites/remove-from-favorites';
import {ProductResponse} from '../../../../services/models/product-response';

@Component({
  selector: 'app-favorites',
  standalone: false,
  templateUrl: './favorites.component.html',
  styleUrl: './favorites.component.scss'
})
export class FavoritesComponent implements OnInit{

  favoritesResponse: PageResponseFavoritesResponse = {};
  page= 0;
  size= 5;
  message = '';
  level = 'success';

  constructor(
    private favoritesService: FavoritesService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.findAllFavorites()
  }

  private findAllFavorites() {
    this.favoritesService.getFavorites({
      page: this.page,
      size: this.size
    }).subscribe({
      next:(favorites)=>{
        this.favoritesResponse = favorites;
      }
    });
  }


  goToFirstPage() {
    this.page=0;
    this.findAllFavorites();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllFavorites();
  }

  goToPage(index: number) {
    this.page=index;
    this.findAllFavorites();
  }

  goToNextPage() {
    this.page++;
    this.findAllFavorites();
  }

  goToLastPage() {
    this.page=this.favoritesResponse.totalPages as number - 1;
    this.findAllFavorites();
  }

  get isLastPage(): boolean {
    return this.page == this.favoritesResponse.totalPages as number -1;
  }

  removeFromFavorites(product: ProductResponse) {
    if (product.id) {
      this.favoritesService.removeFromFavorites({
        productId: product.id
      }).subscribe({
        next: () => {
          this.message = 'Product removed from favorites';
          this.level = 'success';
          this.findAllFavorites(); // Refresh the list
        },
        error: (err) => {
          console.log(err);
          this.level = 'error';
          this.message = err.error?.error || 'Error removing product from favorites';
        }
      });
    }
  }

}

