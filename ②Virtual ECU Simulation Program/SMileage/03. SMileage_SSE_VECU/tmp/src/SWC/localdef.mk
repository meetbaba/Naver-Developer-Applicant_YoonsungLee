SOURCE_ROOT := ..
INCLUDE_ROOT := $(SOURCE_ROOT)/../_include

OBJECTSDIR := $(SOURCE_ROOT)/../obj/SWC
LIBRARYDIR := $(SOURCE_ROOT)/../lib/SWC

include $(SOURCE_ROOT)/globaldef.mk
-include $(INCLUDE_ROOT)/incldef.mk
-include $(SOURCE_ROOT)/incldef.mk

SUBDIRS :=

EXISTING_LIBRARIES := 

OBJECTS := \
	$(OBJECTSDIR)/AccelerateDevice.o \
	$(OBJECTSDIR)/BrakeDevice.o \
	$(OBJECTSDIR)/CoolantTemperatureDevice.o \
	$(OBJECTSDIR)/CrankshaftPositionDevice.o \
	$(OBJECTSDIR)/GearDevice.o \
	$(OBJECTSDIR)/GyroDevice.o \
	$(OBJECTSDIR)/OutFuelCutDevice.o \
	$(OBJECTSDIR)/Rte_CoolantTemperaturePlausi.o \
	$(OBJECTSDIR)/Rte_CrankshaftPositionPlausi.o \
	$(OBJECTSDIR)/Rte_EngineECU.o \
	$(OBJECTSDIR)/Rte_FuelCutActuator.o \
	$(OBJECTSDIR)/Rte_FuelCutAlgo.o \
	$(OBJECTSDIR)/Rte_GyroPlausi.o \
	$(OBJECTSDIR)/Rte_VehicleSpeedPlausi.o \
	$(OBJECTSDIR)/VehicleSpeedDevice.o

LIB := $(LIBRARYDIR)/SWC.a

