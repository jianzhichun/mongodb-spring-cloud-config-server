import { Component, ViewChild } from '@angular/core';
import { JsonEditorComponent, JsonEditorOptions } from 'ng2-jsoneditor';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public editorOptions: JsonEditorOptions;
  @ViewChild(JsonEditorComponent) editor: JsonEditorComponent;

  constructor() {
    this.editorOptions = new JsonEditorOptions();
  }

  public setTreeMode() {
    this.editor.setMode('tree');
  }
}
