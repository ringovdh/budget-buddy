import { Component, OnInit } from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ImportService} from "../../import/import.service";

@Component({
  selector: 'app-import-modal',
  templateUrl: './import-modal.component.html',
  styleUrls: ['./import-modal.component.css',
    '../../../assets/modal_form_layout.css']
})
export class ImportModalComponent implements OnInit {

  uploadForm!: FormGroup;
  selectedFile: null;
  currentFile?: File;

  constructor(public importService: ImportService,
              public ngbActiveModal: NgbActiveModal) { }

  ngOnInit(): void {
    this.uploadForm = new FormGroup({
      upload: new FormControl('', Validators.required)
    });
  }

  selectedPDFFile(event:any): void {
    if (event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
    }
  }

  submit() {
    if (this.selectedFile) {
      const file: File | null = this.selectedFile;

      if (file) {
        this.currentFile = file;
        this.importService.uploadTransactions(this.currentFile).subscribe(data => {
          this.ngbActiveModal.close(data);
          console.log('Transaction successfully loaded!' , data);
        });

      }
    }
  }

  close() {
    this.ngbActiveModal.close('closed');
  }
}
