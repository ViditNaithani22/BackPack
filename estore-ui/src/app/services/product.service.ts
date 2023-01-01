import { Injectable } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Product } from '../models/Product';
const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}
@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/products'

  constructor(private httpClient:HttpClient) { }

  getProducts(): Observable<Product[]>{
    const products =  this.httpClient.get<Product[]>(this.apiUrl);
    return products;
  }

  getProductById(id:number): Observable<Product>{
    const endpoint = `${this.apiUrl}/${id}`;
    const product = this.httpClient.get<Product>(endpoint);
    return product;
  }
  
  searchProducts(containsText:string): Observable<Product[]>{
    const endpoint = `${this.apiUrl}?name=${containsText}`;
    console.log(endpoint);
    const products =  this.httpClient.get<Product[]>(endpoint);
    return products;
  }
  
  updateProduct(product: Product): Observable<Product[]>{
    return this.httpClient.put<Product[]>(this.apiUrl, product,httpOptions);
  }

  deleteProduct(product: Product): Observable<Product[]>{
    const url = `${this.apiUrl}/${product.id}`;
    return this.httpClient.delete<Product[]>(url)
  }

  createProduct(product: Product): Observable<Product> {
    return this.httpClient.post<Product>(this.apiUrl, product, httpOptions).pipe(
      catchError(error => {
        if (error.error instanceof ErrorEvent) {
          console.log(`Error: ${error.error.message}`);
        } else {
          console.log(`Error: ${error.message}`);
        }
        return of({ "name": "",
                    "description":"",
                    "price":0,
                     "quantity":0,
                    "manufacturer":"",
                    "imageUrl":""
                   });
      })
    )
  }



}

