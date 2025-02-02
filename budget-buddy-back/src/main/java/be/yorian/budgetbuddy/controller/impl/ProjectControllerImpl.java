package be.yorian.budgetbuddy.controller.impl;

import be.yorian.budgetbuddy.controller.ProjectController;
import be.yorian.budgetbuddy.dto.ProjectOverview;
import be.yorian.budgetbuddy.entity.Project;
import be.yorian.budgetbuddy.response.CustomResponse;
import be.yorian.budgetbuddy.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ProjectControllerImpl implements ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectControllerImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    @GetMapping("/projects/projectname")
    public ResponseEntity<CustomResponse> getProjectByProjectname(@RequestParam Optional<String> projectname,
                                                                  @RequestParam Optional<Integer> page,
                                                                  @RequestParam Optional<Integer> size) {
        Page<Project> projects = projectService.getProjectByProjectname(projectname.orElse(""),
                page.orElse(0), size.orElse(10));
        CustomResponse response = new CustomResponse();
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());
        Map<String, Page> dataMap = new HashMap<>();
        dataMap.put("page", projects);
        response.setData(dataMap);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/projects/")
    public List<Project> getProjects() {
        return projectService.getProjects();
    }

    @Override
    @GetMapping("/projects/overview")
    public List<ProjectOverview> getProjectOverview() {
        return projectService.getProjectOverview();
    }
    
    @Override
    @PostMapping("/projects/")
    public void createProject(@RequestBody Project project) {
        projectService.saveProject(project);
    }

    @Override
    @PutMapping("/projects/{project_id}")
    public void updateProject(@PathVariable("project_id")Long projectId,
                              @RequestBody Project project) {
        projectService.updateProject(projectId, project);
    }

    @Override
    @DeleteMapping("/projects/{project_id}")
    public void deleteProject(@PathVariable("project_id")Long projectId) {
        projectService.deleteProject(projectId);
    }

}
