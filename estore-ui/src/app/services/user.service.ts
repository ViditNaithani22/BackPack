import { Injectable } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';
import { User } from '../models/user.model';
import { HttpClient, HttpHeaders } from '@angular/common/http'

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiURL = 'http://localhost:8080/users'

  constructor(private httpClient: HttpClient) {

  }

  createUser(user: User): Observable<User> {
    return this.httpClient.post<User>(this.apiURL, user, httpOptions).pipe(
      catchError(error => {
        if (error.error instanceof ErrorEvent) {
          console.log(`Error: ${error.error.message}`);
        } else {
          console.log(`Error: ${error.message}`);
        }
        return of({ "username": "" });
      })
    )
  }


  getUsersMatchingName(username: string): Observable<User[]> {
    const endpoint = `${this.apiURL}?name=${username}`
    const users = this.httpClient.get<User[]>(endpoint);
    return users;
  }

  getUser(id:number):Observable<User>{
    const endpoint =`${this.apiURL}/${id}`;
    return this.httpClient.get<User>(endpoint);
  }

  
 
}
