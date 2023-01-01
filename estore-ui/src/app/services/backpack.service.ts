import { Injectable } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';
import { BackPack } from '../models/backpack.model';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Product } from '../models/Product';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

@Injectable({
  providedIn: 'root'
})


export class BackpackService {
  private apiURL = 'http://localhost:8080/backpack'

  constructor(private httpClient: HttpClient) {

  }




  createBackPack(backpack: BackPack): Observable<BackPack> {
    return this.httpClient.post<BackPack>(this.apiURL, backpack, httpOptions).pipe(
      catchError(error => {
        if (error.error instanceof ErrorEvent) {
          console.log(`Error: ${error.error.message}`);
        } else {
          console.log(`Error: ${error.message}`);
        }
        return of({
          "name": "",
          "userId": 0,
          "description": "",
          "location": "",
          "activity": "",
          "productId": [0, 0, 0],
        });
      })
    )
  }


  getBackPacksMatchingLocation(location: string): Observable<BackPack[]> {
    const endpoint = `${this.apiURL}?location=${location}`
    const backpacks = this.httpClient.get<BackPack[]>(endpoint);
    return backpacks;
  }

  getBackPack(): BackPack | null {
    let backpack = localStorage.getItem("backpack");
    if (backpack) {
      return JSON.parse(backpack);
    } else {
      return null;
    }
  }

  searchBackpacks(containsText:string): Observable<BackPack[]>{
    const endpoint = `${this.apiURL}?search=${containsText}`;
    console.log(endpoint);
    const backpacks =  this.httpClient.get<BackPack[]>(endpoint);
    return backpacks;
  }

  setBackPack(backpack: BackPack) {
    localStorage.setItem("backpack", JSON.stringify(backpack));

  }

  getBackpacks(): Observable<BackPack[]> {
    const backpacks = this.httpClient.get<BackPack[]>(this.apiURL);
    return backpacks;
  }

  getBackpack(id:number): Observable<BackPack>{
    const endpoint = this.apiURL+"/"+id;
    return this.httpClient.get<BackPack>(endpoint);
  }

  getProductsForBackpack(id:number): Observable<Product[]>{
    const endpoint = `${this.apiURL}/products/${id}`;
    return this.httpClient.get<Product[]>(endpoint);
  }

  deleteBackpack(backpack: BackPack): Observable<BackPack[]>{
    const url = `${this.apiURL}/${backpack.id}`;
    return this.httpClient.delete<BackPack[]>(url)
  }


}