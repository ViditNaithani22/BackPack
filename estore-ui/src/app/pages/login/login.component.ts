import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserStore } from 'src/app/state/user.store';
import { User } from '../../models/user.model';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {

  user: User = new User;
  public isLoggedIn: boolean = false;

  constructor(private userService: UserService, private router: Router, private userStore: UserStore) {
    if (this.isLoggedIn) {
      router.navigate(['']);
    }
  }

  ngOnInit(): void {
    this.userStore.isLoggedIn().subscribe(isLoggedIn => this.isLoggedIn = isLoggedIn)
    this.resetForm();
  }

  resetForm(form?: NgForm) {
    if (form != null) {
      form.reset();
    }
    this.user = {
      username: ""
    }
  }

  doesNotExist = false;

  submitData(username: string) {
    this.userService.getUsersMatchingName(username).subscribe((users) => {
      users.forEach(user => {
        console.log(user);
        if (user.username == this.user.username) {
          this.userStore.setUser(user);
          this.router.navigate(['']);

        }
      })

      this.doesNotExist = true;
    })


  }

}