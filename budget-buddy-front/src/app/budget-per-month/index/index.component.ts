import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import { BudgetOverviewPerMonth } from "../../entity/BudgetOverviewPerMonth";
import { BudgetPerMonthService } from "../budgetPerMonth.service";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { BudgetTransactionsModalComponent } from "../../modal/budget-transactions-modal/budget-transactions-modal.component";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css',
    '../../../assets/panel_layout.css']
})
export class IndexComponent implements OnChanges {
  @Input() incomingBudget: BudgetOverviewPerMonth[] = [];
  @Input() outgoingBudget: BudgetOverviewPerMonth[] = [];
  @Input() fixedOutgoingBudget: BudgetOverviewPerMonth[] = [];
  @Input() savings: BudgetOverviewPerMonth[] = [];

  constructor(private modalService: NgbModal) {

  }

  ngOnChanges(changes: SimpleChanges) {

  }

  openTransactionsModal(overview: BudgetOverviewPerMonth) {
    const modalRef = this.modalService.open(BudgetTransactionsModalComponent);
    modalRef.componentInstance.overview = overview;
  }

}
