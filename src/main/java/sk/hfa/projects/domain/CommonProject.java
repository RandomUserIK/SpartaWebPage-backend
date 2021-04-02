package sk.hfa.projects.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sk.hfa.projects.domain.enums.Category;
import sk.hfa.projects.services.ProjectService;
import sk.hfa.projects.web.domain.requestbodies.CommonProjectRequest;
import sk.hfa.projects.web.domain.requestbodies.ProjectRequest;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CommonProject extends Project {

    private boolean common;

    public static Project build(ProjectRequest request) {
        return CommonProject.builder()
                .id(request.getId())
                .title(request.getTitle())
                .titleImage(request.getTitleImage())
                .category(Category.COMMON)
                .textSections(request.getTextSections())
                .imagePaths(request.getImagePaths())
                .hasGarage(request.getHasGarage())
                .persons(request.getPersons())
                .rooms(request.getRooms())
                .energeticClass(request.getEnergeticClass())
                .entryOrientation(request.getEntryOrientation())
                .heatingSource(request.getHeatingSource())
                .heatingType(request.getHeatingType())
                .floorPlanImage(request.getFloorPlanImage())
                .builtUpArea(request.getBuiltUpArea())
                .usableArea(request.getUsableArea())
                .selfHelpBuildPrice(request.getSelfHelpBuildPrice())
                .onKeyPrice(request.getOnKeyPrice())
                .basicProjectPrice(request.getBasicProjectPrice())
                .extendedProjectPrice(request.getExtendedProjectPrice())
                .totalLivingArea(request.getTotalLivingArea())
                .roofPitch(request.getRoofPitch())
                .minimumParcelWidth(request.getMinimumParcelWidth())
                .common(((CommonProjectRequest) request).isCommon())
                .build();
    }

}