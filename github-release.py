#!/usr/bin/python

import os
import sys

oauth_token = sys.argv[1]
version = ""

java_gradle_build_file = open("mal-tournament-java/build.gradle", 'r')
java_gradle_build = java_gradle_build_file.read().split("\n")
java_gradle_build_file.close()

for line in java_gradle_build:
    if line.startswith("version = \""):
        version = line.split("version = \"")[1].split("\"")[0]
        break

create_repo_command = "curl -X POST https://api.github.com/repos/namboy94/mal-tournament/releases?access_token="
create_repo_command += oauth_token
create_repo_command += " -d '{\"tag_name\": \"" + version + "\", \"target_commitish\": \"master\", "
create_repo_command += "\"name\":\"" + version + "\", \"body\": \"Automatic Release Build\", \"draft\": false, \"prerelease\": false}'"

response = os.popen(create_repo_command).read()
tag_id = response.split("\"id\": ")[1].split(",")[0]

jarfilename = "mal-tournament-java-" + version + ".jar"
jarfile = "mal-tournament-java/build/libs/" + jarfilename

uploaded_binary_command = "curl -X POST --header \"Content-Type:application/java-archive\" --data-binary @" + jarfile


uploaded_binary_command += " 'https://uploads.github.com/repos/namboy94/mal-tournament/releases/" + tag_id + "/assets?name=" + jarfilename
uploaded_binary_command += "&access_token=" + oauth_token + "'"

os.popen(uploaded_binary_command)
