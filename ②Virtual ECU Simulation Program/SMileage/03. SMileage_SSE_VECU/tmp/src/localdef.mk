SOURCE_ROOT := .
INCLUDE_ROOT := $(SOURCE_ROOT)/../_include

OBJECTSDIR := $(SOURCE_ROOT)/../obj/
LIBRARYDIR := $(SOURCE_ROOT)/../lib/

include $(SOURCE_ROOT)/globaldef.mk
-include $(INCLUDE_ROOT)/incldef.mk
-include $(SOURCE_ROOT)/incldef.mk

SUBDIRS := \
	Includes \
	MainApplication \
	MCAL \
	RTA-OS \
	RTE \
	SWC \
	XCP

EXISTING_LIBRARIES := 

OBJECTS :=

LIB :=

