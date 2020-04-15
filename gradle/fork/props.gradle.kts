import com.cognifide.gradle.aem.common.instance.local.Source
import com.neva.gradle.fork.ForkExtension

configure<ForkExtension> {
    properties {
        define("Build", mapOf(
                "webpackMode" to {
                    label = "Webpack Mode"
                    description = "Controls optimization of front-end resources (CSS/JS/assets) "
                    select("dev", "prod")
                },
                "testBrowser" to {
                    label = "Test Browser"
                    description = "Browser used when running functional tests powered by Cypress"
                    select("auto", "chrome", "chrome:canary", "chromium", "electron", "edge", "edge:canary", "firefox", "firefox:nightly")
                }
        ))

        define("Authorization", mapOf(
                "companyUser" to {
                    label = "User"
                    description = "Authorized to access AEM files"
                    defaultValue = System.getProperty("user.name").orEmpty()
                    optional()
                },
                "companyPassword" to {
                    label = "Password"
                    description = "For above user"
                    optional()
                },
                "companyDomain" to {
                    label = "Domain"
                    description = "Needed only when accessing AEM files over SMB"
                    defaultValue = System.getenv("USERDOMAIN").orEmpty()
                    optional()
                }
        ))

        define("Instance", mapOf(
                "instanceType" to {
                    label = "Type"
                    select("local", "remote")
                    description = "Local - instance will be created on local file system\nRemote - connecting to remote instance only"
                    controller { toggle(value == "local", "instanceRunModes", "instanceJvmOpts", "localInstance*") }
                },
                "instanceAuthorHttpUrl" to {
                    label = "Author HTTP URL"
                    url("http://localhost:4502")
                    optional()
                    description = "For accessing AEM author instance (leave empty to skip creating it)"
                },
                "instanceAuthorOnly" to {
                    label = "Author Only"
                    description = "Limits instances to work with to author instance only."
                    checkbox(false)
                },
                "instancePublishHttpUrl" to {
                    label = "Publish HTTP URL"
                    url("http://localhost:4503")
                    optional()
                    description = "For accessing AEM publish instance (leave empty to skip creating it)"
                },
                "instancePublishOnly" to {
                    label = "Publish Only"
                    description = "Limits instances to work with to publish instance only."
                    checkbox(false)
                }
        ))

        define("Local instance", mapOf(
                "localInstanceSource" to {
                    label = "Source"
                    description = "Controls how instances will be created (from scratch, backup or any available source)"
                    select(Source.values().map { it.name.toLowerCase() }, Source.AUTO.name.toLowerCase())
                },
                "localInstanceQuickstartJarUri" to {
                    label = "Quickstart URI"
                    description = "For file named 'cq-quickstart-x.x.x.jar'"
                },
                "localInstanceQuickstartLicenseUri" to {
                    label = "Quickstart License URI"
                    description = "For file named 'license.properties'"
                },
                "localInstanceBackupDownloadUri" to {
                    label = "Backup Download URI"
                    description = "For backup file. Protocols supported: SMB/SFTP/HTTP"
                    optional()
                },
                "localInstanceBackupUploadUri" to {
                    label = "Backup Upload URI"
                    description = "For directory containing backup files. Protocols supported: SMB/SFTP"
                    optional()
                },
                "instanceRunModes" to {
                    label = "Run Modes"
                    text("local")
                },
                "instanceJvmOpts" to {
                    label = "JVM Options"
                    text("-server -Xmx2048m -XX:MaxPermSize=512M -Djava.awt.headless=true")
                }
        ))

        define("Other", mapOf(
                "instanceSatisfierEnabled" to {
                    label = "Instance Satisfier Enabled"
                    description = "Turns on/off automated package pre-installation."
                    checkbox(true)
                },
                "instanceProvisionerEnabled" to {
                    label = "Instance Provisioner Enabled"
                    description = "Turns on/off automated instance configuration."
                    checkbox(true)
                },
                "packageValidatorEnabled" to {
                    label = "Package Validator Enabled"
                    description = "Turns on/off package validation using OakPAL."
                    checkbox(true)
                },
                "packageDamAssetToggle" to {
                    label = "Package Deploy Without DAM Worklows"
                    description = "Turns on/off temporary disablement of assets processing for package deployment time.\n" +
                            "Useful to avoid redundant rendition generation when package contains renditions synchronized earlier."
                    checkbox(true)
                },

                "notifierEnabled" to {
                    label = "Notifications"
                    description = "Controls displaying of GUI notifications (baloons)"
                    checkbox(true)
                }
        ))
    }
}
