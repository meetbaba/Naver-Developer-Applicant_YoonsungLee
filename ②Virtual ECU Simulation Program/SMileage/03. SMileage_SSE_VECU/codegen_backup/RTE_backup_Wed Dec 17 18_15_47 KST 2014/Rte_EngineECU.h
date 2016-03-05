/** @file     Rte_EngineECU.h
  *
  * @brief    Application header file
  *
  * @note     AUTOMATICALLY GENERATED FILE! DO NOT EDIT!
  *
  * @note     Generated by ETAS GmbH  RTA-RTE v5.4.1  (R4.0 backend version: v7.1.31 (Build 33134))
  *           at Wed Dec 17 18:13:28 2014
  */

#ifndef RTE_ENGINEECU_H
#define RTE_ENGINEECU_H

#ifndef RTE_CORE
#ifdef RTE_APPLICATION_HEADER_FILE
#error Multiple application header files included.
#endif /* RTE_APPLICATION_HEADER_FILE */
#define RTE_APPLICATION_HEADER_FILE
#endif /* RTE_CORE */

/*******************************************************
 ***
 *** Includes
 ***
 *******************************************************/

#include "Rte.h"
#include "Rte_Intl.h"
#include "Rte_EngineECU_Type.h"

#include "Rte_DataHandleType.h"

#ifdef __cplusplus
extern "C" {
#endif /* __cplusplus */

#if !defined(RTE_RUNNABLEAPI_RE_SendData) && !defined(RTE_RUNNABLEAPI_RE_SetFuelCutState)
#define RTE_PRV_ALL_API
#endif

/*******************************************************
 ***
 *** Constants
 ***
 *******************************************************/


/*******************************************************
 ***
 *** Public Types
 ***
 *******************************************************/

/* BEGIN: SWC types (core visible) */
typedef struct {
   Rte_CallFP_EngineECU_CS_IF_ACCELERATE_SetAccelerateLevel   Call_SetAccelerateLevel;
   Rte_ResultFP_EngineECU_CS_IF_ACCELERATE_SetAccelerateLevel Result_SetAccelerateLevel;
} Rte_PDS_EngineECU_CS_IF_ACCELERATE_R;

typedef Rte_PDS_EngineECU_CS_IF_ACCELERATE_R Rte_PDS_EngineECU_CS_IF_ACCELERATE_RA[1];

typedef struct {
   Rte_CallFP_EngineECU_CS_IF_BRAKE_SetBrakeLevel   Call_SetBrakeLevel;
   Rte_ResultFP_EngineECU_CS_IF_BRAKE_SetBrakeLevel Result_SetBrakeLevel;
} Rte_PDS_EngineECU_CS_IF_BRAKE_R;

typedef Rte_PDS_EngineECU_CS_IF_BRAKE_R Rte_PDS_EngineECU_CS_IF_BRAKE_RA[1];

typedef struct {
   Rte_CallFP_EngineECU_CS_IF_CP_RAW_SetCrankshaftPositionRaw   Call_SetCrankshaftPositionRaw;
   Rte_ResultFP_EngineECU_CS_IF_CP_RAW_SetCrankshaftPositionRaw Result_SetCrankshaftPositionRaw;
} Rte_PDS_EngineECU_CS_IF_CP_RAW_R;

typedef Rte_PDS_EngineECU_CS_IF_CP_RAW_R Rte_PDS_EngineECU_CS_IF_CP_RAW_RA[1];

typedef struct {
   Rte_CallFP_EngineECU_CS_IF_CT_RAW_SetCoolantTemperatureRaw   Call_SetCoolantTemperatureRaw;
   Rte_ResultFP_EngineECU_CS_IF_CT_RAW_SetCoolantTemperatureRaw Result_SetCoolantTemperatureRaw;
} Rte_PDS_EngineECU_CS_IF_CT_RAW_R;

typedef Rte_PDS_EngineECU_CS_IF_CT_RAW_R Rte_PDS_EngineECU_CS_IF_CT_RAW_RA[1];

typedef struct {
   Rte_CallFP_EngineECU_CS_IF_GEAR_SetGearLevel   Call_SetGearLevel;
   Rte_ResultFP_EngineECU_CS_IF_GEAR_SetGearLevel Result_SetGearLevel;
} Rte_PDS_EngineECU_CS_IF_GEAR_R;

typedef Rte_PDS_EngineECU_CS_IF_GEAR_R Rte_PDS_EngineECU_CS_IF_GEAR_RA[1];

typedef struct {
   Rte_CallFP_EngineECU_CS_IF_GYRO_RAW_SetGyroRaw   Call_SetGyroRaw;
   Rte_ResultFP_EngineECU_CS_IF_GYRO_RAW_SetGyroRaw Result_SetGyroRaw;
} Rte_PDS_EngineECU_CS_IF_GYRO_RAW_R;

typedef Rte_PDS_EngineECU_CS_IF_GYRO_RAW_R Rte_PDS_EngineECU_CS_IF_GYRO_RAW_RA[1];

typedef struct {
   uint8 _dummy;
} Rte_PDS_EngineECU_CS_IF_SET_FUELCUT_STATE_P;

typedef Rte_PDS_EngineECU_CS_IF_SET_FUELCUT_STATE_P Rte_PDS_EngineECU_CS_IF_SET_FUELCUT_STATE_PA[1];

typedef struct {
   Rte_CallFP_EngineECU_CS_IF_VS_RAW_SetVehicleSpeedRaw   Call_SetVehicleSpeedRaw;
   Rte_ResultFP_EngineECU_CS_IF_VS_RAW_SetVehicleSpeedRaw Result_SetVehicleSpeedRaw;
} Rte_PDS_EngineECU_CS_IF_VS_RAW_R;

typedef Rte_PDS_EngineECU_CS_IF_VS_RAW_R Rte_PDS_EngineECU_CS_IF_VS_RAW_RA[1];

struct Rte_CDS_EngineECU {
   /* Port Data Structure Arrays */
   Rte_PDS_EngineECU_CS_IF_ACCELERATE_RA                       RPortAccelerate;
   Rte_PDS_EngineECU_CS_IF_BRAKE_RA                            RPortBrake;
   Rte_PDS_EngineECU_CS_IF_CP_RAW_RA                           RPortCPRAW;
   Rte_PDS_EngineECU_CS_IF_CT_RAW_RA                           RPortCTRAW;
   Rte_PDS_EngineECU_CS_IF_GEAR_RA                             RPortGear;
   Rte_PDS_EngineECU_CS_IF_GYRO_RAW_RA                         RPortGyroRAW;
   Rte_PDS_EngineECU_CS_IF_SET_FUELCUT_STATE_PA                PPortSetFuelCutState;
   Rte_PDS_EngineECU_CS_IF_VS_RAW_RA                           RPortVSRAW;
   /* Inter-runnable variable API */
   Rte_IrvWriteFP_EngineECU_RE_SetFuelCutState_eX_FuelCutState IrvWrite_RE_SetFuelCutState_eX_FuelCutState;
};
typedef struct Rte_CDS_EngineECU Rte_CDS_EngineECU;

/* END: SWC types (core visible) */

/*******************************************************
 ***
 *** Private Types
 ***
 *******************************************************/

/* BEGIN: SWC types (private) */
#ifndef RTE_CORE
typedef P2CONST(Rte_PDS_EngineECU_CS_IF_ACCELERATE_R, AUTOMATIC, RTE_CONST) Rte_PortHandle_CS_IF_ACCELERATE_R;

typedef P2CONST(Rte_PDS_EngineECU_CS_IF_BRAKE_R, AUTOMATIC, RTE_CONST) Rte_PortHandle_CS_IF_BRAKE_R;

typedef P2CONST(Rte_PDS_EngineECU_CS_IF_CP_RAW_R, AUTOMATIC, RTE_CONST) Rte_PortHandle_CS_IF_CP_RAW_R;

typedef P2CONST(Rte_PDS_EngineECU_CS_IF_CT_RAW_R, AUTOMATIC, RTE_CONST) Rte_PortHandle_CS_IF_CT_RAW_R;

typedef P2CONST(Rte_PDS_EngineECU_CS_IF_GEAR_R, AUTOMATIC, RTE_CONST) Rte_PortHandle_CS_IF_GEAR_R;

typedef P2CONST(Rte_PDS_EngineECU_CS_IF_GYRO_RAW_R, AUTOMATIC, RTE_CONST) Rte_PortHandle_CS_IF_GYRO_RAW_R;

typedef P2CONST(Rte_PDS_EngineECU_CS_IF_SET_FUELCUT_STATE_P, AUTOMATIC, RTE_CONST) Rte_PortHandle_CS_IF_SET_FUELCUT_STATE_P;

typedef P2CONST(Rte_PDS_EngineECU_CS_IF_VS_RAW_R, AUTOMATIC, RTE_CONST) Rte_PortHandle_CS_IF_VS_RAW_R;

#endif /* RTE_CORE */

/* END: SWC types (private) */

/*******************************************************
 ***
 *** Instance Declarations
 ***
 *******************************************************/

#define RTE_START_SEC_CONST_UNSPECIFIED
#include "MemMap.h" /*lint !e537 permit multiple inclusion */
extern CONST(struct Rte_CDS_EngineECU, RTE_CONST) Rte_Instance_SwComponentPrototype_EngineECU;
#define RTE_STOP_SEC_CONST_UNSPECIFIED
#include "MemMap.h" /*lint !e537 permit multiple inclusion */


#ifndef RTE_CORE
typedef CONSTP2CONST(struct Rte_CDS_EngineECU, AUTOMATIC, RTE_CONST) Rte_Instance;
#endif /* RTE_CORE */

/*******************************************************
 ***
 *** API Mapping macros
 ***
 *******************************************************/

/* API Mapping Macros */
#ifndef RTE_CORE

#define RTE_START_SEC_CODE
#include "MemMap.h" /*lint !e537 permit multiple inclusion */
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Call_SwComponentPrototype_EngineECU_RPortAccelerate_SetAccelerateLevel(VAR(uint8, AUTOMATIC) accelerateLevel);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Call_SwComponentPrototype_EngineECU_RPortBrake_SetBrakeLevel(VAR(uint8, AUTOMATIC) brakeLevel);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Call_SwComponentPrototype_EngineECU_RPortCPRAW_SetCrankshaftPositionRaw(VAR(float32, AUTOMATIC) crankshaftPositionRaw);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Call_SwComponentPrototype_EngineECU_RPortCTRAW_SetCoolantTemperatureRaw(VAR(float32, AUTOMATIC) coolantTemperatureRaw);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Call_SwComponentPrototype_EngineECU_RPortGear_SetGearLevel(VAR(uint8, AUTOMATIC) gearLevel);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Call_SwComponentPrototype_EngineECU_RPortGyroRAW_SetGyroRaw(VAR(float32, AUTOMATIC) gyroRaw);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Call_SwComponentPrototype_EngineECU_RPortVSRAW_SetVehicleSpeedRaw(VAR(float32, AUTOMATIC) vehicleSpeedRaw);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Result_SwComponentPrototype_EngineECU_RPortAccelerate_SetAccelerateLevel(void);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Result_SwComponentPrototype_EngineECU_RPortBrake_SetBrakeLevel(void);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Result_SwComponentPrototype_EngineECU_RPortCPRAW_SetCrankshaftPositionRaw(void);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Result_SwComponentPrototype_EngineECU_RPortCTRAW_SetCoolantTemperatureRaw(void);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Result_SwComponentPrototype_EngineECU_RPortGear_SetGearLevel(void);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Result_SwComponentPrototype_EngineECU_RPortGyroRAW_SetGyroRaw(void);
FUNC(VAR(Std_ReturnType, AUTOMATIC), RTE_CODE) Rte_Result_SwComponentPrototype_EngineECU_RPortVSRAW_SetVehicleSpeedRaw(void);
#define RTE_STOP_SEC_CODE
#include "MemMap.h" /*lint !e537 permit multiple inclusion */
#if defined(RTE_PRV_ALL_API) || defined(RTE_RUNNABLEAPI_RE_SendData)
#define Rte_Call_RPortAccelerate_SetAccelerateLevel( self, accelerateLevel ) (Rte_Call_SwComponentPrototype_EngineECU_RPortAccelerate_SetAccelerateLevel(accelerateLevel))
#endif
#if defined(RTE_PRV_ALL_API) || defined(RTE_RUNNABLEAPI_RE_SendData)
#define Rte_Call_RPortBrake_SetBrakeLevel( self, brakeLevel ) (Rte_Call_SwComponentPrototype_EngineECU_RPortBrake_SetBrakeLevel(brakeLevel))
#endif
#if defined(RTE_PRV_ALL_API) || defined(RTE_RUNNABLEAPI_RE_SendData)
#define Rte_Call_RPortCPRAW_SetCrankshaftPositionRaw( self, crankshaftPositionRaw ) (Rte_Call_SwComponentPrototype_EngineECU_RPortCPRAW_SetCrankshaftPositionRaw(crankshaftPositionRaw))
#endif
#if defined(RTE_PRV_ALL_API) || defined(RTE_RUNNABLEAPI_RE_SendData)
#define Rte_Call_RPortCTRAW_SetCoolantTemperatureRaw( self, coolantTemperatureRaw ) (Rte_Call_SwComponentPrototype_EngineECU_RPortCTRAW_SetCoolantTemperatureRaw(coolantTemperatureRaw))
#endif
#if defined(RTE_PRV_ALL_API) || defined(RTE_RUNNABLEAPI_RE_SendData)
#define Rte_Call_RPortGear_SetGearLevel( self, gearLevel ) (Rte_Call_SwComponentPrototype_EngineECU_RPortGear_SetGearLevel(gearLevel))
#endif
#if defined(RTE_PRV_ALL_API) || defined(RTE_RUNNABLEAPI_RE_SendData)
#define Rte_Call_RPortGyroRAW_SetGyroRaw( self, gyroRaw ) (Rte_Call_SwComponentPrototype_EngineECU_RPortGyroRAW_SetGyroRaw(gyroRaw))
#endif
#if defined(RTE_PRV_ALL_API) || defined(RTE_RUNNABLEAPI_RE_SendData)
#define Rte_Call_RPortVSRAW_SetVehicleSpeedRaw( self, vehicleSpeedRaw ) (Rte_Call_SwComponentPrototype_EngineECU_RPortVSRAW_SetVehicleSpeedRaw(vehicleSpeedRaw))
#endif
#define Rte_Result_RPortAccelerate_SetAccelerateLevel( self ) (Rte_Result_SwComponentPrototype_EngineECU_RPortAccelerate_SetAccelerateLevel())
#define Rte_Result_RPortBrake_SetBrakeLevel( self ) (Rte_Result_SwComponentPrototype_EngineECU_RPortBrake_SetBrakeLevel())
#define Rte_Result_RPortCPRAW_SetCrankshaftPositionRaw( self ) (Rte_Result_SwComponentPrototype_EngineECU_RPortCPRAW_SetCrankshaftPositionRaw())
#define Rte_Result_RPortCTRAW_SetCoolantTemperatureRaw( self ) (Rte_Result_SwComponentPrototype_EngineECU_RPortCTRAW_SetCoolantTemperatureRaw())
#define Rte_Result_RPortGear_SetGearLevel( self ) (Rte_Result_SwComponentPrototype_EngineECU_RPortGear_SetGearLevel())
#define Rte_Result_RPortGyroRAW_SetGyroRaw( self ) (Rte_Result_SwComponentPrototype_EngineECU_RPortGyroRAW_SetGyroRaw())
#define Rte_Result_RPortVSRAW_SetVehicleSpeedRaw( self ) (Rte_Result_SwComponentPrototype_EngineECU_RPortVSRAW_SetVehicleSpeedRaw())

/* Rte_Irv API (explicit access) */
#define Rte_IrvWrite_RE_SetFuelCutState_eX_FuelCutState(self, data) ((self)->IrvWrite_RE_SetFuelCutState_eX_FuelCutState(data))

/* Rte_Port API */
#define Rte_Port_RPortAccelerate(self) ((Rte_PortHandle_CS_IF_ACCELERATE_R)&((self)->RPortAccelerate[0]))
#define Rte_Port_RPortBrake(self) ((Rte_PortHandle_CS_IF_BRAKE_R)&((self)->RPortBrake[0]))
#define Rte_Port_RPortCPRAW(self) ((Rte_PortHandle_CS_IF_CP_RAW_R)&((self)->RPortCPRAW[0]))
#define Rte_Port_RPortCTRAW(self) ((Rte_PortHandle_CS_IF_CT_RAW_R)&((self)->RPortCTRAW[0]))
#define Rte_Port_RPortGear(self) ((Rte_PortHandle_CS_IF_GEAR_R)&((self)->RPortGear[0]))
#define Rte_Port_RPortGyroRAW(self) ((Rte_PortHandle_CS_IF_GYRO_RAW_R)&((self)->RPortGyroRAW[0]))
#define Rte_Port_PPortSetFuelCutState(self) ((Rte_PortHandle_CS_IF_SET_FUELCUT_STATE_P)&((self)->PPortSetFuelCutState[0]))
#define Rte_Port_RPortVSRAW(self) ((Rte_PortHandle_CS_IF_VS_RAW_R)&((self)->RPortVSRAW[0]))

/* Rte_NPorts API */
#define Rte_NPorts_CS_IF_ACCELERATE_R(self) ((uint8)1)
#define Rte_NPorts_CS_IF_BRAKE_R(self) ((uint8)1)
#define Rte_NPorts_CS_IF_CP_RAW_R(self) ((uint8)1)
#define Rte_NPorts_CS_IF_CT_RAW_R(self) ((uint8)1)
#define Rte_NPorts_CS_IF_GEAR_R(self) ((uint8)1)
#define Rte_NPorts_CS_IF_GYRO_RAW_R(self) ((uint8)1)
#define Rte_NPorts_CS_IF_SET_FUELCUT_STATE_P(self) ((uint8)1)
#define Rte_NPorts_CS_IF_VS_RAW_R(self) ((uint8)1)

/* Rte_Ports API */
#define Rte_Ports_CS_IF_ACCELERATE_R(self) ((Rte_PortHandle_CS_IF_ACCELERATE_R)&((self)->RPortAccelerate[0]))
#define Rte_Ports_CS_IF_BRAKE_R(self) ((Rte_PortHandle_CS_IF_BRAKE_R)&((self)->RPortBrake[0]))
#define Rte_Ports_CS_IF_CP_RAW_R(self) ((Rte_PortHandle_CS_IF_CP_RAW_R)&((self)->RPortCPRAW[0]))
#define Rte_Ports_CS_IF_CT_RAW_R(self) ((Rte_PortHandle_CS_IF_CT_RAW_R)&((self)->RPortCTRAW[0]))
#define Rte_Ports_CS_IF_GEAR_R(self) ((Rte_PortHandle_CS_IF_GEAR_R)&((self)->RPortGear[0]))
#define Rte_Ports_CS_IF_GYRO_RAW_R(self) ((Rte_PortHandle_CS_IF_GYRO_RAW_R)&((self)->RPortGyroRAW[0]))
#define Rte_Ports_CS_IF_SET_FUELCUT_STATE_P(self) ((Rte_PortHandle_CS_IF_SET_FUELCUT_STATE_P)&((self)->PPortSetFuelCutState[0]))
#define Rte_Ports_CS_IF_VS_RAW_R(self) ((Rte_PortHandle_CS_IF_VS_RAW_R)&((self)->RPortVSRAW[0]))

#endif /* RTE_CORE */

/*******************************************************
 ***
 *** RE Prototypes
 ***
 *******************************************************/

#define EngineECU_START_SEC_CODE
#include "EngineECU_MemMap.h" /*lint !e537 permit multiple inclusion */
FUNC(void, EngineECU_CODE) SetFuelCutState(CONST(Rte_SelfType_EngineECU, AUTOMATIC) self,
                                           VAR(uint8, AUTOMATIC) fuelCutState);
#define EngineECU_STOP_SEC_CODE
#include "EngineECU_MemMap.h" /*lint !e537 permit multiple inclusion */
#define EngineECU_START_SEC_CODE
#include "EngineECU_MemMap.h" /*lint !e537 permit multiple inclusion */
FUNC(void, EngineECU_CODE) SendData(CONST(Rte_SelfType_EngineECU, AUTOMATIC) self);
#define EngineECU_STOP_SEC_CODE
#include "EngineECU_MemMap.h" /*lint !e537 permit multiple inclusion */

/* Initial values for data element prototypes */

#ifdef __cplusplus
} /* extern C */
#endif /* __cplusplus */

#endif /* RTE_ENGINEECU_H */
