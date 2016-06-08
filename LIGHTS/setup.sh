#!/bin/bash

APT_PACKAGES=(ansible git)
GIT_REPO="https://github.com/arogarth/ArcherLights.git"
PROJECT_PATH="/tmp/ArcheryLights"

echo "Update APT-Cache..."
apt-get update > /dev/null

echo "Install required packages..."
apt-get install -y ${APT_PACKAGES[@]}

if [ -d ${PROJECT_PATH} ]; then rm -rf ${PROJECT_PATH}; fi

echo "Clone ArcherLights git repository"
git clone ${GIT_REPO} ${PROJECT_PATH}

cd ${PROJECT_PATH}/LIGHTS/ansible

echo "127.0.0.1" > /tmp/inventory.localhost
ansible-playbook --connection=local -i /tmp/inventory.localhost playbook.yml
