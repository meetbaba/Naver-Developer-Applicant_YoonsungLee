EXE := Smileage

LDLIBS += \
	$(LIBRARYDIR)/MainApplication/MainApplication.a \
	$(LIBRARYDIR)/RTE/RTE.a \
	$(LIBRARYDIR)/SWC/SWC.a \
	$(LIBRARYDIR)/XCP/XCP.a

LDEXTLIBS += \
	$(LIBRARYDIR)/RTA-OS/RTAOS.a

