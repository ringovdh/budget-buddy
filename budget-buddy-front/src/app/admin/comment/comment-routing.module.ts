import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from "./index/index.component";
import { CreateComponent } from "./create/create.component";
import { EditComponent } from "./edit/edit.component";

const routes: Routes = [
  { path: 'comment', redirectTo: 'comment/index', pathMatch: 'full'},
  { path: 'comment/index', component: IndexComponent },
  { path: 'comment/create', component: CreateComponent },
  { path: 'comment/:commentId/edit', component: EditComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class CommentRoutingModule { }
