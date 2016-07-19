#!/usr/bin/python

import os
import sys

# Set project specific values here

gradle_root_file = "build.gradle"
apk_output_dir = "mal-tournament-android/build/outputs/apk/"
apk_name_pre_version = "mal-tournament-android-release-"
analyticsfile = "mal-tournament-android/src/main/java/net/namibsun/maltourn/android/activities/AnalyticsActivity.java"

# Don't change anything below this!

version = ""
with open(gradle_root_file, 'r') as gradlefile:
    for line in gradlefile.read().split("\n"):
        if line.startswith("    version = \""):
            version = line.split("version = \"")[1].split("\"")[0]

apk_file = apk_output_dir + apk_name_pre_version + version + ".apk"
apk_temp_file = apk_file + ".temp"
apk_noanalytics_file = apk_output_dir + apk_name_pre_version + "noanalytics-" + version + ".apk"

if sys.argv[1] == "stash":
    os.rename(apk_file, apk_temp_file)

elif sys.argv[1] == "deactivate":
    with open(analyticsfile, 'r') as analytics:
        content = analytics.read()
    content = content.replace("protected boolean analyticsActive = true;", "protected boolean analyticsActive = false;")
    with open(analyticsfile, 'w') as analytics:
        analytics.write(content)

elif sys.argv[1] == "restore":
    os.rename(apk_file, apk_noanalytics_file)
    os.rename(apk_temp_file, apk_file)
    with open(analyticsfile, 'r') as analytics:
        content = analytics.read()
    content = content.replace("protected boolean analyticsActive = false;", "protected boolean analyticsActive = true;")
    with open(analyticsfile, 'w') as analytics:
        analytics.write(content)