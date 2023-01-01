import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Product } from '../models/Product';
import { CartItem } from '../models/CartItem';
import { UserService } from './user.service';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private userId: number|undefined;
  private apiUrl = 'http://localhost:8080/cart'

  constructor(private httpClient:HttpClient, private userService:UserService) {
  
   }

  getCart(userId:number): Observable<Product[]>{
    console.log(userId);
    const endpoint = 'http://localhost:8080/cart/'+String(userId);
    const cart = this.httpClient.get<Product[]>(endpoint);
    return cart;
  }


  decrease(cartItem:CartItem): Observable<Product[]>{
    let urlToDecrease = "http://localhost:8080/cart/decrease"
    return this.httpClient.put<Product[]>(urlToDecrease, cartItem, httpOptions);
  }

  increase(cartItem:CartItem): Observable<Product[]>{
    let urlToIncrease = "http://localhost:8080/cart/increase"
    return this.httpClient.put<Product[]>(urlToIncrease, cartItem, httpOptions);
  }

  clearItem(cartItem:CartItem): Observable<Product[]>{
    let urlToClear = "http://localhost:8080/cart/clear"
    return this.httpClient.put<Product[]>(urlToClear, cartItem, httpOptions);
  }

  addToCart(cartItem:CartItem):Observable<CartItem>{
    console.log(cartItem);
    const newCartItem = this.httpClient.post<CartItem>(this.apiUrl, cartItem, httpOptions);
    return newCartItem;
  }

  confirmCheckout(userId:number):Observable<boolean>{
    let isSuccessful = this.httpClient.get<boolean>("http://localhost:8080/cart/checkout/"+String(userId));
    return isSuccessful;
  }

}
