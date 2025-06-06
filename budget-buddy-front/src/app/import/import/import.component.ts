import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Transaction} from "../../admin/transaction/transaction";
import {CreateComponent} from "../../admin/transaction/create/create.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {BudgetOverviewPerMonth} from "../../entity/BudgetOverviewPerMonth";
import {ImportModalComponent} from "../../modal/import-modal/import-modal.component";
import {BudgetPerMonthService} from "../../budget-per-month/budgetPerMonth.service";
import { MonthlyBudgetOverview } from 'src/app/entity/MonthlyBudgetOverview';

@Component({
  selector: 'app-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.css',
    '../../../assets/modal_form_layout.css',
    '../../../assets/panel_layout.css',
    '../../../assets/table_layout.css',
    '../../../assets/pagination_layout.css']
})
export class ImportComponent implements OnInit {

  uploadForm!: FormGroup;
  transactions: Transaction[] = [];
  transaction: Transaction;
  submitted: boolean = false;
  budgetOverview: BudgetOverviewPerMonth[] = [];
  monthlyBudgetOverview: MonthlyBudgetOverview;
  newTransactions: Transaction[] = [];
  constructor(public budgetService: BudgetPerMonthService,
              public modalService: NgbModal) { }

  ngOnInit() {
    this.submitted = false;
    this.uploadForm = new FormGroup({
      upload: new FormControl('', Validators.required)
    });
  }

  createTransaction(){
    const modalRef = this.modalService.open(CreateComponent);

    modalRef.result.then((result) => {
      if (result === 'saved') {
        modalRef.close();
        let tx :Transaction = modalRef.componentInstance.transaction
        this.loadBudgetOverview(tx.date.getMonth()+1, tx.date.getFullYear());
      }
    });
  }

  loadBudgetOverview(month: number, year: number) {
    this.budgetService.getMonthlyBudgetOverview(month, year)
      .subscribe(data => {
        this.monthlyBudgetOverview = data;
      });
  }

  uploadPDF() {
    const modalRef = this.modalService.open(ImportModalComponent);
    modalRef.result.then((result) => {
      if (result) {
        this.budgetOverview = result.data.import.existingTransactions;
        this.newTransactions = result.data.import.newTransactions;
        this.loadBudgetOverview(4, 2025);
      }
    });
  }

}
