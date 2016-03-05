/** \file         Os_Hooks.c
  *
  * \brief        General service interfaces (Startup/Idle/...) for RTPC-EVE ( target platform: VRTA-ux/RTPC )
  *
  * [$crn:2007:dox
  * \copyright Copyright 2012 ETAS GmbH
  * $]
  *
  * \note         PLATFORM DEPENDANT [yes/no]: yes
  *
  * \note         TO BE CHANGED BY USER [yes/no]: no
  *
  * $Id: Os_Hooks.c 2379 2012-08-10 12:55:57Z pin9fe $
  */

#include "Os.h"

#include <Os.h>
#include "Main_Cfg.h"
#include "target.h"
#include "Rte.h"
#include "Vrta_Cfg.h"

/* ------------------------------------------------------------------------- */
/* This is called during StartOS() and before RTA-OS starts the scheduler.
 * It is the safest place to enable interrupt sources that have been initialized
 * before StartOS() was called. */
FUNC(void, OS_APPL_CODE)
StartupHook(void)
{
    TargetEnableInterrupts();
}

/* ------------------------------------------------------------------------- */
FUNC(void, OS_APPL_CODE) ShutdownHook(StatusType Error)
{
    /* End of example */
   // printf("Shutdown\n");
}

/* ------------------------------------------------------------------------- */
FUNC(boolean,OS_APPL_CODE)
Os_Cbk_Idle(void)
{
#ifndef USES_BSW
    static boolean init = FALSE;

    if ( !init )
    {
        /* Start the RTE */
        Rte_Start();

        /* Enable interrupts used that drive periodic runnables (e.g. schedule
         * table, periodic alarms, etc.). This should be done only after
         * Rte_Start to ensure that no runnable is triggered until the RTE is
         * initialized. */
        init = TRUE;
    }
#endif

    while(!vrtaIsAppFinished()) {
        rtos_log_flush();
	/* To support VRTA Implementation */
    	vrtaActionHandler();

    	if (!AdaptiveTick()) {  /* True if AdaptiveTime selected */
        	vrtaIsIdle(5);
        }
#ifdef OS_TRACE
    	Os_CheckTraceOutput();
#endif
    }
    
	vrtaTerminate();

    return TRUE;
}


/*****************************************************************************
 * \brief Return value of the stopwatch counter
 *
 * \return Current tick value of OS stopwatch
 */

FUNC(Os_StopwatchTickType, OS_APPL_CODE)
Os_Cbk_GetStopwatch( void )
{
#ifdef OS_TRACE
	return (Os_StopwatchTickType)vrtaReadHPTime(OSSWTICKSPERSECOND);
#else
    return (Os_StopwatchTickType) (1000 * TargetReadClock());
#endif /* defined(OS_TRACE) */
}

/*****************************************************************************
 * \brief Os_Cbk_CheckMemoryAccess dummy implementation.
 */

FUNC(AccessType, OS_APPL_CODE)
Os_Cbk_CheckMemoryAccess(ApplicationType Application, TaskType TaskID, ISRType ISRID, MemoryStartAddressType Address, MemorySizeType Size)
{
    (void)Application;
    (void)TaskID;
    (void)ISRID;
    (void)Address;
    (void)Size;

    return (AccessType)(0U);
}
