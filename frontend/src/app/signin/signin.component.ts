import { Component } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import {
  Form,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-signin',
  imports: [SharedModule],
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.css',
})
export class SigninComponent {
  hidePassword = true;

  registerForm: any;

  constructor(private fb: FormBuilder) {
    this.registerForm = fb.group({
      email: ['', Validators.required],
      phone: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  validateForm(){
    console.log(this.registerForm);
  }

}
