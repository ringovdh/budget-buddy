import { Component, OnInit } from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-confirmation-modal',
  templateUrl: './confirmation-modal.component.html',
  styleUrls: ['./confirmation-modal.component.css',
    '../../../assets/modal_form_layout.css']
})
export class ConfirmationModalComponent implements OnInit {

  constructor(public ngbActiveModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  confirm(){
    this.ngbActiveModal.close('confirmed');
  }

  close() {
    this.ngbActiveModal.close('closed');
  }
}
