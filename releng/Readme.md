Setting new version
===================

export RELEASE_VERSION=x.y.z
git clean -df
mvn org.eclipse.tycho:tycho-versions-plugin:set-version -Dproperties=recommendersVersion -DnewVersion=${RELEASE_VERSION}
mvn org.eclipse.tycho:tycho-versions-plugin:set-version -Dartifacts=$(basename plugins/*/ tests/*/ features/*/ | paste -sd "," - ) -DnewVersion=${RELEASE_VERSION}-SNAPSHOT
mvn tidy:pom
git commit -a -m "[releng] ${RELEASE_VERSION}"

