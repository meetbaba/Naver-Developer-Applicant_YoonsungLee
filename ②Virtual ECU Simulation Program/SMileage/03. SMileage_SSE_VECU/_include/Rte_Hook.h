/** @file     Rte_Hook.h
  *
  * @brief    VFB Trace header file
  *
  * @note     AUTOMATICALLY GENERATED FILE! DO NOT EDIT!
  *
  * @note     Generated by ETAS GmbH  RTA-RTE v5.4.1  (R4.0 backend version: v7.1.31 (Build 33134))
  *           at Wed Dec 17 18:21:49 2014
  */

#ifndef RTE_HOOK_H
#define RTE_HOOK_H

#include "Rte_Const.h"
#include <Os.h>
#include "Rte_Cfg.h"
#include "Rte_Type.h"

#ifndef RTE_VFB_TRACE
#define RTE_VFB_TRACE (0)
#endif /* RTE_VFB_TRACE */

#ifdef __cplusplus
extern "C" {
#endif /* __cplusplus */

/*lint -esym(961,92) Allow use of #undef within this file */

#if defined(Rte_ModeSwitchHook_Return) && (RTE_VFB_TRACE == 0)
#undef Rte_ModeSwitchHook_Return
#endif
#if defined(Rte_ModeSwitchHook_Return)
#undef Rte_ModeSwitchHook_Return
void Rte_ModeSwitchHook_Return(void);
#else
#define Rte_ModeSwitchHook_Return() ((void)(0))
#endif /* Rte_ModeSwitchHook_Return */

#if defined(Rte_ModeSwitchHook_Start) && (RTE_VFB_TRACE == 0)
#undef Rte_ModeSwitchHook_Start
#endif
#if defined(Rte_ModeSwitchHook_Start)
#undef Rte_ModeSwitchHook_Start
void Rte_ModeSwitchHook_Start(void);
#else
#define Rte_ModeSwitchHook_Start() ((void)(0))
#endif /* Rte_ModeSwitchHook_Start */

#if defined(Rte_StartHook_Return) && (RTE_VFB_TRACE == 0)
#undef Rte_StartHook_Return
#endif
#if defined(Rte_StartHook_Return)
#undef Rte_StartHook_Return
void Rte_StartHook_Return(void);
#else
#define Rte_StartHook_Return() ((void)(0))
#endif /* Rte_StartHook_Return */

#if defined(Rte_StartHook_Start) && (RTE_VFB_TRACE == 0)
#undef Rte_StartHook_Start
#endif
#if defined(Rte_StartHook_Start)
#undef Rte_StartHook_Start
void Rte_StartHook_Start(void);
#else
#define Rte_StartHook_Start() ((void)(0))
#endif /* Rte_StartHook_Start */

#if defined(Rte_StopHook_Return) && (RTE_VFB_TRACE == 0)
#undef Rte_StopHook_Return
#endif
#if defined(Rte_StopHook_Return)
#undef Rte_StopHook_Return
void Rte_StopHook_Return(void);
#else
#define Rte_StopHook_Return() ((void)(0))
#endif /* Rte_StopHook_Return */

#if defined(Rte_StopHook_Start) && (RTE_VFB_TRACE == 0)
#undef Rte_StopHook_Start
#endif
#if defined(Rte_StopHook_Start)
#undef Rte_StopHook_Start
void Rte_StopHook_Start(void);
#else
#define Rte_StopHook_Start() ((void)(0))
#endif /* Rte_StopHook_Start */

#if defined(Rte_Task_Activate) && (RTE_VFB_TRACE == 0)
#undef Rte_Task_Activate
#endif
#if defined(Rte_Task_Activate)
#undef Rte_Task_Activate
void Rte_Task_Activate(TaskType);
#else
#define Rte_Task_Activate(task) ((void)(0))
#endif /* Rte_Task_Activate */

#if defined(Rte_Task_SetEvent) && (RTE_VFB_TRACE == 0)
#undef Rte_Task_SetEvent
#endif
#if defined(Rte_Task_SetEvent)
#undef Rte_Task_SetEvent
void Rte_Task_SetEvent(TaskType, EventMaskType);
#else
#define Rte_Task_SetEvent(task, ev) ((void)(0))
#endif /* Rte_Task_SetEvent */

#if defined(Rte_Task_WaitEvent) && (RTE_VFB_TRACE == 0)
#undef Rte_Task_WaitEvent
#endif
#if defined(Rte_Task_WaitEvent)
#undef Rte_Task_WaitEvent
void Rte_Task_WaitEvent(TaskType, EventMaskType);
#else
#define Rte_Task_WaitEvent(task, ev) ((void)(0))
#endif /* Rte_Task_WaitEvent */

#if defined(Rte_Task_WaitEventRet) && (RTE_VFB_TRACE == 0)
#undef Rte_Task_WaitEventRet
#endif
#if defined(Rte_Task_WaitEventRet)
#undef Rte_Task_WaitEventRet
void Rte_Task_WaitEventRet(TaskType, EventMaskType);
#else
#define Rte_Task_WaitEventRet(task, ev) ((void)(0))
#endif /* Rte_Task_WaitEventRet */

#ifdef __cplusplus
} /* extern C */
#endif /* __cplusplus */

#endif /* RTE_HOOK_H */
