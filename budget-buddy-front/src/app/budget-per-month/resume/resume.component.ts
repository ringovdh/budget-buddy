import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {ResumeData} from "../../entity/ResumeData";

@Component({
  selector: 'app-resume',
  templateUrl: './resume.component.html',
  styleUrls: ['./resume.component.css',
  '../../../assets/resume.layout.css']
})
export class ResumeComponent implements OnChanges {

  @Input() resumeData: ResumeData;

  constructor() { }

  ngOnChanges(changes: SimpleChanges) {
  }

}
