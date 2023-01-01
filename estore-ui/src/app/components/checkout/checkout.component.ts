import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/Product';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';
import { UserStore } from 'src/app/state/user.store';
@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html'
})
export class CheckoutComponent implements OnInit {

  cart: Product[] = [];
  inventory: Product[] = [];
  totalPrice: number = 0;
  deliveryAddress = "";
  phoneNumber = "";
  cardNumber = "";
  userId: number = 0;
  constructor(private cartService: CartService, private productService: ProductService, private userStore: UserStore) { }


  ngOnInit(): void {
    this.userStore.getUserId().subscribe((userId) => {
      this.userId = userId != undefined ? userId : 0;
      this.cartService.getCart(this.userId).subscribe((cart) => {
        console.log(cart);
        this.cart = cart
      });
    });
    this.productService.getProducts().subscribe((inventory) => this.inventory = inventory);
  }

  updateTotalPrice(): number {
    let totalPrice = 0;
    for (const product of this.cart) {
      let thisRowTotal = (product.price) * (product.quantity);
      product.totalPrice = Number(thisRowTotal.toFixed(2));
      totalPrice += thisRowTotal;
    }
    return Number(totalPrice.toFixed(2));
  }

  confirmCheckout(): void {
    alert("Thank you for shopping with us!");
    this.cartService.confirmCheckout(this.userId).subscribe((success) => {
      if (success) {

        this.userStore.getUserId().subscribe((userId) => {
          this.userId = userId != undefined ? userId : 0;
          this.cartService.getCart(this.userId).subscribe((cart) => {
            console.log(cart);
            this.cart = cart;
          });
          this.productService.getProducts().subscribe((inventory) => this.inventory = inventory);
        });


        
      }
    });
    window.open("http://localhost:4200", "_self");
  }

}
