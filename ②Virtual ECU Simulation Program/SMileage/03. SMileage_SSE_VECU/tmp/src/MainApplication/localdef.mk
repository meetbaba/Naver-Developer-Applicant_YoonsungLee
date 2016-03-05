SOURCE_ROOT := ..
INCLUDE_ROOT := $(SOURCE_ROOT)/../_include

OBJECTSDIR := $(SOURCE_ROOT)/../obj/MainApplication
LIBRARYDIR := $(SOURCE_ROOT)/../lib/MainApplication

include $(SOURCE_ROOT)/globaldef.mk
-include $(INCLUDE_ROOT)/incldef.mk
-include $(SOURCE_ROOT)/incldef.mk

SUBDIRS :=

EXISTING_LIBRARIES := 

OBJECTS := \
	$(OBJECTSDIR)/BswTasks.o \
	$(OBJECTSDIR)/eveVrtaSyncClock.o \
	$(OBJECTSDIR)/main.o \
	$(OBJECTSDIR)/Os_ErrorHook.o \
	$(OBJECTSDIR)/Os_Hooks.o \
	$(OBJECTSDIR)/target.o \
	$(OBJECTSDIR)/VECU_SystemClock.o \
	$(OBJECTSDIR)/VECU_SystemClock_ISR.o \
	$(OBJECTSDIR)/Vrta_Cfg.o

LIB := $(LIBRARYDIR)/MainApplication.a

