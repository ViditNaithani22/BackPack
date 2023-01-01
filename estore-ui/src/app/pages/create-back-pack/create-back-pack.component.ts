import { Component, OnInit } from '@angular/core';
import { BackPack } from '../../models/backpack.model';
import { BackpackService } from '../../services/backpack.service';
import { Product } from 'src/app/models/Product';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import { UserStore } from 'src/app/state/user.store';


@Component({
  selector: 'app-create-back-pack',
  templateUrl: './create-back-pack.component.html'
})


export class CreateBackPackComponent implements OnInit {
  products: Product[] = [];
  backpack: BackPack = new BackPack;
  pros: Array<any> = [];
  count: any = 0;
  user: any = 0;
  userId: number = 0;

  addToBackpack(productId: number) {
    this.pros.push(productId);
    this.backpack.productId = this.pros;
  }

  constructor(private backpackService: BackpackService, private productService: ProductService, private userService: UserService, private userStore: UserStore) { }

  ngOnInit(): void {
    this.userStore.getUserId().subscribe((id) => this.userId = Number(id));
    this.productService.getProducts().subscribe((products) => this.products = products);
  }
  searched = false;
  searchProducts(searchWith: string) {
    console.log(searchWith);
    this.searched = true;

    this.productService.searchProducts(searchWith).subscribe((products) => {
      console.log(products);
      this.products = products
    });
  }

  submitData() {
    this.backpack.userId = this.userId;
    this.backpackService.createBackPack(this.backpack).subscribe((backpack) => {
      console.log(backpack);
    });
  }


}
