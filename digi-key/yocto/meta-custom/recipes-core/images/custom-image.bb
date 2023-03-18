SUMMARY = "Ninkasi Linux Image."

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image
inherit extrausers

# set rootfs to 200 MiB by default
IMAGE_OVERHEAD_FACTOR ?="1.0"
IMAGE_ROOTFS_SIZE ?= "204800"

# change root password - letmein
# printf "%q" $(mkpasswd -m sha256crypt letmein)
PASSWD = "\$5\$9RrZCXONuuRNOdbQ\$YXJ3gIjE4BvnzKHhMyp2J7m28kpWwRZgqATzzvrcoa0"
EXTRA_USERS_PARAMS = "\
    usermod -p '${PASSWD}' root; \
"
