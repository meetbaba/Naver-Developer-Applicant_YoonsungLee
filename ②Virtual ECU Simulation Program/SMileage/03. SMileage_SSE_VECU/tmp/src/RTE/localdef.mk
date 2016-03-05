SOURCE_ROOT := ..
INCLUDE_ROOT := $(SOURCE_ROOT)/../_include

OBJECTSDIR := $(SOURCE_ROOT)/../obj/RTE
LIBRARYDIR := $(SOURCE_ROOT)/../lib/RTE

include $(SOURCE_ROOT)/globaldef.mk
-include $(INCLUDE_ROOT)/incldef.mk
-include $(SOURCE_ROOT)/incldef.mk

SUBDIRS :=

EXISTING_LIBRARIES := 

OBJECTS := \
	$(OBJECTSDIR)/Rte.o \
	$(OBJECTSDIR)/Rte_Lib.o

LIB := $(LIBRARYDIR)/RTE.a

