import { Component, OnInit } from '@angular/core';
import { CartItem } from 'src/app/models/CartItem';
import { Product } from 'src/app/models/Product';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import { UserStore } from 'src/app/state/user.store';

@Component({
  selector: 'app-product-collection',
  templateUrl: './product-collection.component.html'
})
export class ProductCollectionComponent implements OnInit {
  products: Product[] = [];
  userId:number = 0;

  constructor(private productService: ProductService, private userService:UserService, private cartService: CartService, private userStore:UserStore) { }

  ngOnInit(): void {
    this.productService.getProducts().subscribe((products) => this.products = products)
    this.userStore.getUserId().subscribe((id)=> this.userId = Number(id));
  }

  searchProducts(searchWith: string) {
    console.log(searchWith)

    this.productService.searchProducts(searchWith).subscribe((products) => {
      console.log(products);
      this.products = products
    });
  }


  addToCart(productId:number) {
    let userId = this.userId;
    if (userId && productId) {

      const cartItem: CartItem = {
        userId: userId,
        productId: productId,
        quantity: 1
      };
      this.cartService.addToCart(cartItem).subscribe((cartItem) => {
        console.log(cartItem)
      });

    }
  }

}
