#!/bin/bash

APT_PACKAGES=(ansible git)
GIT_REPO="https://github.com/arogarth/ArcherLights.git"
PROJECT_PATH="/tmp/ArcheryLights"

echo "Update APT-Cache..."
apt-get update

echo "Install required packages..."
apt-get install -y ${APT_PACKAGES[@]}

echo "Clone ArcherLights git repository"
git clone ${GIT_REPO} ${PROJECT_PATH}

cd ${PROJECT_PATH}/LIGHTS/ansible

ansible-playbook --connection=local -i inventory.local playbook.yml
