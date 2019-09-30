plugins {
    id("com.neva.fork")
    id("com.cognifide.aem.common")
}

apply(from = "gradle/common.gradle.kts")
apply(from = "gradle/fork.gradle.kts")

description = "Example"
defaultTasks("develop")

aem {
    tasks {
        sequence("develop", {
            description = "Builds and deploys AEM application to instances, cleans environment then runs all tests"
        }) {
            dependsOn(
                    ":aem:instanceSatisfy",
                    ":aem:instanceProvision",
                    ":aem:assembly:full:packageDeploy",
                    ":aem:migration:packageDeploy",
                    ":aem:environmentClean",
                    ":aem:environmentAwait",
                    ":test:integration:test",
                    ":test:functional:run",
                    ":test:performance:lighthouseRun"
            )
        }
    }
}
