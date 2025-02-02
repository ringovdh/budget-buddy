import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from "./index/index.component";
import { CreateComponent } from "./create/create.component";
import { EditComponent } from "./edit/edit.component";

const routes: Routes = [
  { path: 'category', redirectTo: 'category/index', pathMatch: 'full'},
  { path: 'category/index', component: IndexComponent },
  { path: 'category/create', component: CreateComponent },
  { path: 'category/:categoryId/edit', component: EditComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class CategoryRoutingModule { }
