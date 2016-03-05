/** \file         VECU_SystemClock_ISR.c
  *
  * \brief        RTE tick counter handling for RTPC-EVE ( target platform: VRTA-ux/RTPC )
  *
  * [$crn:2007:dox
  * \copyright Copyright 2012 ETAS GmbH
  * $]
  *
  * \note         PLATFORM DEPENDANT [yes/no]: yes
  *
  * \note         TO BE CHANGED BY USER [yes/no]: no
  *
  * $Id: VECU_SystemClock_ISR.c 2379 2012-08-10 12:55:57Z pin9fe $
  */

#include "Os_VAP.h"
#include "Main_Cfg.h"
#include "Rte_Const.h"


/* The rate at which VECU_SYSTEMCLOCK_ISR is called, used to control when the counters are ticked*/
#define VECU_SYSTEMCLOCK_INTERVAL_US 1000 /* by default 1 ms*/
#define USECONDS_TO_TICKS( USECS )  (USECS / VECU_SYSTEMCLOCK_INTERVAL_US) /* convert US to ticks*/


/* In the default configuration, this ISR is driven by the
 * VECU_SystemClock defined in VECU_SystemClock.cpp.
 * You may add further actions here as well as you may choose
 * a different method to stimulate the RTE tick counter in
 * VRTA OS configuration.
 */
VAP_ISR(VECU_SYSTEMCLOCK_ISR)
{
#ifdef DEFAULT_BSW_TASK
    Os_IncrementCounter_BswCounter();
#endif
#ifdef ENABLE_XCP
    Os_IncrementCounter_XCP_Counter();
#endif
    Os_IncrementCounter_Rte_TickCounter();

#ifdef RTE_MSITABLE_SIZE
   static uint16 mf_count = USECONDS_TO_TICKS( RTE_MAINFUNCTION_PERIOD_US );
   if ( 0 == --mf_count )
   {
      mf_count = USECONDS_TO_TICKS( RTE_MAINFUNCTION_PERIOD_US );
      Rte_MainFunction();
   }
#endif /* RTE_MSITABLE_SIZE */
}
