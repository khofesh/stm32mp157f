# Custom Image and Layer

```shell
cd /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/
bitbake-layers create-layer meta-custom
cd meta-custom
mkdir -p recipes-core/images
cp ../poky/meta/recipes-core/images/core-image-minimal.bb recipes-core/images/custom-image.bb

```

```shell
cp ../poky/meta/recipes-core/images/core-image-minimal.bb recipes-core/images/custom-image.bb
vim recipes-core/images/custom-image.bb
vim conf/bblayers.conf
bitbake -e | grep IMAGE_FEATURES
vim conf/local.conf
bitbake -e | grep IMAGE_FEATURES
bitbake custom-image
cd cd tmp/deploy/images/stm32mp15-disco/
cd tmp/deploy/images/stm32mp15-disco/
cd scripts/
./create_sdcard_from_flashlayout.sh ../flashlayout_custom-image/optee/FlashLayout_sdcard_stm32mp157f-dk2-optee.tsv
lsblk
umount /dev/sdd*
lsblk
sudo dd if=../FlashLayout_sdcard_stm32mp157f-dk2-optee.raw of=/dev/sdd bs=8M conv=fdatasync
cd ..
cd ~/GitHub/stm32mp157f/digi-key/yocto/build-mp1/
bitbake custom-image
cd tmp/deploy/images/stm32mp15-disco/scripts/
./create_sdcard_from_flashlayout.sh ../flashlayout_custom-image/optee/FlashLayout_sdcard_stm32mp157f-dk2-optee.tsv
umount /dev/sdd*
lsblk
sudo dd if=../FlashLayout_sdcard_stm32mp157f-dk2-optee.raw of=/dev/sdd bs=8M conv=fdatasync
cd tmp/deploy/images/stm32mp15-disco/scripts/
./create_sdcard_from_flashlayout.sh ../flashlayout_custom-image/optee/FlashLayout_sdcard_stm32mp157f-dk2-optee.tsv
lsblk
umount /dev/sdd*
sudo dd if=../FlashLayout_sdcard_stm32mp157f-dk2-optee.raw of=/dev/sdd bs=8M conv=fdatasync
bitbake custom-image
cd tmp/deploy/images/stm32mp15-disco/scripts/
./create_sdcard_from_flashlayout.sh ../flashlayout_custom-image/optee/FlashLayout_sdcard_stm32mp157f-dk2-optee.tsv
sudo dd if=../FlashLayout_sdcard_stm32mp157f-dk2-optee.raw of=/dev/sdd bs=8M conv=fdatasync

```

yeah, can't login, lol
