import {Component, Input, OnInit} from '@angular/core';
import {Project} from "../../projects/project";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-project-transactions-modal',
  templateUrl: './project-transactions-modal.component.html',
  styleUrls: ['./project-transactions-modal.component.css',
    '../../../assets/modal_form_layout.css']
})
export class ProjectTransactionsModalComponent implements OnInit {

  @Input() public project: Project;

  constructor(public ngbActiveModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  close() {
    this.ngbActiveModal.close('closed');
  }

}
