import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
    providedIn: 'root'
})

export class UserStore{
    private user$ = new BehaviorSubject<User>({username: ''});

    constructor(){
        const userJson = localStorage.getItem("user");
        if(userJson !=null){
            this.user$.next(JSON.parse(userJson));
        }  
    }

    public getUser():Observable<User>{
        return this.user$;
    }

    public setUser(user: User){
        this.user$.next(user);
        localStorage.setItem("user", JSON.stringify(user));
    }

    public clearUser(){
        this.user$.next({username: ''})
        localStorage.removeItem("user");
    }

    public isLoggedIn() : Observable<boolean>{
        return this.getUser().pipe(
            map((user) => user.username != '')
        )
    }

    public isAdminLoggedIn() : Observable<boolean>{
        return this.getUser().pipe(
            map((user) => !!user.isAdmin )
        )
    }

    public getUserName() : Observable<string>{
        return this.getUser().pipe(
            map((user) => user.username)
        )
    }

    public getUserId() : Observable<number| undefined>{
        return this.getUser().pipe(
            map((user) => user.id)
        )
    }
}

