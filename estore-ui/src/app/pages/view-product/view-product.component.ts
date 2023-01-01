import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/Product';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import { CartService } from 'src/app/services/cart.service';
import { CartItem } from 'src/app/models/CartItem';
import { UserStore } from 'src/app/state/user.store';

@Component({
  selector: 'app-view-product',
  templateUrl: './view-product.component.html'
})
export class ViewProductComponent implements OnInit {
  
  public userId:number|undefined;
  public isLoggedIn:boolean = false;
  public isAdminLoggedIn:boolean = false;
  product!:Product;

  constructor( private route: ActivatedRoute, private productService:ProductService, private userService:UserService, private cartService: CartService, private userStore: UserStore) { }

  ngOnInit(): void {
    this.getProduct()
    this.userStore.getUserId().subscribe(userId => this.userId = userId);
    this.userStore.isLoggedIn().subscribe(isLoggedIn => this.isLoggedIn = isLoggedIn)
    this.userStore.isAdminLoggedIn().subscribe(isAdminLoggedIn => this.isAdminLoggedIn = isAdminLoggedIn)
  }

  getProduct(){
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProductById(id).subscribe((product)=> this.product = product)
  }

  addToCart(){

    let userId = this.userId;
    let productId =  this.product.id;

    if(userId && productId){

      const cartItem:CartItem = {
        userId: userId,
        productId: productId,
        quantity: 1
      };
      this.cartService.addToCart(cartItem).subscribe((cartItem)=> {
        console.log(cartItem)
      });
  
    }
    
    
  }

}