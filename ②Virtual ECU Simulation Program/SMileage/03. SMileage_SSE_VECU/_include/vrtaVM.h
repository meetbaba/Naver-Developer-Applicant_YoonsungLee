/*
* [VP_VECU  5] [$TargetUX 3]
* This file specifies the MultiCore VM DLL API.
*/
#ifndef vrtaVMH
#define vrtaVMH
/* [VP_VECU  4] */
/* [VP_VECU  418] */
/* [VP_VECU  419] */
/* [VP_VECU  420] */

#ifdef __cplusplus
#define EXPORT
extern "C" {
#else
#define EXPORT
#endif /* __cplusplus */

#ifndef _lint
#include <inttypes.h>
#include <stdint.h>
#endif
#define VRTA_ux /* Linux version */
#define VRTA_VM_VERSION (2)
#include /* no inline */ "vrtaTypes.h"

#ifndef _lint
#define DEBUG_LOG(text) /* nothing */
#endif

typedef const void *vrtaEventListener;
typedef vrtaErrType(*vrtaEventCallback)(const void *instance, const vrtaEvent *event); /*  [VP_VECU  97] */
typedef vrtaErrType(*vrtaActionCallback)(void *instance, const vrtaAction *action); /*  [VP_VECU  64] */
typedef vrtaErrType(*vrtaStateCallback)(void *instance, vrtaEvent *state); /*  [VP_VECU  67] */

#define RTVECU_NUM_VECTORS  (30U)        /* [VP_VECU  234] Number of interrupt vectors in the vector table */
typedef struct {
  vrtaUInt numVectors; /* Must be RTVECU_NUM_VECTORS. */
  vrtaIntVector vectors[RTVECU_NUM_VECTORS]; /* [VP_VECU  549] [VP_VECU  235] [VP_VECU  236] */
} vrtaVectorTable; /* [VP_VECU  548] Interrupt vector table */

extern void VM_Initialize(vrtaAppMain main, vrtaInt argc, const vrtaTextPtr argv[], const vrtaVectorTable * vecTable);
extern vrtaInt VM_AddCore(vrtaAppMain main, const vrtaVectorTable * vecTable);
extern vrtaInt VM_GetCoreID(void);
extern vrtaDevID VM_GetCoreICUID(vrtaDevID core);
extern vrtaInt VM_StartCore(vrtaInt core);
extern void VM_Start(void);
extern void VM_IsIdle(vrtamillisecond msecs);
extern void VM_Reset(void);
extern void VM_Terminate(void);
extern void VM_EnterUninterruptibleSection(void);
extern void VM_LeaveUninterruptibleSection(void);
extern void VM_EnterGlobalUninterruptibleSection(void);
extern void VM_LeaveGlobalUninterruptibleSection(void);
extern void VM_SpawnThread(void (*func)(void));
extern vrtaBoolean VM_IsAppThread(void);
extern vrtaBoolean VM_IsAppFinished(void);
extern void VM_NoneUserThread(const vrtaTextPtr funcName);
extern vrtaInt VM_CoreCount(void);
extern vrtaBoolean  VM_play_nicely(vrtaBoolean flag);
extern vrtaBoolean VM_niceness(void);
extern void VM_Yield(void);

extern vrtaDevID VM_RegisterVirtualDevice (
  const vrtaTextPtr name,
  const vrtaOptStringlistPtr info,
  const vrtaOptStringlistPtr events,
  const vrtaOptStringlistPtr actions,
  const vrtaActionCallback aCallback,
  const vrtaStateCallback sCallback,
  const void *tag
);
extern vrtaErrType VM_SendAction(vrtaDevID id, const vrtaAction *a);           /*  [VP_VECU  430] */
extern vrtaErrType VM_RaiseEvent(vrtaDevID id, const vrtaEvent *e);            /*  [VP_VECU  431] */
extern vrtaErrType VM_GetState(vrtaDevID id, vrtaEvent *e);                    /*  [VP_VECU  432] */
extern vrtaEventListener VM_EventRegister(vrtaEventCallback eCallback, const void *tag);                  /*  [VP_VECU  433] */
extern vrtaErrType VM_EventUnregister(vrtaEventListener listener);                                        /*  [VP_VECU  434] */
extern vrtaErrType VM_HookEvent(vrtaEventListener listener, vrtaDevID dev, vrtaEventID ev, vrtaBoolean capture); /*  [VP_VECU  435] */

extern vrtaUInt VM_GetCurrentThreadId(void);
extern vrtaUInt VM_RunTimeInNs(void);
extern void VM_SleepInMs(vrtaUInt delay);
extern vrtaInt VM_TimingClock(void);
extern vrtaUInt VM_RunTimeInMs(void);
#ifdef __cplusplus
}
#endif /* __cplusplus */

enum vrtaResetTypes {
  vrtaDevStart, vrtaDevStop, vrtaDevWriteToPersistentStorage, vrtaDevReadFromPersistentStorage
};

typedef struct {
  vrtaDataLen * vPSLen; /*  Pointer to length of persistent data for device */
  vrtaByte ** vPSAddr;  /*  Pointer to persistent data for device */
  vrtaByte vResetType;  /*  Contains an 'enum vrtaResetTypes' */
} vrtaDevResetInfo;
#endif /* vrtaVMH */
