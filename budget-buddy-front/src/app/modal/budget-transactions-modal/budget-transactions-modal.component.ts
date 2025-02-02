import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import { EditComponent } from 'src/app/admin/transaction/edit/edit.component';
import { Transaction } from 'src/app/admin/transaction/transaction';
import { TransactionService } from 'src/app/admin/transaction/transaction.service';
import {BudgetOverviewPerMonth} from "../../entity/BudgetOverviewPerMonth";
import { ConfirmationModalComponent } from '../confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-budget-transactions-modal',
  templateUrl: './budget-transactions-modal.component.html',
  styleUrls: ['./budget-transactions-modal.component.css',
    '../../../assets/modal_form_layout.css']
})
export class BudgetTransactionsModalComponent implements OnInit {

  @Input() public overview: BudgetOverviewPerMonth;

  constructor(public ngbActiveModal: NgbActiveModal,
              public transactionService: TransactionService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
  }

  editTransaction(tx: Transaction){
    const modalRef = this.modalService.open(EditComponent);
    modalRef.componentInstance.transaction = tx;

    modalRef.componentInstance.returnTransaction.subscribe((receivedTransaction: Transaction) => {
      let index = this.overview.transactions.indexOf(tx);
      this.overview.transactions[index] = receivedTransaction;
    });
  }

  deleteTransaction(id: number) {
    const modalRef = this.modalService.open(ConfirmationModalComponent);
    modalRef.result.then((result) => {
      if (result === 'confirmed') {
        this.transactionService.delete(id).subscribe(() => {
          this.overview.transactions = this.overview.transactions.filter(item => item.tx_id !== id);
          console.log('Transaction deleted successfully!');
        })
      }
    });
  }

  close() {
    this.ngbActiveModal.close('closed');
  }
}
