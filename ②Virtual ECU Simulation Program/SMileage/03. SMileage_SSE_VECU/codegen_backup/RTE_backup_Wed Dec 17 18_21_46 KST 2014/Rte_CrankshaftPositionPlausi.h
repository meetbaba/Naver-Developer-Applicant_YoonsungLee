/** @file     Rte_CrankshaftPositionPlausi.h
  *
  * @brief    Application header file
  *
  * @note     AUTOMATICALLY GENERATED FILE! DO NOT EDIT!
  *
  * @note     Generated by ETAS GmbH  RTA-RTE v5.4.1  (R4.0 backend version: v7.1.31 (Build 33134))
  *           at Wed Dec 17 18:15:49 2014
  */

#ifndef RTE_CRANKSHAFTPOSITIONPLAUSI_H
#define RTE_CRANKSHAFTPOSITIONPLAUSI_H

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
#include "Rte_CrankshaftPositionPlausi_Type.h"

#include "Rte_DataHandleType.h"

#ifdef __cplusplus
extern "C" {
#endif /* __cplusplus */

#if !defined(RTE_RUNNABLEAPI_RE_CrankshaftPositionPlausi)
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
   uint8 _dummy;
} Rte_PDS_CrankshaftPositionPlausi_CS_IF_CP_RAW_P;

typedef Rte_PDS_CrankshaftPositionPlausi_CS_IF_CP_RAW_P Rte_PDS_CrankshaftPositionPlausi_CS_IF_CP_RAW_PA[1];

typedef struct {
   uint8 _dummy;
} Rte_PDS_CrankshaftPositionPlausi_SR_IF_CP_PLS_P;

typedef Rte_PDS_CrankshaftPositionPlausi_SR_IF_CP_PLS_P Rte_PDS_CrankshaftPositionPlausi_SR_IF_CP_PLS_PA[1];

struct Rte_CDS_CrankshaftPositionPlausi {
   /* Data handles */
   P2VAR(Rte_DE_float32                                  , AUTOMATIC, RTE_DATA) RE_CrankshaftPositionPlausi_PPortCPPLS_crankshaftPosition_pls;
   /* Port Data Structure Arrays */
   Rte_PDS_CrankshaftPositionPlausi_CS_IF_CP_RAW_PA PPortCPRAW;
   Rte_PDS_CrankshaftPositionPlausi_SR_IF_CP_PLS_PA PPortCPPLS;
};
typedef struct Rte_CDS_CrankshaftPositionPlausi Rte_CDS_CrankshaftPositionPlausi;

/* END: SWC types (core visible) */

/*******************************************************
 ***
 *** Private Types
 ***
 *******************************************************/

/* BEGIN: SWC types (private) */
#ifndef RTE_CORE
typedef P2CONST(Rte_PDS_CrankshaftPositionPlausi_CS_IF_CP_RAW_P, AUTOMATIC, RTE_CONST) Rte_PortHandle_CS_IF_CP_RAW_P;

typedef P2CONST(Rte_PDS_CrankshaftPositionPlausi_SR_IF_CP_PLS_P, AUTOMATIC, RTE_CONST) Rte_PortHandle_SR_IF_CP_PLS_P;

#endif /* RTE_CORE */

/* END: SWC types (private) */

/*******************************************************
 ***
 *** Instance Declarations
 ***
 *******************************************************/

#define RTE_START_SEC_CONST_UNSPECIFIED
#include "MemMap.h" /*lint !e537 permit multiple inclusion */
extern CONST(struct Rte_CDS_CrankshaftPositionPlausi, RTE_CONST) Rte_Instance_SwComponentPrototype_CrankshaftPositionPlausi;
#define RTE_STOP_SEC_CONST_UNSPECIFIED
#include "MemMap.h" /*lint !e537 permit multiple inclusion */


#ifndef RTE_CORE
typedef CONSTP2CONST(struct Rte_CDS_CrankshaftPositionPlausi, AUTOMATIC, RTE_CONST) Rte_Instance;
#endif /* RTE_CORE */

/*******************************************************
 ***
 *** API Mapping macros
 ***
 *******************************************************/

/* API Mapping Macros */
#ifndef RTE_CORE
#if defined(RTE_PRV_ALL_API) || defined(RTE_RUNNABLEAPI_RE_CrankshaftPositionPlausi)
/* Inline write optimization; Rte_IWriteRef_RE_CrankshaftPositionPlausi_PPortCPPLS_crankshaftPosition_pls to implicit access macro */
#define Rte_IWriteRef_RE_CrankshaftPositionPlausi_PPortCPPLS_crankshaftPosition_pls( self )  ( &((self->RE_CrankshaftPositionPlausi_PPortCPPLS_crankshaftPosition_pls)->value) )
#endif
#if defined(RTE_PRV_ALL_API) || defined(RTE_RUNNABLEAPI_RE_CrankshaftPositionPlausi)
/* Inline write optimization; Rte_IWrite_RE_CrankshaftPositionPlausi_PPortCPPLS_crankshaftPosition_pls to implicit access macro */
#define Rte_IWrite_RE_CrankshaftPositionPlausi_PPortCPPLS_crankshaftPosition_pls( self, data )  ( ((self->RE_CrankshaftPositionPlausi_PPortCPPLS_crankshaftPosition_pls)->value) = (data) )
#endif

/* Rte_Port API */
#define Rte_Port_PPortCPRAW(self) ((Rte_PortHandle_CS_IF_CP_RAW_P)&((self)->PPortCPRAW[0]))
#define Rte_Port_PPortCPPLS(self) ((Rte_PortHandle_SR_IF_CP_PLS_P)&((self)->PPortCPPLS[0]))

/* Rte_NPorts API */
#define Rte_NPorts_CS_IF_CP_RAW_P(self) ((uint8)1)
#define Rte_NPorts_SR_IF_CP_PLS_P(self) ((uint8)1)

/* Rte_Ports API */
#define Rte_Ports_CS_IF_CP_RAW_P(self) ((Rte_PortHandle_CS_IF_CP_RAW_P)&((self)->PPortCPRAW[0]))
#define Rte_Ports_SR_IF_CP_PLS_P(self) ((Rte_PortHandle_SR_IF_CP_PLS_P)&((self)->PPortCPPLS[0]))

#endif /* RTE_CORE */

/*******************************************************
 ***
 *** RE Prototypes
 ***
 *******************************************************/

#define CrankshaftPositionPlausi_START_SEC_CODE
#include "CrankshaftPositionPlausi_MemMap.h" /*lint !e537 permit multiple inclusion */
FUNC(void, CrankshaftPositionPlausi_CODE) CrankshaftPositionPlausi(CONST(Rte_SelfType_CrankshaftPositionPlausi, AUTOMATIC) self,
                                                                   VAR(float32, AUTOMATIC) crankshaftPositionRaw);
#define CrankshaftPositionPlausi_STOP_SEC_CODE
#include "CrankshaftPositionPlausi_MemMap.h" /*lint !e537 permit multiple inclusion */

/* Initial values for data element prototypes */

#ifdef __cplusplus
} /* extern C */
#endif /* __cplusplus */

#endif /* RTE_CRANKSHAFTPOSITIONPLAUSI_H */
