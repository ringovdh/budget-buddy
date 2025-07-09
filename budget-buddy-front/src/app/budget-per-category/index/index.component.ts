import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BudgetTransactionsModalComponent } from 'src/app/modal/budget-transactions-modal/budget-transactions-modal.component';
import {CategoricalBudgetOverview} from "../../entity/CategoricalBudgetOverview";
import {BudgetPerMonthPerCategory} from "../../entity/BudgetPerMonthPerCategory";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css',
    '../../../assets/panel_layout.css']
})
export class IndexComponent implements OnChanges {

  @Input() categoricalBudgetOverview: CategoricalBudgetOverview;

  constructor(private modalService: NgbModal) { }

  ngOnChanges(changes: SimpleChanges) { }

  openTransactionsModal(overview: BudgetPerMonthPerCategory) {
    const modalRef = this.modalService.open(BudgetTransactionsModalComponent);
    modalRef.componentInstance.overview = overview;
  }

}
