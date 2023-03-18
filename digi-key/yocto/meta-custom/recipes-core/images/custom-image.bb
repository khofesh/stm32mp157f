SUMMARY = "Ninkasi Linux Image."

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image
inherit extrausers

# set rootfs to 200 MiB by default
IMAGE_OVERHEAD_FACTOR ?="1.0"
IMAGE_ROOTFS_SIZE ?= "204800"

# change root password (note the capital -P)
EXTRA_USERS_PARAMS = "\
    usermod --password $(openssl passwd 'letmein') root \
"
