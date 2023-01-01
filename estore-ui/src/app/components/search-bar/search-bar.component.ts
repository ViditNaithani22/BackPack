import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html'
})
export class SearchBarComponent {

  @Output() onSearchProducts: EventEmitter<string> = new EventEmitter();

  onSearch(searchWith: string) {
    this.onSearchProducts.emit(searchWith);
  }

}
