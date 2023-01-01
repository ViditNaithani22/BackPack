import { Component, OnInit } from '@angular/core';
import { UserStore } from 'src/app/state/user.store';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {
  public isLoggedIn:boolean = false;

  constructor(private userStore:UserStore) { }

  ngOnInit(): void {
    this.userStore.isLoggedIn().subscribe(isLoggedIn => this.isLoggedIn = isLoggedIn)
  }


}
