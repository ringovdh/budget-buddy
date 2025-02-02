import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Transaction} from "../../admin/transaction/transaction";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CreateComponent} from "../../admin/transaction/create/create.component";

@Component({
  selector: 'app-import-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css',
    '../../../assets/panel_layout.css']
})
export class IndexComponent implements OnChanges {

  @Input() newTransactions: Transaction[] = [];
  @Output() savedTransaction = new EventEmitter<Transaction>();
  totalTransactions = 0;

  constructor(private modalService: NgbModal) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.totalTransactions = this.newTransactions.length;
  }



  openTransactionsModal(transaction: Transaction) {
    const modalRef = this.modalService.open(CreateComponent);
    modalRef.componentInstance.transaction = transaction;
    modalRef.result.then((result) => {
      this.newTransactions.splice(this.newTransactions.findIndex(tx => tx.number === transaction.number), 1);
      this.totalTransactions --;
      this.savedTransaction.emit(result);
    });
  }


}
