﻿// Copyright 2021 Maintainers of NUKE.
// Distributed under the MIT License.
// https://github.com/nuke-build/nuke/blob/master/LICENSE

using Nuke.Common.CI.GitHubActions;
using Nuke.Components;
using static Nuke.Components.IHazTwitterCredentials;
#if ENTERPRISE
using Nuke.Enterprise.Notifications;
using static Nuke.Enterprise.Notifications.IHazSlackCredentials;
#endif

[GitHubActions(
    "continuous",
    GitHubActionsImage.WindowsLatest,
    GitHubActionsImage.UbuntuLatest,
    GitHubActionsImage.MacOsLatest,
    OnPushBranchesIgnore = new[] { MasterBranch, ReleaseBranchPrefix + "/*" },
    OnPullRequestBranches = new[] { DevelopBranch },
    PublishArtifacts = false,
    InvokedTargets = new[] { nameof(ITest.Test), nameof(IPack.Pack) },
    CacheKeyFiles = new[] { "global.json", "source/**/*.csproj" },
    ImportSecrets = new[]
                    {
                        nameof(EnterpriseAccessToken),
#if ENTERPRISE
                        Slack + nameof(IHazSlackCredentials.AppAccessToken),
                        Slack + nameof(IHazSlackCredentials.UserAccessToken),
#endif
                    })]
partial class Build
{
}
