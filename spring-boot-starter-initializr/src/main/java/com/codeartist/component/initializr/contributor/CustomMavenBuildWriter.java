package com.codeartist.component.initializr.contributor;

import io.spring.initializr.generator.buildsystem.*;
import io.spring.initializr.generator.buildsystem.maven.*;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.version.VersionProperty;
import io.spring.initializr.generator.version.VersionReference;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 自定义Maven项目生成类
 *
 * @author AiJiangnan
 * @date 2023/6/2
 */
public class CustomMavenBuildWriter {

    public void writeTo(IndentingWriter writer, CustomMavenBuild build) {
        MavenBuildSettings settings = build.getSettings();
        writeProject(writer, () -> {
            writeParent(writer, build);
            writeProjectCoordinates(writer, settings);
            writePackaging(writer, settings);
            writeProjectName(writer, settings);
            writeModules(writer, build.modules());
            writeProperties(writer, build.properties());
            writeDependencies(writer, build.dependencies());
            writeDependencyManagement(writer, build.boms());
            writeBuild(writer, build);
            writeProfiles(writer, build);
        });
    }

    protected Comparator<Dependency> getDependencyComparator() {
        return DependencyComparator.INSTANCE;
    }

    private void writeProject(IndentingWriter writer, Runnable whenWritten) {
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        //noinspection SpellCheckingInspection
        writer.println(
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        writer.indented(() -> {
            writer.println(
                    "xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\">");
            writeSingleElement(writer, "modelVersion", "4.0.0");
            whenWritten.run();
        });
        writer.println();
        writer.println("</project>");
    }

    private void writeParent(IndentingWriter writer, MavenBuild build) {
        MavenParent parent = build.getSettings().getParent();
        if (parent == null) {
            return;
        }
        writer.println("<parent>");
        writer.indented(() -> {
            writeSingleElement(writer, "groupId", parent.getGroupId());
            writeSingleElement(writer, "artifactId", parent.getArtifactId());
            writeSingleElement(writer, "version", parent.getVersion());
        });
        writer.println("</parent>");
    }

    private void writeProjectCoordinates(IndentingWriter writer, MavenBuildSettings settings) {
        writeSingleElement(writer, "groupId", settings.getGroup());
        writeSingleElement(writer, "artifactId", settings.getArtifact());
        writeSingleElement(writer, "version", settings.getVersion());
    }

    private void writePackaging(IndentingWriter writer, MavenBuildSettings settings) {
        String packaging = settings.getPackaging();
        if (!"jar".equals(packaging)) {
            writeSingleElement(writer, "packaging", packaging);
            writer.println();
        }
    }

    private void writeProjectName(IndentingWriter writer, MavenBuildSettings settings) {
        writeSingleElement(writer, "name", settings.getName());
        writeSingleElement(writer, "description", settings.getDescription());
        if (settings.getName() != null) {
            writer.println();
        }
    }

    private void writeModules(IndentingWriter writer, List<String> modules) {
        if (CollectionUtils.isEmpty(modules)) {
            return;
        }
        writer.println("<modules>");
        writer.indented(() -> modules.forEach(module -> writeSingleElement(writer, "module", module)));
        writer.println("</modules>");
    }

    private void writeProperties(IndentingWriter writer, PropertyContainer properties) {
        if (properties.isEmpty()) {
            return;
        }
        writeElement(writer, "properties", () -> {
            properties.values().forEach((entry) -> writeSingleElement(writer, entry.getKey(), entry.getValue()));
            properties.versions((VersionProperty::toStandardFormat))
                    .forEach((entry) -> writeSingleElement(writer, entry.getKey(), entry.getValue()));
        });
        writer.println();
    }

    private void writeDependencies(IndentingWriter writer, DependencyContainer dependencies) {
        if (dependencies.isEmpty()) {
            return;
        }
        writeElement(writer, "dependencies", () -> {
            Collection<Dependency> compiledDependencies = writeDependencies(writer, dependencies,
                    (scope) -> scope == null || scope == DependencyScope.COMPILE);
            if (!compiledDependencies.isEmpty()) {
                writer.println();
            }
            writeDependencies(writer, dependencies, hasScope(DependencyScope.RUNTIME));
            writeDependencies(writer, dependencies, hasScope(DependencyScope.COMPILE_ONLY));
            writeDependencies(writer, dependencies, hasScope(DependencyScope.ANNOTATION_PROCESSOR));
            writeDependencies(writer, dependencies, hasScope(DependencyScope.PROVIDED_RUNTIME));
            writeDependencies(writer, dependencies, hasScope(DependencyScope.TEST_COMPILE, DependencyScope.TEST_RUNTIME));
        });
    }

    private Predicate<DependencyScope> hasScope(DependencyScope... validScopes) {
        return (scope) -> Arrays.asList(validScopes).contains(scope);
    }

    private Collection<Dependency> writeDependencies(IndentingWriter writer, DependencyContainer dependencies,
                                                     Predicate<DependencyScope> filter) {
        Collection<Dependency> candidates = dependencies.items().filter((dep) -> filter.test(dep.getScope()))
                .sorted(getDependencyComparator()).collect(Collectors.toList());
        writeCollection(writer, candidates, this::writeDependency);
        return candidates;
    }

    private void writeDependency(IndentingWriter writer, Dependency dependency) {
        writeElement(writer, "dependency", () -> {
            writeSingleElement(writer, "groupId", dependency.getGroupId());
            writeSingleElement(writer, "artifactId", dependency.getArtifactId());
            writeSingleElement(writer, "version", determineVersion(dependency.getVersion()));
            writeSingleElement(writer, "scope", scopeForType(dependency.getScope()));
            writeSingleElement(writer, "classifier", dependency.getClassifier());
            if (isOptional(dependency)) {
                writeSingleElement(writer, "optional", Boolean.toString(true));
            }
            writeSingleElement(writer, "type", dependency.getType());
            writeCollectionElement(writer, "exclusions", dependency.getExclusions(), this::writeDependencyExclusion);
        });
    }

    private void writeDependencyExclusion(IndentingWriter writer, Dependency.Exclusion exclusion) {
        writeElement(writer, "exclusion", () -> {
            writeSingleElement(writer, "groupId", exclusion.getGroupId());
            writeSingleElement(writer, "artifactId", exclusion.getArtifactId());
        });
    }

    private String scopeForType(DependencyScope type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case ANNOTATION_PROCESSOR:
            case COMPILE:
            case COMPILE_ONLY:
                return null;
            case PROVIDED_RUNTIME:
                return "provided";
            case RUNTIME:
                return "runtime";
            case TEST_COMPILE:
            case TEST_RUNTIME:
                return "test";
            default:
                throw new IllegalStateException("Unrecognized dependency type '" + type + "'");
        }
    }

    private boolean isOptional(Dependency dependency) {
        if (dependency instanceof MavenDependency && ((MavenDependency) dependency).isOptional()) {
            return true;
        }
        return (dependency.getScope() == DependencyScope.ANNOTATION_PROCESSOR
                || dependency.getScope() == DependencyScope.COMPILE_ONLY);
    }

    private void writeDependencyManagement(IndentingWriter writer, BomContainer boms) {
        if (boms.isEmpty()) {
            return;
        }
        writeElement(writer, "dependencyManagement",
                () -> writeCollectionElement(writer, "dependencies", boms.items()
                                .sorted(Comparator.comparing(BillOfMaterials::getOrder)).collect(Collectors.toList()),
                        this::writeBom));
    }

    private void writeBom(IndentingWriter writer, BillOfMaterials bom) {
        writeElement(writer, "dependency", () -> {
            writeSingleElement(writer, "groupId", bom.getGroupId());
            writeSingleElement(writer, "artifactId", bom.getArtifactId());
            writeSingleElement(writer, "version", determineVersion(bom.getVersion()));
            writeSingleElement(writer, "type", "pom");
            writeSingleElement(writer, "scope", "import");
        });
    }

    private String determineVersion(VersionReference versionReference) {
        if (versionReference == null) {
            return null;
        }
        return (versionReference.isProperty()) ? "${" + versionReference.getProperty().toStandardFormat() + "}"
                : versionReference.getValue();
    }

    private void writeBuild(IndentingWriter writer, MavenBuild build) {
        MavenBuildSettings settings = build.getSettings();
        if (settings.getDefaultGoal() == null && settings.getFinalName() == null
                && settings.getSourceDirectory() == null && settings.getTestSourceDirectory() == null
                && build.resources().isEmpty() && build.testResources().isEmpty() && build.plugins().isEmpty()) {
            return;
        }
        writer.println();
        writeElement(writer, "build", () -> {
            writeSingleElement(writer, "defaultGoal", settings.getDefaultGoal());
            writeSingleElement(writer, "finalName", settings.getFinalName());
            writeSingleElement(writer, "sourceDirectory", settings.getSourceDirectory());
            writeSingleElement(writer, "testSourceDirectory", settings.getTestSourceDirectory());
            writeResources(writer, build.resources(), build.testResources());
            writeCollectionElement(writer, "plugins", build.plugins().values(), this::writePlugin);
        });
    }

    private void writeResources(IndentingWriter writer, MavenResourceContainer resources,
                                MavenResourceContainer testResources) {
        writeCollectionElement(writer, "resources", resources.values(), this::writeResource);
        writeCollectionElement(writer, "testResources", testResources.values(), this::writeTestResource);
    }

    private void writeResource(IndentingWriter writer, MavenResource resource) {
        writeResource(writer, resource, "resource");
    }

    private void writeTestResource(IndentingWriter writer, MavenResource resource) {
        writeResource(writer, resource, "testResource");
    }

    private void writeResource(IndentingWriter writer, MavenResource resource, String resourceName) {
        writeElement(writer, resourceName, () -> {
            writeSingleElement(writer, "directory", resource.getDirectory());
            writeSingleElement(writer, "targetPath", resource.getTargetPath());
            if (resource.isFiltering()) {
                writeSingleElement(writer, "filtering", "true");
            }
            writeCollectionElement(writer, "includes", resource.getIncludes(), this::writeResourceInclude);
            writeCollectionElement(writer, "excludes", resource.getExcludes(), this::writeResourceExclude);
        });
    }

    private void writeResourceInclude(IndentingWriter writer, String include) {
        writeSingleElement(writer, "include", include);
    }

    private void writeResourceExclude(IndentingWriter writer, String exclude) {
        writeSingleElement(writer, "exclude", exclude);
    }

    private void writePlugin(IndentingWriter writer, MavenPlugin plugin) {
        writeElement(writer, "plugin", () -> {
            writeSingleElement(writer, "groupId", plugin.getGroupId());
            writeSingleElement(writer, "artifactId", plugin.getArtifactId());
            writeSingleElement(writer, "version", plugin.getVersion());
            if (plugin.isExtensions()) {
                writeSingleElement(writer, "extensions", "true");
            }
            writePluginConfiguration(writer, plugin.getConfiguration());
            writeCollectionElement(writer, "executions", plugin.getExecutions(), this::writePluginExecution);
            writeCollectionElement(writer, "dependencies", plugin.getDependencies(), this::writePluginDependency);
        });
    }

    private void writePluginConfiguration(IndentingWriter writer, MavenPlugin.Configuration configuration) {
        if (configuration == null || configuration.getSettings().isEmpty()) {
            return;
        }
        writeCollectionElement(writer, "configuration", configuration.getSettings(), this::writeSetting);
    }

    @SuppressWarnings("unchecked")
    private void writeSetting(IndentingWriter writer, MavenPlugin.Setting setting) {
        if (setting.getValue() instanceof String) {
            writeSingleElement(writer, setting.getName(), setting.getValue());
        } else if (setting.getValue() instanceof List) {
            writeCollectionElement(writer, setting.getName(), (List<MavenPlugin.Setting>) setting.getValue(), this::writeSetting);
        }
    }

    private void writePluginExecution(IndentingWriter writer, MavenPlugin.Execution execution) {
        writeElement(writer, "execution", () -> {
            writeSingleElement(writer, "id", execution.getId());
            writeSingleElement(writer, "phase", execution.getPhase());
            List<String> goals = execution.getGoals();
            if (!goals.isEmpty()) {
                writeElement(writer, "goals", () -> goals.forEach((goal) -> writeSingleElement(writer, "goal", goal)));
            }
            writePluginConfiguration(writer, execution.getConfiguration());
        });
    }

    private void writePluginDependency(IndentingWriter writer, MavenPlugin.Dependency dependency) {
        writeElement(writer, "dependency", () -> {
            writeSingleElement(writer, "groupId", dependency.getGroupId());
            writeSingleElement(writer, "artifactId", dependency.getArtifactId());
            writeSingleElement(writer, "version", dependency.getVersion());
        });
    }

    private void writeRepositories(IndentingWriter writer, MavenRepositoryContainer buildRepositories,
                                   MavenRepositoryContainer buildPluginRepositories) {
        List<MavenRepository> repositories = filterRepositories(buildRepositories.items());
        List<MavenRepository> pluginRepositories = filterRepositories(buildPluginRepositories.items());
        if (repositories.isEmpty() && pluginRepositories.isEmpty()) {
            return;
        }
        writeCollectionElement(writer, "repositories", repositories, this::writeRepository);
        writeCollectionElement(writer, "pluginRepositories", pluginRepositories, this::writePluginRepository);
    }

    private List<MavenRepository> filterRepositories(Stream<MavenRepository> repositories) {
        return repositories.filter((repository) -> !MavenRepository.MAVEN_CENTRAL.equals(repository))
                .collect(Collectors.toList());
    }

    private void writeRepository(IndentingWriter writer, MavenRepository repository) {
        writeRepository(writer, repository, "repository");
    }

    private void writePluginRepository(IndentingWriter writer, MavenRepository repository) {
        writeRepository(writer, repository, "pluginRepository");
    }

    private void writeRepository(IndentingWriter writer, MavenRepository repository, String childName) {
        writeElement(writer, childName, () -> {
            writeSingleElement(writer, "id", repository.getId());
            writeSingleElement(writer, "name", repository.getName());
            writeSingleElement(writer, "url", repository.getUrl());
            if (repository.isSnapshotsEnabled()) {
                writeElement(writer, "snapshots", () -> writeSingleElement(writer, "enabled", Boolean.toString(true)));
            }
        });
    }

    private void writeDistributionManagement(IndentingWriter writer,
                                             MavenDistributionManagement distributionManagement) {
        if (distributionManagement.isEmpty()) {
            return;
        }
        writeElement(writer, "distributionManagement", () -> {
            writeSingleElement(writer, "downloadUrl", distributionManagement.getDownloadUrl());
            writeDeploymentRepository(writer, "repository", distributionManagement.getRepository());
            writeDeploymentRepository(writer, "snapshotRepository", distributionManagement.getSnapshotRepository());
            MavenDistributionManagement.Site site = distributionManagement.getSite();
            if (!site.isEmpty()) {
                writeElement(writer, "site", () -> {
                    writeSingleElement(writer, "id", site.getId());
                    writeSingleElement(writer, "name", site.getName());
                    writeSingleElement(writer, "url", site.getUrl());
                });
            }
            MavenDistributionManagement.Relocation relocation = distributionManagement.getRelocation();
            if (!relocation.isEmpty()) {
                writeElement(writer, "relocation", () -> {
                    writeSingleElement(writer, "groupId", relocation.getGroupId());
                    writeSingleElement(writer, "artifactId", relocation.getArtifactId());
                    writeSingleElement(writer, "version", relocation.getVersion());
                    writeSingleElement(writer, "message", relocation.getMessage());
                });
            }
        });
    }

    private void writeDeploymentRepository(IndentingWriter writer, String name, MavenDistributionManagement.DeploymentRepository repository) {
        if (!repository.isEmpty()) {
            writeElement(writer, name, () -> {
                writeSingleElement(writer, "id", repository.getId());
                writeSingleElement(writer, "name", repository.getName());
                writeSingleElement(writer, "url", repository.getUrl());
                writeSingleElement(writer, "layout", repository.getLayout());
                if (repository.getUniqueVersion() != null) {
                    writeSingleElement(writer, "uniqueVersion", Boolean.toString(repository.getUniqueVersion()));
                }
            });
        }
    }

    private void writeProfiles(IndentingWriter writer, MavenBuild build) {
        MavenProfileContainer profiles = build.profiles();
        if (profiles.isEmpty()) {
            return;
        }
        writer.println();
        writeElement(writer, "profiles", () -> profiles.values().forEach((profile) -> writeProfile(writer, profile)));
    }

    private void writeProfile(IndentingWriter writer, MavenProfile profile) {
        writeElement(writer, "profile", () -> {
            writeSingleElement(writer, "id", profile.getId());
            writeProfileActivation(writer, profile.getActivation());
            writeProperties(writer, profile.properties());
            writeDependencies(writer, profile.dependencies());
            writeDependencyManagement(writer, profile.boms());
            writeProfileBuild(writer, profile);
            writeRepositories(writer, profile.repositories(), profile.pluginRepositories());
            writeDistributionManagement(writer, profile.getDistributionManagement());
        });
    }

    private void writeProfileActivation(IndentingWriter writer, MavenProfileActivation activation) {
        if (activation.isEmpty()) {
            return;
        }
        writeElement(writer, "activation", () -> {
            writeSingleElement(writer, "activeByDefault", activation.getActiveByDefault());
            writeSingleElement(writer, "jdk", activation.getJdk());
            ifNotNull(activation.getOs(), (os) -> writeElement(writer, "os", () -> {
                writeSingleElement(writer, "name", os.getName());
                writeSingleElement(writer, "arch", os.getArch());
                writeSingleElement(writer, "family", os.getFamily());
                writeSingleElement(writer, "version", os.getVersion());
            }));
            ifNotNull(activation.getProperty(), (property) -> writeElement(writer, "property", () -> {
                writeSingleElement(writer, "name", property.getName());
                writeSingleElement(writer, "value", property.getValue());
            }));
            ifNotNull(activation.getFile(), (file) -> writeElement(writer, "file", () -> {
                writeSingleElement(writer, "exists", file.getExists());
                writeSingleElement(writer, "missing", file.getMissing());
            }));
        });
    }

    private void writeProfileBuild(IndentingWriter writer, MavenProfile profile) {
        MavenProfile.Settings settings = profile.getSettings();
        if (settings.getDefaultGoal() == null && settings.getFinalName() == null && profile.resources().isEmpty()
                && profile.testResources().isEmpty() && profile.plugins().isEmpty()) {
            return;
        }
        writeElement(writer, "build", () -> {
            writeSingleElement(writer, "defaultGoal", settings.getDefaultGoal());
            writeSingleElement(writer, "finalName", settings.getFinalName());
            writeResources(writer, profile.resources(), profile.testResources());
            writeCollectionElement(writer, "plugins", profile.plugins().values(), this::writePlugin);
        });
    }

    private void writeSingleElement(IndentingWriter writer, String name, Object value) {
        if (value != null) {
            CharSequence text = (value instanceof CharSequence) ? (CharSequence) value : value.toString();
            writer.print(String.format("<%s>", name));
            writer.print(encodeText(text));
            writer.println(String.format("</%s>", name));
        }
    }

    private void writeElement(IndentingWriter writer, String name, Runnable withContent) {
        writer.println(String.format("<%s>", name));
        writer.indented(withContent);
        writer.println(String.format("</%s>", name));
    }

    private <T> void writeCollectionElement(IndentingWriter writer, String name, Stream<T> items,
                                            BiConsumer<IndentingWriter, T> itemWriter) {
        writeCollectionElement(writer, name, items.collect(Collectors.toList()), itemWriter);
    }

    private <T> void writeCollectionElement(IndentingWriter writer, String name, Collection<T> items,
                                            BiConsumer<IndentingWriter, T> itemWriter) {
        if (!ObjectUtils.isEmpty(items)) {
            writeElement(writer, name, () -> writeCollection(writer, items, itemWriter));
        }
    }

    private <T> void writeCollection(IndentingWriter writer, Collection<T> collection,
                                     BiConsumer<IndentingWriter, T> itemWriter) {
        if (!collection.isEmpty()) {
            collection.forEach((item) -> itemWriter.accept(writer, item));
        }
    }

    private <T> void ifNotNull(T value, Consumer<T> elementWriter) {
        if (value != null) {
            elementWriter.accept(value);
        }
    }

    private String encodeText(CharSequence text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            switch (character) {
                case '\'':
                    sb.append("&apos;");
                    break;
                case '\"':
                    sb.append("&quot;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                default:
                    sb.append(character);
            }
        }
        return sb.toString();
    }
}
