
#
# Makefile.compile
# (C) ETAS GmbH. All rights reserved.
# http://www.etas.com
#

include ./localdef.mk
-include ./mcaldrv.mk
-include ./userdef.mk

OBJECTSDIR?=.

DEPENDENCIES := $(OBJECTS:.o=.d)


# --------------------------------------------------------------------------------
# Create the directory for the objects if it is not existing:
$(shell test -d $(OBJECTSDIR) || mkdir -p $(OBJECTSDIR))
$(shell test -d $(LIBRARYDIR) || mkdir -p $(LIBRARYDIR))


.PHONY: all clean 


# ********************************************************************************

all: $(SUBDIRS) $(LIB) $(EXISTING_LIBRARIES)

clean: $(SUBDIRS)
	$(RM) $(EXE) $(LIB) $(OBJECTS) $(DEPENDENCIES) $(EXISTING_LIBRARIES)


# ================================================================================
ifneq ($(LIB),)
# LIB is defined

# Create a list file that holds the name of all .o files in the lib archive.
# This file will be re-written only if the contents changes.
# This is done to allow to rebuild a LIB when a OBJECT is removed.
#
# The OBJECTS are defined in 'localdef.mk', that's why the generation of that list
# depends on 'localdef.mk'.
#
LIBLIST:=$(LIB).list
LIBLISTTMP:=$(LIBLIST)_

$(LIBLIST): localdef.mk
	@echo $(OBJECTS) > $(LIBLISTTMP) ; \
	(  if  test ! -r $(LIBLIST) || ! diff  -q $(LIBLIST) $(LIBLISTTMP) ; then \
	     mv -f  $(LIBLISTTMP) $(LIBLIST) ; \
	   else \
	     rm -f $(LIBLISTTMP) ; \
	   fi  \
	) > /dev/null

$(LIB): $(OBJECTS) $(LIBLIST)
	-$(RM) $(LIB)
	$(AR) $(ARFLAGS) $(LIB) $(OBJECTS) 


endif

# ********************************************************************************

# Common Sub-Directory handling

.PHONY: $(SUBDIRS) 
$(SUBDIRS):
	@echo "Processing .$(RELATIVE_DIR)/$@ ..."
	$(MAKE) RELATIVE_DIR="$(RELATIVE_DIR)/$@" -C $@ $(MAKECMDGOALS)


# ================================================================================
# Implicit rules

# Plain C:
$(OBJECTSDIR)/%.o: %.c 
	$(CC) -MMD -MP -c -o $@ $(CFLAGS) $(INCDIRS) $<

# C++:
$(OBJECTSDIR)/%.o: %.cpp
	$(CXX) -MMD -MP -c -o $@ $(CFLAGS) $(CPPFLAGS) $(INCDIRS) $<

# Precompiled object files:
$(OBJECTSDIR)/%.o: %.o
	cp -f $< $@

# Existing libraries:
$(LIBRARYDIR)/%.a: %.a
	cp -f $< $@

.PHONY: compile_info
compile_info:
	@echo "[COMPILE]"
# ================================================================================

ifneq ( $(MAKECMDGOALS), clean )
-include $(DEPENDENCIES)
endif

