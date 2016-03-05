SOURCE_ROOT := ..
INCLUDE_ROOT := $(SOURCE_ROOT)/../_include

OBJECTSDIR := $(SOURCE_ROOT)/../obj/RTA-OS
LIBRARYDIR := $(SOURCE_ROOT)/../lib/RTA-OS

include $(SOURCE_ROOT)/globaldef.mk
-include $(INCLUDE_ROOT)/incldef.mk
-include $(SOURCE_ROOT)/incldef.mk

SUBDIRS :=

EXISTING_LIBRARIES := \
	$(LIBRARYDIR)/RTAOS.a

OBJECTS :=

LIB :=

