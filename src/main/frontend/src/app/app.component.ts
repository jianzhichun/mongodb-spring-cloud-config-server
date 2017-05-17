import { Component } from '@angular/core';
import { Http } from '@angular/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public configs: any;

  constructor(private http: Http, private router: Router) { 
    this.http.get("/api/config/mongo").subscribe(data => this.configs = data.json());
  }

  public select(app, label, profile){
    this.router.navigate(['config', [app, label, profile].join('-')])
  }
}
