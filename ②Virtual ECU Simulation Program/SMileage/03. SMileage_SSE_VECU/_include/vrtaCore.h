#ifndef vrtaCoreH
#define vrtaCoreH
#include <vrtaVM.h>
#include "vrtaDevice.h"
#include "vrtaStdDevices.h"


#ifdef __cplusplus
extern "C" {
#endif
  
/*  [VP_VECU  429] [$TargetUX 3] Wrappers around the DLL API */
extern void vrtaLoadVM(void);
extern vrtaErrType vrtaSendAction(vrtaDevID id, const vrtaAction *a);           /*  [VP_VECU  430] */
extern vrtaErrType vrtaRaiseEvent(vrtaDevID id, const vrtaEvent *e);            /*  [VP_VECU  431] */
extern vrtaErrType vrtaGetState(vrtaDevID id, vrtaEvent *e);                    /*  [VP_VECU  432] */
extern vrtaEventListener vrtaEventRegister(vrtaEventCallback eCallback, const void *tag);                  /*  [VP_VECU  433] */
extern vrtaErrType vrtaEventUnregister(vrtaEventListener listener);                                        /*  [VP_VECU  434] */
extern vrtaErrType vrtaHookEvent(vrtaEventListener listener, vrtaDevID dev, vrtaEventID ev, vrtaBoolean capture); /*  [VP_VECU  435] */
extern vrtaIntPriority vrtaOSSetIPL(vrtaIntPriority newIPL);                    /*  [VP_VECU  561] */
extern vrtaIntPriority vrtaOSGetIPL(void);                                      /*  [VP_VECU  562] */
extern void vrtaOSSetIrqMask(vrtaVecNumber vectorNumber);
extern void vrtaOSClearIrqMask(vrtaVecNumber vectorNumber);
extern void vrtaOSSetIrqPending(vrtaVecNumber vectorNumber);
extern void vrtaOSClearIrqPending(vrtaVecNumber vectorNumber);
extern vrtaBoolean vrtaOSIsIrqPending(vrtaVecNumber vectorNumber);
extern void vrtaStart(void);                                                    /*  [VP_VECU  437] */
extern vrtaInt vrtaStartCore(vrtaInt coreID);
extern void vrtaTerminate(void);                                                /*  [VP_VECU  438] */
extern void vrtaReset(void);                                                    /*  [VP_VECU  439] */
extern void vrtaEnterUninterruptibleSection(void);                              /*  [VP_VECU  440] */
extern void vrtaLeaveUninterruptibleSection(void);                              /*  [VP_VECU  441] */
extern void vrtaEnterGlobalUninterruptibleSection(void);
extern void vrtaLeaveGlobalUninterruptibleSection(void);
extern void vrtaIsIdle(vrtamillisecond msecs);                                  /*  [VP_VECU  442] */
extern void vrtaSpawnThread(void (*func)(void));                                /*  [VP_VECU  443] */
extern vrtaInt vrtaAddCore(const vrtaVectorTable* vecTable);
extern vrtaInt vrtaGetCoreID(void);
extern void vrtaInitialize(vrtaInt argc,const vrtaTextPtr argv[],const vrtaVectorTable* vecTable); /*  [VP_VECU  444] */
extern vrtaDevID vrtaRegisterVirtualDevice (                                    /*  [VP_VECU  445] */
  const vrtaTextPtr name,
  const vrtaOptStringlistPtr info,
  const vrtaOptStringlistPtr events,
  const vrtaOptStringlistPtr actions,
  const vrtaActionCallback aCallback,
  const vrtaStateCallback sCallback,
  const void *tag
  );
extern vrtaBoolean vrtaIsAppThread(void);                                       /*  [VP_VECU 565] */
extern vrtaBoolean vrtaIsAppFinished(void);                                     /*  [VP_VECU 566] */
extern void vrtaNoneUserThread(const vrtaTextPtr funcName);                     /*  [VP_VECU 567] */
extern vrtaBoolean vrtaNeedsForcedTerminate;
extern vrtaInt vrtaCoreCount(void);
extern vrtaByte *vrtaOSGetUserStackBase(void);
extern vrtaInt vrtaGetCoreICUID(vrtaInt index);

/* [MISRA 2004 Rule 19.7] */ /*lint -estring(961, OS_MAIN) */
extern void vrta_app_main(void);                                        /*  User-application */
#ifndef OS_MAIN
 #define OS_MAIN() void vrta_app_main(void) /* [$TgtCore 56] */
#endif /* OS_MAIN */

extern vrtaUInt vrtaReadHPTime(vrtaUInt desired_ticks_per_s);   /*  Fine time support */
extern vrtaUInt vrtaTimeInNanoseconds(void);
extern void vrtaSetStartTime(void);
extern void vrtaFireISR(vrtaUInt vec, vrtaDevID core);

extern const vrtaDevID Os_CoreIdForInterruptVector[];
extern vrtaDevID Os_ICUForInterruptVector[];
extern vrtaDevID Os_ICUForCore[];

#ifdef __cplusplus
}
#endif
extern void InitializeDevices(void);                            /*  User-application */
/* --------------------------------------------------------------------------- */
#endif
