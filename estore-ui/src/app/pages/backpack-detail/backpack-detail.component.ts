import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BackPack } from 'src/app/models/backpack.model';
import { CartItem } from 'src/app/models/CartItem';
import { Product } from 'src/app/models/Product';
import { User } from 'src/app/models/user.model';
import { BackpackService } from 'src/app/services/backpack.service';
import { CartService } from 'src/app/services/cart.service';
import { UserService } from 'src/app/services/user.service';
import { UserStore } from 'src/app/state/user.store';

@Component({
  selector: 'app-backpack-detail',
  templateUrl: './backpack-detail.component.html',
  styleUrls: ['./backpack-detail.component.scss']
})
export class BackpackDetailComponent implements OnInit {
  backpack!:BackPack;
  user!:User;
  products:Product[] = [];
  loggedInId:number=0;
  public isLoggedIn:boolean = false;


  constructor(private route: ActivatedRoute, private backpackService:BackpackService, private userService:UserService, private cartService:CartService, private router:Router, private userStore:UserStore) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.backpackService.getBackpack(id).subscribe((backpack)=>{
      this.backpack = backpack;
      this.userService.getUser(Number(backpack.userId)).subscribe((user)=>{
        this.user = user;
      })
    });
    this.backpackService.getProductsForBackpack(id).subscribe((products)=>{
      console.log(products);
      this.products = products;
    })
    this.userStore.getUserId().subscribe(id => this.loggedInId = Number(id));
    this.userStore.isLoggedIn().subscribe(isLoggedIn => this.isLoggedIn = isLoggedIn);
  }

  addToCart(){
    this.products.forEach(product =>{
      if(product.isChecked){
        const cartItem:CartItem = {productId: Number(product.id), userId: Number(this.loggedInId), quantity: 1};
        this.cartService.addToCart(cartItem).subscribe(item => {
          this.router.navigate(['/cart']);
        })
      }
    })
    
  }

  selectAll(){
    this.products.forEach(product => {
      product.isChecked = !product.isChecked;
    });
  }

}