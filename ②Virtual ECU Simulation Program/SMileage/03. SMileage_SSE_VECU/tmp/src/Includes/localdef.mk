SOURCE_ROOT := ..
INCLUDE_ROOT := $(SOURCE_ROOT)/../_include

OBJECTSDIR := $(SOURCE_ROOT)/../obj/Includes
LIBRARYDIR := $(SOURCE_ROOT)/../lib/Includes

include $(SOURCE_ROOT)/globaldef.mk
-include $(INCLUDE_ROOT)/incldef.mk
-include $(SOURCE_ROOT)/incldef.mk

SUBDIRS :=

EXISTING_LIBRARIES := 

OBJECTS :=

LIB :=

