#!/bin/bash
#DIR="$(dirname "${BASH_SOURCE[0]}")"
echo ":::::::::::::: START SCRIPT :::::::::::::::::"
#cd /opt/atlassian/pipelines/agent/build/
echo "Finding all files under directory first........"


echo "DEV_ACCOUNT::" ${DEV_ACCOUNT}
echo "API_HOST::" ${API_HOST}
echo "RETAILER_PASSWORD::" ${RETAILER_PASSWORD}
echo "PLUGIN_NAME::" ${PLUGIN_NAME}
echo "RETAILER_USERNAME::" ${RETAILER_USERNAME}
echo "CLIENT_SECRET::" ${CLIENT_SECRET}
echo "GIT_EMAIL::" ${GIT_EMAIL}
echo "GIT_USERNAME::" ${GIT_USERNAME}
echo "GIT_ACCESS_TOKEN::" ${GIT_ACCESS_TOKEN}

# ######################
# FUNCTION DEFINITIONS
# ######################
function abort_on_failure () {
 if [[ ${1} ]];then
 echo "${1}. Aborting."
 fi
exit 1
}
function get_token () {
 echo "${!API_HOST}/oauth/token?username=${!RETAILER_USERNAME}&password=${!RETAILER_PASSWORD}&scope=api&client_id=${!DEV_ACCOUNT}&client_secret=${!CLIENT_SECRET}&grant_type=password"
token=$(\
  curl -s -XPOST \
  -H "Cache-Control: no-cache" \
  -H "fluent.account: ${ACCOUNT}" \
  "${!API_HOST}/oauth/token?username=${!RETAILER_USERNAME}&password=${!RETAILER_PASSWORD}&scope=api&client_id=${!DEV_ACCOUNT}&client_secret=${!CLIENT_SECRET}&grant_type=password" \
      | jq ".access_token")
token=$(sed -e 's/^"//' -e 's/"$//' <<<"$token")
case $token in null)
 abort_on_failure "Failed to obtain the access token"
 ;;
esac
if [ -z "$token" ]
then
 abort_on_failure "Zero length : Failed to obtain the access
token"
 exit 1
fi
}
