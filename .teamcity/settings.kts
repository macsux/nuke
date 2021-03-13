// ------------------------------------------------------------------------------
// <auto-generated>
//
//     This code was generated.
//
//     - To turn off auto-generation set:
//
//         [TeamCity (AutoGenerate = false)]
//
//     - To trigger manual generation invoke:
//
//         nuke --generate-configuration TeamCity --host TeamCity
//
// </auto-generated>
// ------------------------------------------------------------------------------

import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.buildFeatures.*
import jetbrains.buildServer.configs.kotlin.v2018_1.buildSteps.*
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.*
import jetbrains.buildServer.configs.kotlin.v2018_1.vcs.*

version = "2020.1"

project {
    buildType(Compile)
    buildType(Pack)
    buildType(Test_P1T2)
    buildType(Test_P2T2)
    buildType(Test)
    buildType(ReportDuplicates)
    buildType(ReportIssues)
    buildType(ReportCoverage)

    buildTypesOrder = arrayListOf(Compile, Pack, Test_P1T2, Test_P2T2, Test, ReportDuplicates, ReportIssues, ReportCoverage)

    params {
        checkbox (
            "env.AutoStash",
            label = "AutoStash",
            value = "True",
            checked = "True",
            unchecked = "False",
            display = ParameterDisplay.NORMAL)
        password (
            "env.AzurePipelinesAccessToken",
            label = "AzurePipelinesAccessToken",
            value = "",
            display = ParameterDisplay.NORMAL)
        password (
            "env.CodecovToken",
            label = "CodecovToken",
            value = "",
            display = ParameterDisplay.NORMAL)
        select (
            "env.Configuration",
            label = "Configuration",
            value = "Release",
            options = listOf("Debug" to "Debug", "Release" to "Release"),
            display = ParameterDisplay.NORMAL)
        text (
            "env.EnterpriseAccessToken",
            label = "EnterpriseAccessToken",
            value = "",
            allowEmpty = true,
            display = ParameterDisplay.NORMAL)
        text (
            "env.GitHubRegistryApiKey",
            label = "GitHubRegistryApiKey",
            value = "",
            allowEmpty = true,
            display = ParameterDisplay.NORMAL)
        password (
            "env.GitHubToken",
            label = "GitHubToken",
            value = "",
            display = ParameterDisplay.NORMAL)
        password (
            "env.GitterAuthToken",
            label = "GitterAuthToken",
            value = "",
            display = ParameterDisplay.NORMAL)
        checkbox (
            "env.IgnoreFailedSources",
            label = "IgnoreFailedSources",
            description = "Ignore unreachable sources during Restore",
            value = "False",
            checked = "True",
            unchecked = "False",
            display = ParameterDisplay.NORMAL)
        text (
            "env.PublicNuGetApiKey",
            label = "PublicNuGetApiKey",
            value = "",
            allowEmpty = true,
            display = ParameterDisplay.NORMAL)
        password (
            "env.SignPathApiToken",
            label = "SignPathApiToken",
            value = "",
            display = ParameterDisplay.NORMAL)
        text (
            "env.SignPathOrganizationId",
            label = "SignPathOrganizationId",
            value = "",
            allowEmpty = true,
            display = ParameterDisplay.NORMAL)
        text (
            "env.SignPathPolicySlug",
            label = "SignPathPolicySlug",
            value = "",
            allowEmpty = true,
            display = ParameterDisplay.NORMAL)
        text (
            "env.SignPathProjectSlug",
            label = "SignPathProjectSlug",
            value = "",
            allowEmpty = true,
            display = ParameterDisplay.NORMAL)
        password (
            "env.SlackAppAccessToken",
            label = "SlackAppAccessToken",
            value = "",
            display = ParameterDisplay.NORMAL)
        password (
            "env.SlackUserAccessToken",
            label = "SlackUserAccessToken",
            value = "",
            display = ParameterDisplay.NORMAL)
        password (
            "env.SlackWebhook",
            label = "SlackWebhook",
            value = "",
            display = ParameterDisplay.NORMAL)
        password (
            "env.TwitterAccessToken",
            label = "TwitterAccessToken",
            value = "",
            display = ParameterDisplay.NORMAL)
        password (
            "env.TwitterAccessTokenSecret",
            label = "TwitterAccessTokenSecret",
            value = "",
            display = ParameterDisplay.NORMAL)
        password (
            "env.TwitterConsumerKey",
            label = "TwitterConsumerKey",
            value = "",
            display = ParameterDisplay.NORMAL)
        password (
            "env.TwitterConsumerSecret",
            label = "TwitterConsumerSecret",
            value = "",
            display = ParameterDisplay.NORMAL)
        checkbox (
            "env.UseHttps",
            label = "UseHttps",
            value = "False",
            checked = "True",
            unchecked = "False",
            display = ParameterDisplay.NORMAL)
        select (
            "env.Verbosity",
            label = "Verbosity",
            description = "Logging verbosity during build execution. Default is 'Normal'.",
            value = "Normal",
            options = listOf("Minimal" to "Minimal", "Normal" to "Normal", "Quiet" to "Quiet", "Verbose" to "Verbose"),
            display = ParameterDisplay.NORMAL)
        text(
            "teamcity.runner.commandline.stdstreams.encoding",
            "UTF-8",
            display = ParameterDisplay.HIDDEN
        )
    }
}
object Compile : BuildType({
    name = "⚙️ Compile"
    vcs {
        root(DslContext.settingsRoot)
        cleanCheckout = true
    }
    steps {
        exec {
            path = "build.cmd"
            arguments = "Restore Compile --skip"
            conditions { contains("teamcity.agent.jvm.os.name", "Windows") }
        }
        exec {
            path = "build.sh"
            arguments = "Restore Compile --skip"
            conditions { doesNotContain("teamcity.agent.jvm.os.name", "Windows") }
        }
    }
    params {
        text(
            "teamcity.ui.runButton.caption",
            "Compile",
            display = ParameterDisplay.HIDDEN
        )
    }
})
object Pack : BuildType({
    name = "📦 Pack"
    vcs {
        root(DslContext.settingsRoot)
        cleanCheckout = true
    }
    artifactRules = "output/packages/*.nupkg => output/packages"
    steps {
        exec {
            path = "build.cmd"
            arguments = "Pack --skip"
            conditions { contains("teamcity.agent.jvm.os.name", "Windows") }
        }
        exec {
            path = "build.sh"
            arguments = "Pack --skip"
            conditions { doesNotContain("teamcity.agent.jvm.os.name", "Windows") }
        }
    }
    params {
        text(
            "teamcity.ui.runButton.caption",
            "Pack",
            display = ParameterDisplay.HIDDEN
        )
    }
    triggers {
        vcs {
            triggerRules = "+:**"
        }
    }
    dependencies {
        snapshot(Compile) {
            onDependencyFailure = FailureAction.FAIL_TO_START
            onDependencyCancel = FailureAction.CANCEL
        }
    }
})
object Test_P1T2 : BuildType({
    name = "🚦 Test 🧩 1/2"
    vcs {
        root(DslContext.settingsRoot)
        cleanCheckout = true
    }
    artifactRules = """
        output/test-results/*.trx => output/test-results
        output/test-results/*.xml => output/test-results
    """.trimIndent()
    steps {
        exec {
            path = "build.cmd"
            arguments = "Test --skip --test-partition 1"
            conditions { contains("teamcity.agent.jvm.os.name", "Windows") }
        }
        exec {
            path = "build.sh"
            arguments = "Test --skip --test-partition 1"
            conditions { doesNotContain("teamcity.agent.jvm.os.name", "Windows") }
        }
    }
    dependencies {
        snapshot(Compile) {
            onDependencyFailure = FailureAction.FAIL_TO_START
            onDependencyCancel = FailureAction.CANCEL
        }
    }
})
object Test_P2T2 : BuildType({
    name = "🚦 Test 🧩 2/2"
    vcs {
        root(DslContext.settingsRoot)
        cleanCheckout = true
    }
    artifactRules = """
        output/test-results/*.trx => output/test-results
        output/test-results/*.xml => output/test-results
    """.trimIndent()
    steps {
        exec {
            path = "build.cmd"
            arguments = "Test --skip --test-partition 2"
            conditions { contains("teamcity.agent.jvm.os.name", "Windows") }
        }
        exec {
            path = "build.sh"
            arguments = "Test --skip --test-partition 2"
            conditions { doesNotContain("teamcity.agent.jvm.os.name", "Windows") }
        }
    }
    dependencies {
        snapshot(Compile) {
            onDependencyFailure = FailureAction.FAIL_TO_START
            onDependencyCancel = FailureAction.CANCEL
        }
    }
})
object Test : BuildType({
    name = "🚦 Test"
    type = Type.COMPOSITE
    vcs {
        root(DslContext.settingsRoot)
        cleanCheckout = true
        showDependenciesChanges = true
    }
    artifactRules = "**/*"
    params {
        text(
            "teamcity.ui.runButton.caption",
            "Test",
            display = ParameterDisplay.HIDDEN
        )
    }
    triggers {
        vcs {
            triggerRules = "+:**"
        }
    }
    dependencies {
        snapshot(Test_P1T2) {
            onDependencyFailure = FailureAction.ADD_PROBLEM
            onDependencyCancel = FailureAction.CANCEL
        }
        snapshot(Test_P2T2) {
            onDependencyFailure = FailureAction.ADD_PROBLEM
            onDependencyCancel = FailureAction.CANCEL
        }
        artifacts(Test_P1T2) {
            artifactRules = "**/*"
        }
        artifacts(Test_P2T2) {
            artifactRules = "**/*"
        }
    }
})
object ReportDuplicates : BuildType({
    name = "🎭 ReportDuplicates"
    vcs {
        root(DslContext.settingsRoot)
        cleanCheckout = true
    }
    steps {
        exec {
            path = "build.cmd"
            arguments = "ReportDuplicates --skip"
            conditions { contains("teamcity.agent.jvm.os.name", "Windows") }
        }
        exec {
            path = "build.sh"
            arguments = "ReportDuplicates --skip"
            conditions { doesNotContain("teamcity.agent.jvm.os.name", "Windows") }
        }
    }
    params {
        text(
            "teamcity.ui.runButton.caption",
            "Report Duplicates",
            display = ParameterDisplay.HIDDEN
        )
    }
    triggers {
        vcs {
            triggerRules = "+:**"
        }
    }
})
object ReportIssues : BuildType({
    name = "💣 ReportIssues"
    vcs {
        root(DslContext.settingsRoot)
        cleanCheckout = true
    }
    steps {
        exec {
            path = "build.cmd"
            arguments = "Restore ReportIssues --skip"
            conditions { contains("teamcity.agent.jvm.os.name", "Windows") }
        }
        exec {
            path = "build.sh"
            arguments = "Restore ReportIssues --skip"
            conditions { doesNotContain("teamcity.agent.jvm.os.name", "Windows") }
        }
    }
    params {
        text(
            "teamcity.ui.runButton.caption",
            "Report Issues",
            display = ParameterDisplay.HIDDEN
        )
    }
    triggers {
        vcs {
            triggerRules = "+:**"
        }
    }
})
object ReportCoverage : BuildType({
    name = "📊 ReportCoverage"
    vcs {
        root(DslContext.settingsRoot)
        cleanCheckout = true
    }
    artifactRules = "output/reports/coverage-report.zip => output/reports"
    steps {
        exec {
            path = "build.cmd"
            arguments = "ReportCoverage --skip"
            conditions { contains("teamcity.agent.jvm.os.name", "Windows") }
        }
        exec {
            path = "build.sh"
            arguments = "ReportCoverage --skip"
            conditions { doesNotContain("teamcity.agent.jvm.os.name", "Windows") }
        }
    }
    params {
        text(
            "teamcity.ui.runButton.caption",
            "Report Coverage",
            display = ParameterDisplay.HIDDEN
        )
    }
    triggers {
        vcs {
            triggerRules = "+:**"
        }
    }
    dependencies {
        snapshot(Test) {
            onDependencyFailure = FailureAction.FAIL_TO_START
            onDependencyCancel = FailureAction.CANCEL
        }
        artifacts(Test) {
            artifactRules = """
                output/test-results/*.trx => output/test-results
                output/test-results/*.xml => output/test-results
            """.trimIndent()
        }
    }
})
