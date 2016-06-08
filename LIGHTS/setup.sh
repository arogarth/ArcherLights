#!/bin/bash

APT_PACKAGES=(ansible git)
GIT_REPO="git@github.com:arogarth/ArcherLights.git"
PROJECT_PATH="/tmp/ArcherLights"

echo "Install required packages..."
apt-get install -y ${APT_PACKAGES[@]}

echo "Clone ArcherLights git repository"
git clone ${GIT_REPO} /tmp/ArcherLights

cd ${PROJECT_PATH}/LIGHTS/ansible

ansible-playbook --connection=local playbook.yml
