import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgxPaginationModule } from "ngx-pagination";
import { IndexComponent } from './index/index.component';
import { CommonModule } from '@angular/common';
import { ProjectRoutingModule } from "./project-routing.module";
import { CreateComponent } from './create/create.component';
import { EditComponent } from './edit/edit.component';

@NgModule({
  declarations: [
    IndexComponent,
    CreateComponent,
    EditComponent,
  ],
  imports: [
    CommonModule,
    ProjectRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule
  ]
})
export class ProjectModule { }
