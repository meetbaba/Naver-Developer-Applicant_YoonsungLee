/** @file     Rte_GyroPlausi.c
  *
  * @brief    RTE Sample SWC implementation skeleton file
  *
  * @note     Generated by ETAS GmbH  RTA-RTE v5.4.1  (R4.0 backend version: v7.1.31 (Build 33134))
  *           at Tue Dec 16 20:46:28 2014
  */

#include "Rte_GyroPlausi.h"

/* --------------------------------------------------------------------------- */
/* RTE Event: /SoftwarePkg/SoftwareComponentPkg/GyroPlausi/IB_GyroPlausi/OIE_SetGyroRaw */

FUNC(void, GyroPlausi_CODE) GyroPlausi(CONST(Rte_SelfType_GyroPlausi, AUTOMATIC) self,
                                       VAR(float32, AUTOMATIC) gyroRaw)
{
   /* ... */
	Rte_IWrite_RE_GyroPlausi_PPortGyroPLS_gyro_pls( self, gyroRaw );
}

