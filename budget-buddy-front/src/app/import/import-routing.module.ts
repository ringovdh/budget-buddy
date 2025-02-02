import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ImportComponent } from "./import/import.component";

const routes: Routes = [
  { path: 'import', redirectTo: 'import/import', pathMatch: 'full' },
  { path: 'import/import', component: ImportComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class ImportRoutingModule { }
