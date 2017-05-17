import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Http } from '@angular/http';
import { JsonEditorComponent, JsonEditorOptions } from 'ng2-jsoneditor';

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit {
  public editorOptions: JsonEditorOptions;
  @ViewChild(JsonEditorComponent) editor: JsonEditorComponent;

  constructor(private http: Http, private route: ActivatedRoute) { 
    
    
  }

  public setTreeMode() {
    this.editor.setMode('tree');
  }

  ngOnInit() {
    this.editorOptions = new JsonEditorOptions();
    let id = this.route.snapshot.params['id'];
    this.http.get('api/config/mongo/'+id).subscribe(rs => 
      this.editor.set(rs.json())
    );
  }

}
