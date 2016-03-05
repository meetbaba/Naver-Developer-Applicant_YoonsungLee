/** @file     Rte_Const.h
  *
  * @brief    RTA-RTE related configuration constants
  *
  * @note     AUTOMATICALLY GENERATED FILE! DO NOT EDIT!
  *
  * @note     Generated by ETAS GmbH  RTA-RTE v5.4.1  (R4.0 backend version: v7.1.31 (Build 33134))
  *           at Wed Dec 17 18:15:49 2014
  */

#ifndef RTE_CONST_H
#define RTE_CONST_H

#define _VECU_Instance_FlatView_SwComponentTypes_VECU_Instance_FlatView_SwComponentPrototype_CoolantTemperaturePlausi SwComponentPrototype_CoolantTemperaturePlausi
#define _VECU_Instance_FlatView_SwComponentTypes_VECU_Instance_FlatView_SwComponentPrototype_CrankshaftPositionPlausi SwComponentPrototype_CrankshaftPositionPlausi
#define _VECU_Instance_FlatView_SwComponentTypes_VECU_Instance_FlatView_SwComponentPrototype_EngineECU                SwComponentPrototype_EngineECU
#define _VECU_Instance_FlatView_SwComponentTypes_VECU_Instance_FlatView_SwComponentPrototype_FuelCutActuator          SwComponentPrototype_FuelCutActuator
#define _VECU_Instance_FlatView_SwComponentTypes_VECU_Instance_FlatView_SwComponentPrototype_FuelCutAlgo              SwComponentPrototype_FuelCutAlgo
#define _VECU_Instance_FlatView_SwComponentTypes_VECU_Instance_FlatView_SwComponentPrototype_GyroPlausi               SwComponentPrototype_GyroPlausi
#define _VECU_Instance_FlatView_SwComponentTypes_VECU_Instance_FlatView_SwComponentPrototype_VehicleSpeedPlausi       SwComponentPrototype_VehicleSpeedPlausi
#define RTE_CALPRM_NONE                                                                                               (1)
#define RTE_MAINFUNCTION_PERIOD_US                                                                                    (10000)
#define RTE_MAINFUNCTION_PERIOD_NS                                                                                    (10000000)
#define RTE_TIMEOUTALARM1                                                                                             (0)
#define RTE_TIMEOUTALARM2                                                                                             (1)
#define RTE_TIMEOUTALARM3                                                                                             (2)
#define RTE_TIMEOUTALARM4                                                                                             (3)
#define RTE_TIMEOUTALARM5                                                                                             (4)
#define RTE_TIMEOUTALARM6                                                                                             (5)
#define RTE_TIMEOUTALARM7                                                                                             (6)
#define RTE_TIMEOUTALARM8                                                                                             (7)
#define RTE_NUM_TIMEOUT_ALARMS                                                                                        (8)
#define RTE_TASKS_ARRAYSIZE                                                                                           (6)
#define RTE_ALARM_COUNTER_TICK_INTERVAL_US                                                                            (1000)
#define RTE_PERIODIC_COUNTER_TICK_INTERVAL_US                                                                         (1000000)
#define RTE_MAX_USED_CORE_ID                                                                                          (0)
#define RTE_OSAPI_AUTOSAR_R40                                                                                         (1)
#define RTE_OSCFG_AUTOSAR_R40                                                                                         (1)
#define RTE_COMPATIBILITY_MODE                                                                                        (1)
#define RTE_MEASUREMENT_ENABLED                                                                                       (1)
#define RTE_NUM_TASKS                                                                                                 (6)
#define RTE_WOWP_EVENTS                                                                                               (0)
/* Box: Rte: OS Environment begin */
/* The following OSENV_ macros are supported by this OS DLL: */
/* OSENV_UNSUPPORTED */
/* OSENV_RTAOS40 */
#if defined(OSENV_RTAOS40)
#if defined( OSENV_UNSUPPORTED )
#error "Please define exactly one of [ OSENV_RTAOS40, OSENV_UNSUPPORTED ] for OS_AUTOSAR40 DLL OS API."
#endif /* defined( OSENV_UNSUPPORTED ) */
#elif defined(OSENV_UNSUPPORTED)
#else
#error "Please define exactly one of [ OSENV_RTAOS40, OSENV_UNSUPPORTED ] for OS_AUTOSAR40 DLL OS API."
#endif
/* Box: User declared atomic BSW types: begin */
/* Box: User declared atomic BSW types: end */
/* Box: Rte: OS Environment end */
#endif /* RTE_CONST_H */
