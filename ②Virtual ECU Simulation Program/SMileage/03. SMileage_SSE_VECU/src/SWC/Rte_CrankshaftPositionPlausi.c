/** @file     Rte_CrankshaftPositionPlausi.c
  *
  * @brief    RTE Sample SWC implementation skeleton file
  *
  * @note     Generated by ETAS GmbH  RTA-RTE v5.4.1  (R4.0 backend version: v7.1.31 (Build 33134))
  *           at Tue Dec 16 20:46:28 2014
  */

#include "Rte_CrankshaftPositionPlausi.h"

/* --------------------------------------------------------------------------- */
/* RTE Event: /SoftwarePkg/SoftwareComponentPkg/CrankshaftPositionPlausi/IB_CrankshaftPositionPlausi/OIE_SetCrankshaftPositionRaw */

FUNC(void, CrankshaftPositionPlausi_CODE) CrankshaftPositionPlausi(CONST(Rte_SelfType_CrankshaftPositionPlausi, AUTOMATIC) self,
                                                                   VAR(float32, AUTOMATIC) crankshaftPositionRaw)
{
   /* ... */
	Rte_IWrite_RE_CrankshaftPositionPlausi_PPortCPPLS_crankshaftPosition_pls( self, crankshaftPositionRaw );
}

