import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-search-backpacks-bar',
  templateUrl: './search-backpacks-bar.component.html'
})
export class SearchBackpacksBarComponent {

  @Output() onSearchBackpacks: EventEmitter<string> = new EventEmitter();

  onSearch(searchString : string){
    this.onSearchBackpacks.emit(searchString);
  }

}