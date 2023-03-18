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

get stm32 layer

```shell
cd ..
ls # make sure you're at the same level as `poky`
git submodule add https://github.com/STMicroelectronics/meta-st-stm32mp.git
cd meta-st-stm32mp/
git checkout kirkstone
less README # copy openembedded-core layer git
cd ..
git submodule add https://github.com/openembedded/openembedded-core.git
```

# References
- https://github.com/STMicroelectronics/meta-st-stm32mp
- https://github.com/STMicroelectronics/meta-st-openstlinux
- https://layers.openembedded.org/layerindex/branch/master/layers/


