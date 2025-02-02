import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {ProjectData} from "../../entity/ProjectData";

@Component({
  selector: 'app-project-resume',
  templateUrl: './project-resume.component.html',
  styleUrls: ['./project-resume.component.css',
    '../../../assets/panel_layout.css',
    '../../../assets/resume.layout.css']
})
export class ProjectResumeComponent implements OnChanges {

  @Input() projectsData: ProjectData[];
  totalProjects: number;

  ngOnChanges(changes: SimpleChanges): void {
    this.countTotalProjects()
  }

  countTotalProjects = () => {
    this.totalProjects = this.projectsData.map(p => p.total).reduce((a, c) => { return a + c }, 0);
  }
}
