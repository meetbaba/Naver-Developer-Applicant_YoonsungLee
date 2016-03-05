/** \file         VECU_SystemClock.h
  *
  * \brief        System clock functions for RTPC-EVE ( target platform: VRTA-ux/RTPC )
  *
  * [$crn:2007:dox
  * \copyright Copyright 2012 ETAS GmbH
  * $]
  *
  * \note         PLATFORM DEPENDANT [yes/no]: yes
  *
  * \note         TO BE CHANGED BY USER [yes/no]: no
  *
  * $Id: VECU_SystemClock.h 2379 2012-08-10 12:55:57Z pin9fe $
  */

#include "vrtaCore.h"
 
#ifdef __cplusplus
extern "C" {
#endif
 
void VECU_SystemClock_Init();
void VECU_SystemClock_Start();

vrtaUInt VECU_SystemClock_GetValue();
 
vrtaBoolean AdaptiveTick(void);


#ifdef __cplusplus
}
#endif
