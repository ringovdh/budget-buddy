package be.yorian.budgetbuddy.repository;

import be.yorian.budgetbuddy.entity.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectRepositoryTest extends BaseRepositoryTest {

    @Autowired
    ProjectRepository repository;


    @Test
    @Sql("/test-project.sql")
    void findByProjectnameContaining() {
        Page<Project> projects = repository.findByProjectnameContaining("project", PageRequest.of(0, 1));
        assertEquals(1, projects.getTotalElements());
        assertThat(projects.getContent().getFirst())
                .satisfies(p ->
                        assertEquals( "Test project", p.getProjectname())
                );
    }

}