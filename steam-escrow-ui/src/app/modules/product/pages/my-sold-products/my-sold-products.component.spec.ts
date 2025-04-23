import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MySoldProductsComponent } from './my-sold-products.component';

describe('MySoldProductsComponent', () => {
  let component: MySoldProductsComponent;
  let fixture: ComponentFixture<MySoldProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MySoldProductsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MySoldProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
