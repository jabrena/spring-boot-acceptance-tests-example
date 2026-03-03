package info.jab.ms;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "info.jab.ms")
class ArchitectureTest {

    @ArchTest
    static final ArchRule services_should_not_depend_on_controllers =
        noClasses()
            .that().resideInAPackage("..service..")
            .should().dependOnClassesThat()
            .resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule layered_architecture_is_respected =
        layeredArchitecture()
            .consideringAllDependencies()
            .layer("Controller").definedBy("..controller..")
            .layer("Service").definedBy("..service..")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller");
}
