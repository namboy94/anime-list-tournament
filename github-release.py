#!/usr/bin/python

import os
import sys
from subprocess import check_output, Popen

# Change project specific stuff here
root_gradle_file = "build.gradle"
repoowner = "namboy94"
reponame = "mal-tournament"

release_assets = [{"filename_pre_version": "mal-tournament-java-",
                   "filename_post_version": ".jar",
                   "filepath": "mal-tournament-java/build/libs/",
                   "content_type": "application/java-archive"},
                  {"filename_pre_version": "mal-tournament-lib-",
                   "filename_post_version": ".jar",
                   "filepath": "mal-tournament-lib/build/libs/",
                   "content_type": "application/java-archive"},
                  {"filename_pre_version": "mal-tournament-android-release-",
                   "filename_post_version": ".apk",
                   "filepath": "mal-tournament-android/build/outputs/apk/",
                   "content_type": "application/vnd.android.package-archive"}]

# Don't edit past here!


oauth_token = sys.argv[1]
version = ""

repopath = "repos/" + repoowner + "/" + reponame + "/releases"
api_repo_url = "https://api.github.com/" + repopath
upload_repo_url = "https://upload.github.com/" + repopath
oauth_param = "access_token=" + oauth_token

with open(root_gradle_file, 'r') as gradlefile:
    for line in gradlefile.read().split("\n"):
        if line.startswith("version = \""):
            version = line.split("version = \"")[1].split("\"")[0]

create_release = ["curl",
                  "-X",
                  "POST",
                  api_repo_url + "?" + oauth_param,
                  "-d",
                  "{\"tag_name\": \"" + version + "\"," +
                  " \"target_commitish\": \"master\"," +
                  "\"name\":\"" + version + "\"," +
                  "\"body\": \"Automatic Release Build\"," +
                  "\"draft\": false," +
                  "\"prerelease\": false}"]

response = check_output(create_release)
tag_id = response.split("\"id\": ")[1].split(",")[0]


for asset in release_assets:
    filename = asset["filename_pre_version"] + version + asset["filename_post_version"]
    filepath = asset["filepath"] + filename
    content_type = asset["content_type"]

    upload_binary = ["curl",
                     "-X",
                     "POST",
                     "--header",
                     "\"Content-Type:" + content_type + "\"",
                     "--data-binary",
                     "@" + filepath,
                     upload_repo_url + "/" + tag_id + "/assets?name=" + filename + "&" + oauth_param]

    Popen(upload_binary).wait()
