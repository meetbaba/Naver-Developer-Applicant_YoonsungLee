/** \file         rtpc-xcp-callbacks.c
  *
  * \brief        XCP callbacks support for RTPC-EVE ( target platform: VRTA-ux/RTPC )
  *
  * [$crn:2007:dox
  * \copyright Copyright 2012 ETAS GmbH
  * $]
  *
  * \note         PLATFORM DEPENDANT [yes/no]: yes
  *
  * \note         TO BE CHANGED BY USER [yes/no]: no
  *
  * $Id: rtpc-xcp-callbacks.c 2379 2012-08-10 12:55:57Z pin9fe $
  */

#include "Std_Types.h"
#include "Os.h"

#include "rtpc-xcp-slave.h"

/* Callback function.
 * Returns the current XCP TimeStamp value. 
 * This value has to be in units of the XCP DAQ ticks.
 * */
unsigned int rtpc_XcpCb_GetTimestamp( void )
{
    TickType val = 0;
    GetCounterValue( Rte_TickCounter, &val );
    return val;
}

/* Callback function.
 * This is called out of the XCP driver at the end of a process.
 * If the caller is VRTA task, it should call vrtaTerminate().
 * */
void rtpc_XcpCb_OsekTerminate(void)
{
    if (vrtaIsAppThread())
        vrtaTerminate();
}

/* Callback function.
 * This is called out of the XCP driver to suspend the VRTA interrupts.
 * Note: A check is necessary to see if the caller is really a VRTA task
 * */
void rtpc_XcpCb_SuspendAllInterrupts(void)
{
    if (vrtaIsAppThread())
        Os_SuspendAllInterrupts();
}

/* Callback function.
 * This is called out of the XCP driver to resume the VRTA interrupts.
 * Note: A check is necessary to see if the caller is really a VRTA task.
 * */
void rtpc_XcpCb_ResumeAllInterrupts(void)
{
    if (vrtaIsAppThread())
        Os_ResumeAllInterrupts();
}

