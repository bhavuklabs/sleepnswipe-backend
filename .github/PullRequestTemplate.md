# Pull Request Template

Rules: 
1. The issue you are trying to fix/resolve has to have the `approved` label.

1. Put in the description of the PR the reference to an issue if it exists. Example: “Issue: #ISSUEID”
2. Commit message should adhere to the following rules: 
a. Must match one of the following patterns: `^Issue #\d+: .$ ^Pull #\d+: .$^(minor|config|infra|doc|spelling|dependency):.*$` 
    
    b. Must contain only one line of text
    
    c. Must not end with a period, space, or a tab
    
    d. Must be less than or equal to 200 characters
    

To avoid multiple iterations of fixes and CI failures, please read the Contribution Guideline.

Attention: We don’t merge PRs that are not passing our CI, but we will help to resolve the issue.