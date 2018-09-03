!/bin/bash
 docker option
ACTION=$1
shift
# push to this registry
export DOCKER_REGISTRY=$1
shift

export PROFILE=$1
shift

export FE_FOLDER="wec-cpdaily-media"
export PROJECT_NAME="wec-cpdaily-media"
export PROJECT_WAR_PATH="wec-cpdaily-media-web/target/wec-cpdaily-media.war"
export CONTEXT=/wec-cpdaily-media    #上下文根


case $ACTION in
"bp")
    image=${DOCKER_REGISTRY}/${PROJECT_NAME}:${CI_BUILD_TAG}_${CI_BUILD_REF}
    echo building and pushing $image
    docker build --build-arg PROJECT_WAR_PATH=${PROJECT_WAR_PATH} --build-arg PROFILE=${PROFILE} -t $image .
    docker push $image
    ;;
esac

