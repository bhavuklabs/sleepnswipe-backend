# Contribution Guidelines

This guide serves to set clear expectations for everyone involved with the project so as to improve it together while also creating a welcoming space for everyone to participate. Make sure you follow these guidelines in order to ensure a positive experience for contributors and maintainers alike.

## Contents

- [Code of Conduct](Contribution%20Guidelines%20165cc94ba8f2804180ffff5f3f0e2e62.md)
- [Getting Started](Contribution%20Guidelines%20165cc94ba8f2804180ffff5f3f0e2e62.md)
- [Opening an Issue](Contribution%20Guidelines%20165cc94ba8f2804180ffff5f3f0e2e62.md)
- [Reporting Security Issues](Contribution%20Guidelines%20165cc94ba8f2804180ffff5f3f0e2e62.md)
- [Submitting Pull Requests](Contribution%20Guidelines%20165cc94ba8f2804180ffff5f3f0e2e62.md)
- [Code Review](Contribution%20Guidelines%20165cc94ba8f2804180ffff5f3f0e2e62.md)
- [Asking Questions](Contribution%20Guidelines%20165cc94ba8f2804180ffff5f3f0e2e62.md)
- [Credits](Contribution%20Guidelines%20165cc94ba8f2804180ffff5f3f0e2e62.md)

## Code of Conduct

This project and everyone participating in it is governed by the Grunfeld [Project Code of Conduct](Contributor%20Covenant%20Code%20of%20Conduct%20165cc94ba8f28015bce0d91b0737da6f.md)

## Getting Started

- Please see the documentation for information on how to get started with any project. This includes setting up your development environment, building the project and running tests.
- Take a look at the deployed contribution guideline for each project for on how to contribute to the project.
- Select an issue to work on from the `Issues` page. We always have a few issues labeled as `good first issue` to help you get started. Once you get your first PR merged, you can move on to take `good second issue`, `good third issue` and so on before taking up some `bug-fixes` or `feature` . Always make sure that the issue you select has the `approved` label.
- When you decide which issue you would like to work upon, please comment on the issue to let others know that you’re working on it (”I am working on this one”). It is completely okay to change a mind, please try to remove comment. If you see such comment created long time ago but issue is still open and no pull is created, please feel free to make a comment that you will try to do it.

## Submitting Pull Requests

- Read our [pull request rules.](Pull%20Request%20Template%20165cc94ba8f28060bfbbd4df6ef491f6.md)
- **Comment on the issue.** When you decide which issue you would like to work upon, please comment on the issue to let others know that you’re working on it (”I am working on this one”). It is completely okay to change a mind, please try to remove comment. If you see such comment created long time ago but issue is still open and no pull is created, please feel free to make a comment that you will try to do it.
- **Read the Github docs.** Visit Github’s [Pull Request Guide](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/about-pull-requests) for information on how to submit a pull request.
- **Follow the template**. Please follow the *GrunfeldProject Pull Request Template* that is provided in the pull request description when submitting a pull request.
- **Run build tools locally**. If you’re using a build tool like Maven, then before submitting a PR, please ensure that your project should pass `mvn clean verify` locally.
- **Keep the PR small.** If you’re working on a large feature, consider breaking it up into smaller PRs that can be reviewed and merged independently. This makes it easier for reviewers to understand the changes and for maintainers to merge the PR.

## Code Review

All submissions, including the submissions by project members, require review. We use Github pull requests for this purpose. Consult the Github Help for more information on pull request reviews or join our discord community for discussions.

Here are some general guidelines to follow while submitting a PR: 

- **Reply to Comments:** IF a reviewer asks for changes, reply to each and every comment with discussion or follow up questions, or let the reviewer know that you have addressed their concerns.
- **Be patient.** Reviewing PRs take time. If a reviewer hasn’t responded in a week or so, feel free to ping them. If you’re a reviewer and you need more time to review a PR, please let the contributor know.
- **Be kind.** Remember that everyone involved in the project is a human. Be kind and respectful in your comments and reviews.
- **Be open to feedback.** If a reviewer asks for changes, be open to their feedback. Remember that the goal is to improve the project, and feedback is an important part of that process.

## Opening an Issue

A great way to contribute to the project is to create a detailed issue when you encounter a problem or would like to suggest a feature. We always appreciate a well-written, thorough issue description.

Some points to consider when opening an issue: 

- Make sure you are using the latest version of the project. Before opening an issue, check if you are using the latest version of the project, found here. If you are not up-to-date, check to see if updating to the latest release fixes your issue.
- Do not open a duplicate feature request. Search for existing feature requests first. If you find your feature(or something similar) previously requested, comment on that issue.
- Fully complete the provided issue template. The [bug report](Bug%20Report%20165cc94ba8f2805db9f4d8a0f87578cd.md) and feature request templates specify all the information we need to quickly and efficiently address your issue. Be clear, concise and descriptive. Provide as much information as you can, including steps to reproduce and stack traces.
- Use [Github Markdown](https://help.github.com/en/github/writing-on-github/basic-writing-and-formatting-syntax). Especially, put code blocks and console outputs in backticks (```). This improves readability.

## Reporting Security Issues

**Do not file a public issue for security vulnerabilities.** Please contact the maintainers directly. See the Security Policy for more information.

## Asking Questions

See our Discussions Page. In short, Github issues are not the appropriate place to debug your specific project, but should be reserved for filing bugs and feature requests. You can also visit our Discord community help channels in order to report the issue.

## Credits

*This document was inspired by work from the following communities:*

- [CocoaPods](https://github.com/CocoaPods/CocoaPods/blob/master/CONTRIBUTING.md)
- [Docker](https://github.com/moby/moby/blob/master/CONTRIBUTING.md)
- [CheckStyle](https://github.com/checkstyle/checkstyle/blob/master/.github/CONTRIBUTING.md)

[Pull Request Template](.github/PullRequestTemplate.md)

[Contributor Covenant Code of Conduct](.github/ContributorCovenantCodeofConduct.md)

[Bug Report](.github/BugReport.md)