import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {Transaction} from "../transaction";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Category} from "../../category/category";
import {TransactionService} from "../transaction.service";
import {CategoryService} from "../../category/category.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import { ProjectService } from 'src/app/admin/project/project.service';
import { Project } from 'src/app/projects/project';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css',
    '../../../../assets/modal_form_layout.css']
})
export class EditComponent implements OnInit {

  transaction!: Transaction;
  categories!: Category[];
  projects : Project[];
  editTransactionForm!: FormGroup;

  @Output() returnTransaction: EventEmitter<any> = new EventEmitter();

  constructor(public transactionService: TransactionService,
              public categoryService: CategoryService,
              public projectService: ProjectService,
              public ngbActiveModal: NgbActiveModal) { }

  ngOnInit(): void {
    this.categoryService.getAll().subscribe(data => {
      this.categories = data;
    });
    this.projectService.getAll().subscribe(data => {
      this.projects = data;
    })
    this.editTransactionForm = new FormGroup({
      number: new FormControl(this.transaction.number),
      date: new FormControl(this.transaction.date, Validators.required),
      sign: new FormControl(this.transaction.sign, Validators.required),
      amount: new FormControl(this.transaction.amount, Validators.required),
      comment: new FormControl(this.transaction.comment, Validators.required),
      category: new FormControl(this.transaction.category, Validators.required),
      project: new FormControl(this.transaction.project),
    });
  }

  compareCategory(cat1: Category, cat2: Category) {
    return cat1.id === cat2.id;
  }

  compareProject(p1: Project, p2: Project) {
    if (p2) {
      return p1.id === p2.id;
    } else {
      return false;
    }
  }

  get f(){
    return this.editTransactionForm.controls;
  }

  submit() {
    this.transactionService.update(this.transaction.tx_id, this.editTransactionForm.value)
      .subscribe((res: Transaction) => {
        this.returnTransaction.emit(res);
        this.ngbActiveModal.close('updated');
        console.log('Transaction updated successfully!');
    });
  }

  close() {
    this.ngbActiveModal.close('closed');
  }

}
