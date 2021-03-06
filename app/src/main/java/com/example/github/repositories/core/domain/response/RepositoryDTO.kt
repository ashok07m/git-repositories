package com.example.github.repositories.core.domain.response

data class RepositoryDTO(
    var id: Int? = null,
    var node_id: String? = null,
    var name: String? = null, 
    var full_name: String? = null, 
    var private: Boolean? = null,
    var owner: OwnerDTO? = null,
    var html_url: String? = null,
    var description: String? = null, 
    var fork: Boolean? = null,
    var url: String? = null,
    var forks_url: String? = null,
    var keys_url: String? = null,
    var collaborators_url: String? = null,
    var teams_url: String? = null,
    var hooks_url: String? = null,
    var issue_events_url: String? = null,
    var events_url: String? = null,
    var assignees_url: String? = null,
    var branches_url: String? = null,
    var tags_url: String? = null,
    var blobs_url: String? = null,
    var git_tags_url: String? = null,
    var git_refs_url: String? = null,
    var trees_url: String? = null,
    var statuses_url: String? = null,
    var languages_url: String? = null,
    var stargazers_url: String? = null,
    var contributors_url: String? = null,
    var subscribers_url: String? = null,
    var subscription_url: String? = null,
    var commits_url: String? = null,
    var git_commits_url: String? = null,
    var comments_url: String? = null,
    var issue_comment_url: String? = null,
    var contents_url: String? = null,
    var compare_url: String? = null,
    var merges_url: String? = null,
    var archive_url: String? = null,
    var downloads_url: String? = null,
    var issues_url: String? = null,
    var pulls_url: String? = null,
    var milestones_url: String? = null,
    var notifications_url: String? = null,
    var labels_url: String? = null,
    var releases_url: String? = null,
    var deployments_url: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var pushed_at: String? = null,
    var git_url: String? = null,
    var ssh_url: String? = null,
    var clone_url: String? = null,
    var svn_url: String? = null,
    var homepage: String? = null,
    var size: Int? = null,
    var stargazers_count: Int? = null,
    var watchers_count: Int? = null,
    var language: String? = null,
    var has_issues: Boolean? = null,
    var has_projects: Boolean? = null,
    var has_downloads: Boolean? = null,
    var has_wiki: Boolean? = null,
    var has_pages: Boolean? = null,
    var forks_count: Int? = null,
    var mirror_url: String? = null,
    var archived: Boolean? = null,
    var disabled: Boolean? = null,
    var open_issues_count: Int? = null,
    var license: LicenseDTO? = null,
    var allow_forking: Boolean? = null,
    var is_template: Boolean? = null,
    var topics: ArrayList<String> = arrayListOf(),
    var visibility: String? = null,
    var forks: Int? = null,
    var open_issues: Int? = null,
    var watchers: Int? = null,
    var default_branch: String? = null,
    var score: Int? = null
)
