# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# Unable to find any files that looked like license statements. Check the accompanying
# documentation and source headers and set LICENSE and LIC_FILES_CHKSUM accordingly.
#
# NOTE: LICENSE is being set to "CLOSED" to allow you to at least start building - if
# this is not accurate with respect to the licensing of the software being built (it
# will not be in most cases) you must specify the correct value before using this
# recipe for anything other than initial testing/development!
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "gitsm://git@github.com/cu-ecen-aeld/assignments-3-and-later-MatheusOgata.git;protocol=ssh;branch=main \
           file://files/aesdchar-start-stop.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "baac66a3e1a5a04f922f69f2f6b995047e63ad5a"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module
#inherit update-rc.d

FILES:${PN} += "${sysconfdir}/init.d/aesdchar-start-stop.sh ${base_bindir}/aesdchar_load ${base_bindir}/aesdchar_unload"

#INITSCRIPT_PACKAGES += "${PN}"
#INITSCRIPT_NAME:${PN} = "aeschar-start-stop.sh"

RPPROVIDES:${PN} += "kernel-module-aesdchar"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/aesdchar"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"


do_install() {

        install -d ${D}${sysconfdir}/init.d
        install -m 0777 ${WORKDIR}/files/aesdchar-start-stop.sh ${D}${sysconfdir}/init.d/

        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
        install -m 0777 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra

        install -d ${D}${base_bindir}
        install -m 0777 ${S}/aesdchar_load ${D}${base_bindir}
        install -m 0777 ${S}/aesdchar_unload ${D}${base_bindir}
}

pkg_postinst_ontarget:${PN}() {
    # This script will run on the target during first boot
    if [ -z "$D" ]; then
        echo "Running post-install script for aesdchar"
        update-rc.d aesdchar-start-stop.sh defaults
    fi
}
