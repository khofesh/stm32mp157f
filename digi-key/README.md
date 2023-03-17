# Buildroot

https://buildroot.org/

https://bootlin.com/blog/updated-buildroot-support-for-stm32mp1-platforms/

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
