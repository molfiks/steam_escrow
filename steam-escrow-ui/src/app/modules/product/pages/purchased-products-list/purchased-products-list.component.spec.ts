import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchasedProductsListComponent } from './purchased-products-list.component';

describe('PurchasedProductsListComponent', () => {
  let component: PurchasedProductsListComponent;
  let fixture: ComponentFixture<PurchasedProductsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PurchasedProductsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PurchasedProductsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
