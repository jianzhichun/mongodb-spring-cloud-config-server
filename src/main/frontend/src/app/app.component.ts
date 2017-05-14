import { Component, ViewChild } from '@angular/core';
import { JsonEditorComponent, JsonEditorOptions } from 'ng2-jsoneditor';
import { Http } from '@angular/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public editorOptions: JsonEditorOptions;
  public configs: any;
  @ViewChild(JsonEditorComponent) editor: JsonEditorComponent;

  constructor(private http: Http) { 
    this.editorOptions = new JsonEditorOptions();
    this.http.get("/api/config/mongo").subscribe(data => this.configs = data.json());
  }

  public setTreeMode() {
    this.editor.setMode('tree');
  }
}
