

ifndef SOURCE_ROOT
	$(error The Makefile variable 'SOURCE_ROOT' is not set)
endif

# Prededefined variables. Might be overwritte later
include $(SOURCE_ROOT)/globaldefaultsdef.mk

# Predefined Directories
BINDIR?=$(SOURCE_ROOT)/../bin
EXTDIR?=$(SOURCE_ROOT)/../_include
LIBDIR?=$(SOURCE_ROOT)/../lib
OBJDIR?=$(SOURCE_ROOT)/../obj


# Fallback executable name:
APP_EXE_NAME?=VECU

CFLAGS := -D'$(OS_ENV)' -I$(SOURCE_ROOT)/../_include -I/opt/etas/include -I/opt/etas/share/eve/mcal/include $(DEBUG) -march=core2 

CPPFLAGS :=

LDFLAGS := -Wl,-Map=Mapfile $(DEBUG)
# The linker option to allow multiple definitions of symbols will be removed soon!
# To check already if there are multiple definitions of symbols you might
# comment out the line below:
#
LDFLAGS += -Wl,--allow-multiple-definition


LDFLAGS += -L/opt/etas/lib/vap -lrtpcxcpslave 

STDLIBS := -lstdc++ -lrt -lpthread -L/opt/etas/lib -lrtos -lrtpc-build-info -les53xx -L/opt/etas/lib/vap -lrtpcxcpslave

