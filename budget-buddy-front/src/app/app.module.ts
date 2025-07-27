import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { registerLocaleData} from '@angular/common';
import localeBE from '@angular/common/locales/be';

import { AppComponent } from './app.component';
import { RouterLink, RouterOutlet } from "@angular/router";
import { HttpClientModule } from "@angular/common/http";
import { NgxPaginationModule } from 'ngx-pagination';
import { CommentModule } from './admin/comment/comment.module';
import { CategoryModule } from './admin/category/category.module';
import { ImportModule } from './import/import.module';
import { NgbModule, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TransactionModule } from './admin/transaction/transaction.module';
import { ReactiveFormsModule } from "@angular/forms";
import { ConfirmationModalComponent } from './modal/confirmation-modal/confirmation-modal.component';
import { ImportModalComponent } from './modal/import-modal/import-modal.component';
import { BudgetPerMonthModule } from "./budget-per-month/budgetPerMonth.module";
import { BudgetTransactionsModalComponent } from './modal/budget-transactions-modal/budget-transactions-modal.component';
import { BudgetPerCategoryModule } from './budget-per-category/budgetPerCategory.module';
import { BudgetPerYearModule } from './budget-per-year/budgetPerYear.module';
import { BudgetBuddySelectComponentModule } from './forms/budgetBuddySelectComponent.module';
import { ProjectModule } from './admin/project/project.module';
import { ProjectsModule } from './projects/projects.module';
import { ProjectTransactionsModalComponent } from './modal/project-transactions-modal/project-transactions-modal.component';
import {SavingsModule} from "./savings/savings.module";

@NgModule({
  declarations: [
    AppComponent,
    ConfirmationModalComponent,
    ImportModalComponent,
    BudgetTransactionsModalComponent,
    ProjectTransactionsModalComponent
  ],
  imports: [
    BrowserModule,
    RouterOutlet,
    CommentModule,
    CategoryModule,
    ProjectModule,
    ProjectsModule,
    ImportModule,
    TransactionModule,
    BudgetPerMonthModule,
    BudgetPerCategoryModule,
    BudgetPerYearModule,
    BudgetBuddySelectComponentModule,
    SavingsModule,
    HttpClientModule,
    RouterLink,
    NgxPaginationModule,
    NgbModule,
    ReactiveFormsModule,
  ],
  providers: [NgbActiveModal],
  bootstrap: [AppComponent]
})

export class AppModule { }

registerLocaleData(localeBE, 'be');
