import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { JSONEditorModule } from 'ng2-jsoneditor';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { NgPipesModule } from 'ngx-pipes';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule, MdNativeDateModule } from '@angular/material';
import { EditorComponent } from './editor/editor.component';
import { RouterModule } from '@angular/router';
import { routes } from './app.routes';

@NgModule({
  declarations: [
    AppComponent,
    EditorComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    JSONEditorModule,
    FormsModule, 
    MaterialModule,
    MdNativeDateModule,
    AccordionModule.forRoot(),
    RouterModule.forRoot(routes),
    NgPipesModule,
    HttpModule 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
