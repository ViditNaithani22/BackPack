import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LandingComponent } from './pages/landing/landing.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ProductCardComponent } from './components/product-card/product-card.component';
import { ProductCollectionComponent } from './components/product-collection/product-collection.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { RegisterComponent } from './pages/register/register.component';
import { CartComponent } from './components/cart/cart.component';
import { CartPageComponent } from './pages/cart-page/cart-page.component';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './pages/login/login.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { ViewProductComponent } from './pages/view-product/view-product.component';
import { BackpackDetailComponent } from './pages/backpack-detail/backpack-detail.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { CheckoutPageComponent } from './pages/checkout-page/checkout-page.component';
import { CreateBackPackComponent } from './pages/create-back-pack/create-back-pack.component';
import { ViewBackpacksComponent } from './components/view-backpacks/view-backpacks.component';
import { ViewBackpacksPageComponent } from './pages/view-backpacks-page/view-backpacks-page.component';
import { SearchBackpacksBarComponent } from './components/search-backpacks-bar/search-backpacks-bar.component';

@NgModule({
  declarations: [
    AppComponent,
    LandingComponent,
    NavbarComponent,
    ProductCardComponent,
    ProductCollectionComponent,
    SearchBarComponent,
    SearchBackpacksBarComponent,
    RegisterComponent,
    ViewProductComponent,
    CartComponent,
    CartPageComponent,
    LoginComponent,
    InventoryComponent,
    RegisterComponent,
    BackpackDetailComponent,
    CheckoutComponent,
    CheckoutPageComponent,
    CreateBackPackComponent,
    ViewBackpacksComponent,
    ViewBackpacksPageComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }