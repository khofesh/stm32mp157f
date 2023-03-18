# Buildroot

https://buildroot.org/

https://bootlin.com/blog/updated-buildroot-support-for-stm32mp1-platforms/

https://github.com/bootlin/buildroot-external-st

```shell
git submodule add git://git.buildroot.net/buildroot
git submodule add -b st/2022.02.7 https://github.com/bootlin/buildroot-external-st.git
```

compile

```shell
cd buildroot
make BR2_EXTERNAL=../buildroot-external-st st_stm32mp157f_dk2_defconfig
make menuconfig #curses interface
sudo dnf install perl-ExtUtils-MakeMaker # Your Perl installation is not complete enough; at least the following modules are missing: ExtUtils::MakeMaker
make -j12
```

copy it using `dd` to sdcard

```shell
sudo dd if=output/images/sdcard.img of=/dev/sde bs=1M
```

boot and see the UART, username is `root` without password

```shell
minicom -b 115200 -o -D /dev/ttyUSB0
```

![minicom](../images/Screenshot%20from%202023-03-17%2023-38-12.png)

# Yocto Project

https://docs.yoctoproject.org/ref-manual/system-requirements.html

fedora packages

```shell
sudo dnf install gawk make wget tar bzip2 gzip python3 unzip perl patch diffutils diffstat git cpp gcc gcc-c++ glibc-devel texinfo chrpath ccache perl-Data-Dumper perl-Text-ParseWords perl-Thread-Queue perl-bignum socat python3-pexpect findutils which file cpio python python3-pip xz python3-GitPython python3-jinja2 SDL-devel xterm rpcgen mesa-libGL-devel perl-FindBin perl-File-Compare perl-File-Copy perl-locale zstd lz4
```

poky

```shell
git submodule add git://git.yoctoproject.org/poky
```

yoctor releases: https://wiki.yoctoproject.org/wiki/Releases

Kirkstone seems to be the LTS, Long Term Support (minimum Apr. 2024¹)

https://github.com/STMicroelectronics/meta-st-stm32mp

```shell
cd poky
git branch -a
git checkout kirkstone
```

check distro config file

```shell
vim meta-poky/conf/distro/poky.conf
```

get stm32 layer and openembedded-core

```shell
cd ..
ls # make sure you're at the same level as `poky`
git submodule add https://github.com/STMicroelectronics/meta-st-stm32mp.git
cd meta-st-stm32mp/
git checkout kirkstone
less README # copy openembedded-core layer git
cd ..
git submodule add https://github.com/openembedded/openembedded-core.git
cd openembedded-core/
git checkout kirkstone
cd ..
git submodule add https://github.com/openembedded/meta-openembedded.git
cd meta-openembedded
git checkout kirkstone
cd ..
source poky/oe-init-build-env build-mp1
```

bitbake

```shell
pwd  # /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/build-mp1
bitbake # Nothing to do.  Use 'bitbake world' to build everything
bitbake-layers show-layers # layers
```

layers

```shell
[fahmad@ryzen build-mp1]$ bitbake-layers show-layers
NOTE: Starting bitbake server...
layer                 path                                      priority
==========================================================================
meta                  /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/poky/meta  5
meta-poky             /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/poky/meta-poky  5
meta-yocto-bsp        /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/poky/meta-yocto-bsp  5
```

edit `bblayers.conf`

```shell
vim conf/bblayers.conf
```

inside `bblayers.conf`

```config
# POKY_BBLAYERS_CONF_VERSION is increased each time build/conf/bblayers.conf
# changes incompatibly
POKY_BBLAYERS_CONF_VERSION = "2"

BBPATH = "${TOPDIR}"
BBFILES ?= ""

BBLAYERS ?= " \
  /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/poky/meta \
  /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/poky/meta-poky \
  /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/poky/meta-yocto-bsp \
  /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/meta-openembedded/meta-oe \
  /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/meta-openembedded/meta-python \
  /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/meta-st-stm32mp \
  "
```

check layers again

```shell
[fahmad@ryzen build-mp1]$  bitbake-layers show-layers
NOTE: Starting bitbake server...
layer                 path                                      priority
==========================================================================
meta                  /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/poky/meta  5
meta-poky             /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/poky/meta-poky  5
meta-yocto-bsp        /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/poky/meta-yocto-bsp  5
meta-oe               /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/meta-openembedded/meta-oe  5
meta-python           /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/meta-openembedded/meta-python  5
meta-st-stm32mp       /home/fahmad/GitHub/stm32mp157f/digi-key/yocto/meta-st-stm32mp  6
```

choose machine

```shell
cd ..
cd meta-st-stm32mp/conf/
cd machine
less stm32mp15-disco.conf # we're using stm32mp15f-dk2
cd ~/GitHub/stm32mp157f/digi-key/yocto/
cd build-mp1/
vim conf/local.conf # we're using MACHINE = "stm32mp15-disco"
```

machine config

```shell
[fahmad@ryzen build-mp1]$  cat conf/local.conf | grep MACHINE
#MACHINE ?= "qemuarm"
#MACHINE ?= "qemuarm64"
#MACHINE ?= "qemumips"
#MACHINE ?= "qemumips64"
#MACHINE ?= "qemuppc"
#MACHINE ?= "qemux86"
#MACHINE ?= "qemux86-64"
#MACHINE ?= "beaglebone-yocto"
#MACHINE ?= "genericx86"
#MACHINE ?= "genericx86-64"
#MACHINE ?= "edgerouter"
#MACHINE ??= "qemux86-64"
MACHINE = "stm32mp15-disco"
#SDKMACHINE ?= "i686"
```

supported images: https://docs.yoctoproject.org/4.0.8/ref-manual/images.html

we'll use `core-image-minimal-dev`

menuconfig

```shell
bitbake -c menuconfig virtual/kernel
```

somehow, my keyboard doesn't work. No Idea!

![menuconfig](../images/Screenshot%20from%202023-03-18%2010-47-52.png)

build image

```shell
bitbake core-image-minimal-dev
```

![build](../images/Screenshot%20from%202023-03-18%2011-36-43.png)

there was an error and I had to do this

```shell
git config --global user.email "myemail@tutanota.com"
git config --global user.name "fahmi ahmad"
```

the images

```shell
cd tmp/deploy/images/stm32mp15-disco/
```

## flash SD card & boot process

# References

- https://github.com/STMicroelectronics/meta-st-stm32mp
- https://github.com/STMicroelectronics/meta-st-openstlinux
- https://layers.openembedded.org/layerindex/branch/master/layers/
