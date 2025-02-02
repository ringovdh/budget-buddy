package be.yorian.budgetbuddy.controller;

import be.yorian.budgetbuddy.dto.ProjectOverview;
import be.yorian.budgetbuddy.entity.Project;
import be.yorian.budgetbuddy.response.CustomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectController {

    ResponseEntity<CustomResponse> getProjectByProjectname(Optional<String> projectname,
                                                           Optional<Integer> page,
                                                           Optional<Integer> size);

    List<Project> getProjects();

    List<ProjectOverview> getProjectOverview();

    void createProject(Project project);

    void updateProject(Long project_id,
                       Project project);

    void deleteProject(Long project_id);
}
