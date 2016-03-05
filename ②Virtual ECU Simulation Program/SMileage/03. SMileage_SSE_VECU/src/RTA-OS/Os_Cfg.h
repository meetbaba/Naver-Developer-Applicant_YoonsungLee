/* 
 * This is Os_Cfg.h, auto-generated for:
 *   Project: os_XCP
 *   Target:  VRTA-ux
 *   Variant: RTPC
 *   [$UKS 650]
 */
#ifndef OS_CFG_H
#define OS_CFG_H
#define OS_ISR_VECTOR_VECU_SystemClock_ISR (7U)

#define OS_NUM_APPMODES (2U)
#define OS_NUM_APPLICATIONS (0U)
#define OS_NUM_SPINLOCKS (0U)
#define OS_NUM_TRUSTED_FUNCTIONS (0U)
#define OS_NUM_EVENTS (0U)
#define OS_NUM_TASKS (7U)
#define OS_NUM_ISRS (1U)
#define OS_NUM_RESOURCES (2U)
#define OS_NUM_ALARMS (9U)
#define OS_NUM_SCHEDULETABLES (1U)
#define OS_NUM_TRACEPOINTS (0U)
#define OS_NUM_TASKTRACEPOINTS (0U)
#define OS_NUM_INTERVALS (0U)
#define OS_NUM_TRACECATEGORIES (0U)
#define OS_TRACE_CATEGORY_ALWAYS ((Os_TraceCategoriesType)(0x80000000UL))
#define OS_TRACE_CATEGORY_NEVER  ((Os_TraceCategoriesType)(0x00000000UL))
/* [MISRA 2004 Rule 19.7] */ /*lint -estring(961, OS_TICKS2*) */
#define OS_NUM_COUNTERS (3U)
#define OSCYCLEDURATION (1000) /* [$UKS 1224] */
#define OSCYCLESPERSECOND (1000000U) /* [$UKS 1225] */
#define OSSWTICKDURATION (1000000) /* [$UKS 1226] */
#define OSSWTICKSPERSECOND (1000U) /* [$UKS 1227] */
#define OS_TICKS2NS_Rte_TOutCounter(ticks) ((PhysicalTimeType)(ticks * 1000000000U /* Default: SecondsPerTick not set */)) /* [$UKS 843] */
#define OS_TICKS2US_Rte_TOutCounter(ticks) ((PhysicalTimeType)(ticks * 1000000U /* Default: SecondsPerTick not set */)) /* [$UKS 843] */
#define OS_TICKS2MS_Rte_TOutCounter(ticks) ((PhysicalTimeType)(ticks * 1000U /* Default: SecondsPerTick not set */)) /* [$UKS 843] */
#define OS_TICKS2SEC_Rte_TOutCounter(ticks) ((PhysicalTimeType)(ticks * 1U /* Default: SecondsPerTick not set */)) /* [$UKS 843] */
#define OSMAXALLOWEDVALUE_Rte_TOutCounter (65535U) /* [$UKS 219] */
#define OSTICKSPERBASE_Rte_TOutCounter (1U) /* [$UKS 220] */
#define OSTICKDURATION_Rte_TOutCounter OS_TICKS2NS_Rte_TOutCounter(1U) /* [$UKS 221] */
#define OSMINCYCLE_Rte_TOutCounter (1U) /* [$UKS 222] */
#define OS_TICKS2NS_Rte_TickCounter(ticks) ((PhysicalTimeType)(ticks * 1000000000U /* Default: SecondsPerTick not set */)) /* [$UKS 843] */
#define OS_TICKS2US_Rte_TickCounter(ticks) ((PhysicalTimeType)(ticks * 1000000U /* Default: SecondsPerTick not set */)) /* [$UKS 843] */
#define OS_TICKS2MS_Rte_TickCounter(ticks) ((PhysicalTimeType)(ticks * 1000U /* Default: SecondsPerTick not set */)) /* [$UKS 843] */
#define OS_TICKS2SEC_Rte_TickCounter(ticks) ((PhysicalTimeType)(ticks * 1U /* Default: SecondsPerTick not set */)) /* [$UKS 843] */
#define OSMAXALLOWEDVALUE_Rte_TickCounter (65535U) /* [$UKS 219] */
#define OSTICKSPERBASE_Rte_TickCounter (1U) /* [$UKS 220] */
#define OSTICKDURATION_Rte_TickCounter OS_TICKS2NS_Rte_TickCounter(1U) /* [$UKS 221] */
#define OSMINCYCLE_Rte_TickCounter (1U) /* [$UKS 222] */
#define OS_TICKS2NS_XCP_Counter(ticks) ((PhysicalTimeType)(ticks * 100000000U)) /* [$UKS 843] */
#define OS_TICKS2US_XCP_Counter(ticks) ((PhysicalTimeType)(ticks * 100000U)) /* [$UKS 843] */
#define OS_TICKS2MS_XCP_Counter(ticks) ((PhysicalTimeType)(ticks * 100U)) /* [$UKS 843] */
#define OS_TICKS2SEC_XCP_Counter(ticks) ((PhysicalTimeType)(ticks / 10U)) /* [$UKS 843] */
#define OSMAXALLOWEDVALUE_XCP_Counter (65535U) /* [$UKS 219] */
#define OSTICKSPERBASE_XCP_Counter (1U) /* [$UKS 220] */
#define OSTICKDURATION_XCP_Counter OS_TICKS2NS_XCP_Counter(1U) /* [$UKS 221] */
#define OSMINCYCLE_XCP_Counter (1U) /* [$UKS 222] */
#define OSMAXALLOWEDVALUE OSMAXALLOWEDVALUE_SystemCounter /* [$UKS 215] */
#define OSTICKSPERBASE OSTICKSPERBASE_SystemCounter /* [$UKS 216] */
#define OSTICKDURATION OSTICKDURATION_SystemCounter /* [$UKS 217] */
#define OSMINCYCLE OSMINCYCLE_SystemCounter /* [$UKS 218] */
#define OS_NUM_CORES (1U)
#define OS_NUM_OS_CORES (1U)



/* ------------------------------------------------- */
/* [MISRA 2004 Rule 19.1] */ /*lint -save -esym(961, 19.1) */
#define OS_START_SEC_APPL_CODE
#include "MemMap.h" /* [MISRA 2004 Rule 19.15] */ /*lint !e537 !e451 */
/*lint -restore */
extern FUNC(void, OS_APPL_CODE) Os_Entry_OsTask_ECU(void);
extern FUNC(void, OS_APPL_CODE) Os_Entry_OsTask_Plausi(void);
extern FUNC(void, OS_APPL_CODE) Os_Entry_OsTask_Algo(void);
extern FUNC(void, OS_APPL_CODE) Os_Entry_OsTask_AlgoToActuator(void);
extern FUNC(void, OS_APPL_CODE) Os_Entry_OsTask_ActuatorToEngine(void);
extern FUNC(void, OS_APPL_CODE) Os_Entry_OsTask_SetFuelCutState(void);
extern FUNC(void, OS_APPL_CODE) Os_Entry_XCPTask(void);
extern FUNC(void, OS_APPL_CODE) Os_Entry_VECU_SystemClock_ISR(void);
/* [MISRA 2004 Rule 19.1] */ /*lint -save -esym(961, 19.1) */
#define OS_STOP_SEC_APPL_CODE
#include "MemMap.h" /* [MISRA 2004 Rule 19.15] */ /*lint !e537 !e451 */
/*lint -restore */

/* ------------------------------------------------- */
/* [MISRA 2004 Rule 19.1] */ /*lint -save -esym(961, 19.1) */
#define OS_START_SEC_CALLOUT_CODE
#include "MemMap.h" /* [MISRA 2004 Rule 19.15] */ /*lint !e537 !e451 */
/*lint -restore */
extern FUNC(void, OS_CALLOUT_CODE) Os_Cbk_Alarm_Rte_NullAlarmCallback(void);
/* [MISRA 2004 Rule 19.1] */ /*lint -save -esym(961, 19.1) */
#define OS_STOP_SEC_CALLOUT_CODE
#include "MemMap.h" /* [MISRA 2004 Rule 19.15] */ /*lint !e537 !e451 */
/*lint -restore */

/* ------------------------------------------------- */
/* [MISRA 2004 Rule 19.1] */ /*lint -save -esym(961, 19.1) */
#define OS_START_SEC_CODE_LIB
#include "MemMap.h" /* [MISRA 2004 Rule 19.15] */ /*lint !e537 !e451 */
/*lint -restore */
extern FUNC(StatusType, OS_CODE) Os_IncrementCounter_Rte_TOutCounter(void);
extern FUNC(StatusType, OS_CODE) Os_IncrementCounter_Rte_TickCounter(void);
extern FUNC(StatusType, OS_CODE) Os_IncrementCounter_XCP_Counter(void);
/* [MISRA 2004 Rule 19.1] */ /*lint -save -esym(961, 19.1) */
#define OS_STOP_SEC_CODE_LIB
#include "MemMap.h" /* [MISRA 2004 Rule 19.15] */ /*lint !e537 !e451 */
/*lint -restore */



/* -------- AppMode declarations --------- */
#define OS_NOAPPMODE ((AppModeType)0U)
#define OSDEFAULTAPPMODE ((AppModeType)1U)  /* [$UKS 381] [$UKS 383] */

/* ----- OS-Application declarations ----- */

/* -------- Event declarations --------- */

/* -------- Task declarations --------- */
#define OsTask_ECU ((TaskType)(&Os_const_tasks[0U]))
DeclareTask(OsTask_ECU)
#define OsTask_Plausi ((TaskType)(&Os_const_tasks[1U]))
DeclareTask(OsTask_Plausi)
#define OsTask_Algo ((TaskType)(&Os_const_tasks[2U]))
DeclareTask(OsTask_Algo)
#define OsTask_AlgoToActuator ((TaskType)(&Os_const_tasks[3U]))
DeclareTask(OsTask_AlgoToActuator)
#define OsTask_ActuatorToEngine ((TaskType)(&Os_const_tasks[4U]))
DeclareTask(OsTask_ActuatorToEngine)
#define OsTask_SetFuelCutState ((TaskType)(&Os_const_tasks[5U]))
DeclareTask(OsTask_SetFuelCutState)
#define XCPTask ((TaskType)(&Os_const_tasks[6U]))
DeclareTask(XCPTask)

/* -------- ISR declarations --------- */
#define VECU_SystemClock_ISR ((ISRType)(&Os_const_isrs[0U]))
DeclareISR(VECU_SystemClock_ISR)

/* -------- Resource declarations --------- */
#define RTE_RESOURCE (&Os_const_resources[0U])
DeclareResource(RTE_RESOURCE)
#define RES_SCHEDULER (&Os_const_resources[1U])
DeclareResource(RES_SCHEDULER)

/* -------- Counter declarations --------- */
#define Rte_TOutCounter (&Os_const_counters[0U])
DeclareCounter(Rte_TOutCounter)
#define Rte_TickCounter (&Os_const_counters[1U])
DeclareCounter(Rte_TickCounter)
#define XCP_Counter (&Os_const_counters[2U])
DeclareCounter(XCP_Counter)

/* -------- Alarm declaration --------- */
#define Rte_TimeoutAlarm1 (0U)
DeclareAlarm(Rte_TimeoutAlarm1)
#define Rte_TimeoutAlarm2 (1U)
DeclareAlarm(Rte_TimeoutAlarm2)
#define Rte_TimeoutAlarm3 (2U)
DeclareAlarm(Rte_TimeoutAlarm3)
#define Rte_TimeoutAlarm4 (3U)
DeclareAlarm(Rte_TimeoutAlarm4)
#define Rte_TimeoutAlarm5 (4U)
DeclareAlarm(Rte_TimeoutAlarm5)
#define Rte_TimeoutAlarm6 (5U)
DeclareAlarm(Rte_TimeoutAlarm6)
#define Rte_TimeoutAlarm7 (6U)
DeclareAlarm(Rte_TimeoutAlarm7)
#define Rte_TimeoutAlarm8 (7U)
DeclareAlarm(Rte_TimeoutAlarm8)
#define XCP_Alarm (8U)
DeclareAlarm(XCP_Alarm)

/* -------- ScheduleTable declaration --------- */
#define Rte_ScheduleTable (&Os_const_scheduletables[0U])
DeclareScheduleTable(Rte_ScheduleTable)

/* -------- Tracepoint declarations --------- */

/* -------- TaskTracepoint declarations --------- */

/* -------- Interval declarations --------- */

/* -------- Filtered APIs --------- */
#define Os_LogTracepoint(TpointID,Category) /* never */
#define Os_LogTracepointValue(TpointID,Value,Category) /* never */
#define Os_LogTracepointData(TpointID,Data,Length,Category) /* never */
#define Os_LogTaskTracepoint(TTpointID,Category) /* never */
#define Os_LogTaskTracepointValue(TTpointID,Value,Category) /* never */
#define Os_LogTaskTracepointData(TTpointID,Data,Length,Category) /* never */
#define Os_LogIntervalStart(IntervalID,Category) /* never */
#define Os_LogIntervalStartValue(IntervalID,Value,Category) /* never */
#define Os_LogIntervalStartData(IntervalID,Data,Length,Category) /* never */
#define Os_LogIntervalEnd(IntervalID,Category) /* never */
#define Os_LogIntervalEndValue(IntervalID,Value,Category) /* never */
#define Os_LogIntervalEndData(IntervalID,Data,Length,Category) /* never */
#ifndef OS_TRACE_NAMESPACE_PURE /* [$UKS 1154] */
/* [MISRA 2004 Rule 19.7] */ /*lint -estring(961, LogTrace* ,LogTaskTrace* ,LogInterval*) */  #define LogTracepoint(TpointID,Category) Os_LogTracepoint(TpointID,Category)
  #define LogTracepointValue(TpointID,Value,Category) Os_LogTracepointValue(TpointID,Value,Category)
  #define LogTracepointData(TpointID,Data,Length,Category) Os_LogTracepointData(TpointID,Data,Length,Category)
  #define LogTaskTracepoint(TTpointID,Category) Os_LogTaskTracepoint(TTpointID,Category)
  #define LogTaskTracepointValue(TTpointID,Value,Category) Os_LogTaskTracepointValue(TTpointID,Value,Category)
  #define LogTaskTracepointData(TTpointID,Data,Length,Category) Os_LogTaskTracepointData(TTpointID,Data,Length,Category)
  #define LogIntervalStart(IntervalID,Category) Os_LogIntervalStart(IntervalID,Category)
  #define LogIntervalStartValue(IntervalID,Value,Category) Os_LogIntervalStartValue(IntervalID,Value,Category)
  #define LogIntervalStartData(IntervalID,Data,Length,Category) Os_LogIntervalStartData(IntervalID,Data,Length,Category)
  #define LogIntervalEnd(IntervalID,Category) Os_LogIntervalEnd(IntervalID,Category)
  #define LogIntervalEndValue(IntervalID,Value,Category) Os_LogIntervalEndValue(IntervalID,Value,Category)
  #define LogIntervalEndData(IntervalID,Data,Length,Category) Os_LogIntervalEndData(IntervalID,Data,Length,Category)
#endif

#endif /* OS_CFG_H */
