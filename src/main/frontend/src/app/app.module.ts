import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { JSONEditorModule } from 'ng2-jsoneditor';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { NgPipesModule } from 'ngx-pipes';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    JSONEditorModule,
    FormsModule, 
    AccordionModule.forRoot(),
    NgPipesModule,
    HttpModule 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
