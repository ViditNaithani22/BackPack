import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { User } from '../../models/user.model';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent implements OnInit {

  user: User = new User;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.resetForm();
  }

  resetForm(form?: NgForm) {
    if (form != null)
      form.reset();
    this.user = {
      username: ""
    }
  }

  submitted = false;
  alreadyExists = false;

  submitData(username: string) {

    console.log("user: " + username);


    localStorage.setItem(this.user.username, JSON.stringify(this.user));
    let data: any = localStorage.getItem(this.user.username);
    console.log(JSON.parse(data));

    this.userService.createUser(this.user).subscribe((user) => {
      console.log(user);
      if (user.username == "") {
        this.alreadyExists = true;
        this.submitted = false;
      }
      else {
        this.submitted = true;
      }
    });

  }

}
