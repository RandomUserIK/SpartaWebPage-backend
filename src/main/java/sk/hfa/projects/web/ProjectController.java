package sk.hfa.projects.web;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.hfa.projects.domain.Project;
import sk.hfa.projects.services.interfaces.IProjectService;
import sk.hfa.projects.web.domain.requestbodies.ProjectRequest;
import sk.hfa.projects.web.domain.responsebodies.ProjectMessageResource;
import sk.hfa.projects.web.domain.responsebodies.ProjectPageMessageResource;
import sk.hfa.web.domain.responsebodies.MessageResource;

@Slf4j
@RestController
@RequestMapping(path = "/api/projects")
public class ProjectController {

    private final IProjectService projectService;

    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource> createProject(@RequestBody ProjectRequest request) {
        log.info("Creating a new project.");
        Project project = projectService.build(request);
        project = projectService.save(project);
        MessageResource responseBody = new ProjectMessageResource(project);
        log.info("The project with the ID: [" + project.getId() + "] was successfully created.");
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource> getProject(@PathVariable long id) {
        log.info("Fetching the project with the ID: " + id);
        Project project = projectService.findById(id);
        MessageResource responseBody = new ProjectMessageResource(project);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource> getAllOnPage(@RequestParam("page") int page,
                                                        @RequestParam("size") int size,
                                                        @QuerydslPredicate(root = Project.class) Predicate predicate) {
        log.info("Fetching projects on the page [" + page + "]");
        Page<Project> result = projectService.getAllOnPage(page, size, predicate);
        MessageResource responseBody = ProjectPageMessageResource.build(result);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping(path = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource> getAllOnPageAndQuery(@RequestParam("page") int page,
                                                                @QuerydslPredicate(root = Project.class) Predicate predicate) {
        log.info("Fetching projects on the page [" + page + "] and filtering on custom query [" + predicate.toString() + "].");
        Page<Project> result = projectService.getAllOnPageAndQuery(page, predicate);
        MessageResource responseBody = ProjectPageMessageResource.build(result);
        return ResponseEntity.ok(responseBody);
    }

}
