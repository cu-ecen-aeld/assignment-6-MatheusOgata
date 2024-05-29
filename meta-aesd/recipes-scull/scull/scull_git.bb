# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"


SRC_URI = "gitsm://git@github.com/cu-ecen-aeld/assignment-7-MatheusOgata.git;protocol=ssh;branch=master \
	   file://files/modules-start-stop.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "ff2ad8fb5af0fa9bc6c24d080daf541cad3d592c"

S = "${WORKDIR}/git"

inherit module
inherit update-rc.d

FILES:${PN} += "${sysconfdir}/init.d/modules-start-stop.sh ${base_bindir}/scull_load \
		${base_bindir}/scull_unload ${base_bindir}/module_load ${base_bindir}/module_unload "

INITSCRIPT_PACKAGES += "${PN}"
INITSCRIPT_NAME:${PN} = "modules-start-stop.sh"

RPROVIDES:${PN} += "kernel-module-scull kernel-module-hello kernel-module-faulty"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"


do_install() {

	install -d ${D}${sysconfdir}/init.d
	install -m 0777 ${WORKDIR}/files/modules-start-stop.sh ${D}${sysconfdir}/init.d/

	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
	install -m 0777 ${S}/scull/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
	install -m 0777 ${S}/misc-modules/hello.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
	install -m 0777 ${S}/misc-modules/faulty.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra

	install -d ${D}${base_bindir}
	install -m 0777 ${S}/scull/scull_load ${D}${base_bindir}
	install -m 0777 ${S}/scull/scull_unload ${D}${base_bindir}
	install -m 0777 ${S}/misc-modules/module_load ${D}${base_bindir}
	install -m 0777 ${S}/misc-modules/module_unload ${D}${base_bindir}

}

