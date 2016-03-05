/** \file         Os_VAP.h
  *
  * \brief        Task and ISR macros for RTPC-EVE ( target platform: VRTA-ux/RTPC )
  *
  * [$crn:2007:dox
  * \copyright Copyright 2012 ETAS GmbH
  * $]
  *
  * \note         PLATFORM DEPENDANT [yes/no]: yes
  *
  * \note         TO BE CHANGED BY USER [yes/no]: no
  *
  * $Id: Os_VAP.h 2379 2012-08-10 12:55:57Z pin9fe $
  */

#ifndef OS_VAP_H
#define OS_VAP_H

/* Include OS structure definitions but exclude the project
 * specific configuration.
 */
#define OS_CFG_H

#include "Os.h"

/* This macro enables us to use macro expansion for x.
 * TASK(x) will do a simple concatenation, _without_
 * prior expansion of x. The indirection will trigger
 * that expansion before passing the result to TASK().
 */
#define VAP_TASK(x) TASK(x)

/* Same thing for ISRs.
 */
#define VAP_ISR(x) ISR(x)

#endif /* OS_VAP_H */
