#
# Makefile.link
# (C) ETAS GmbH. All rights reserved.
# http://www.etas.com
#

include ./localdef.mk
LIBDEF_MK := $(LIBDIR)/libdef.mk
LIBDEFMCAL_MK := $(LIBDIR)/libdefmcal.mk
include $(LIBDEF_MK)
include $(LIBDEFMCAL_MK)

-include ./mcaldrv.mk
-include ./userdef.mk

export EXTDIR := $(CURDIR)/../_include
export LIBDIR := $(CURDIR)/../lib
export OBJDIR := $(CURDIR)/../obj
export OS_ENV


$(shell test -d $(BINDIR) || mkdir -p $(BINDIR))

# Compare the library definition files with the one of the previous link step.
# If they differ, the EXE file has to relinked in any case. Thus, remove the EXE in this case.
RELINKCHECK_FILE := $(LIBDIR)/relink.check
$(shell cat $(LIBDEF_MK) $(LIBDEFMCAL_MK) > $(RELINKCHECK_FILE)_ 2>/dev/zero)
$(shell diff -q $(RELINKCHECK_FILE) $(RELINKCHECK_FILE)_ > /dev/zero 2>&1 || rm -f $(BINDIR)/$(EXE) )



.PHONY: all clean 

all: 
	$(MAKE) -f Makefile.compile
	set -e; for s in $(DRV_SUBDIRS) ; do  $(MAKE) -C $$s all ; done	
	$(MAKE) link
	rm -f $(RELINKCHECK_FILE)_

clean:
	$(MAKE) -f Makefile.compile clean
	set -e; for s in $(DRV_SUBDIRS) ; do  $(MAKE) -C $$s clean ; done
	$(RM) -rf $(BINDIR)



.PHONY: link
link: $(BINDIR)/$(EXE) $(BINDIR)/Mapfile.xml setExecutionMode xcpFiles

$(BINDIR)/$(EXE): $(LDLIBS) 
	@echo "[LINK]"
	$(MKDIR) $(BINDIR)
	$(LD) -o $@ $(LDFLAGS) -Wl,--whole-archive  $(LDLIBS) -Wl,--no-whole-archive $(LDEXTLIBS) $(STDLIBS)
	# Generate a hard link with the extension .vrta_ecu
	f=$@; if test $$f = $${f%.vrta_ecu}; then ln -f $$f $$f.vrta_ecu; fi
	# Save the relink check file for the next link execution:
	mv -f $(RELINKCHECK_FILE)_ $(RELINKCHECK_FILE)

$(BINDIR)/Mapfile.xml: $(BINDIR)/$(EXE)
	@echo "[MAPFILE]"
	$(CREATE_MAPFILE) -o $@ $<

.PHONY: xcpFiles
xcpFiles:
	rtpc-build-cp-xcp-files $(BINDIR)

.PHONY: setExecutionMode
setExecutionMode: $(BINDIR)/$(EXE)
	# This operation will be performed unconditionally
	$(CHMOD) +x $(BINDIR)/$(EXE)



