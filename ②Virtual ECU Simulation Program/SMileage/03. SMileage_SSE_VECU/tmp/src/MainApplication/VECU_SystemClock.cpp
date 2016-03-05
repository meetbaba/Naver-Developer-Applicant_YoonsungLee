/** \file         VECU_SystemClock.cpp
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
  * $Id: VECU_SystemClock.cpp 2379 2012-08-10 12:55:57Z pin9fe $
  */

#include "Main_Cfg.h"
#include "VECU_SystemClock.h"
#include "vrtaSampleDevices.h"
#include "eveVrtaSyncClock.hpp"
#include "rtos.h"

/* Prototypes */
static void SetRealTimeClock(void);
static void SetAdaptiveClock(void);

/** VECU System Clock */
static vrtaSyncClock VECU_SystemClock((vrtaTextPtr)"VECU_SystemClock", VECU_SYSTEMCLOCK_PERIOD);

/** Periodic Counter providing number of VECU System Clock Ticks */
static vrtaUpCounter VECU_SystemClockCounter( (vrtaTextPtr)"VECU_SystemClockCounter", VECU_SystemClock);

/** Periodic Counter used by VECU_SystemClockCompare */
static vrtaUpCounter VECU_SystemClockPeriodicCounter( (vrtaTextPtr)"VECU_SystemClockPeriodicCounter", VECU_SystemClock );

/** Comparer is used to raise VECU System Clock Interrupt */
static vrtaCompare VECU_SystemClockCompare( (vrtaTextPtr)"VECU_SystemClockCompare",
                                             VECU_SystemClockPeriodicCounter,
                                             0,
                                             VECU_SYSTEMCLOCK_IRQ );

/**
 * Initialize VECU System Clock.
 *
 * This function does the initialization of the VECU System Clock
 * and its counters. The clock mode is set according to the
 * content of the file <vecuWorkspace>/var/conf.adaptive-time. The
 * default setting of the clock mode is REALTIME.
 */
extern "C" void VECU_SystemClock_Init()
{
   VECU_SystemClockPeriodicCounter.SetMin( 0 );
   VECU_SystemClockPeriodicCounter.SetMax( 0 );

   VECU_SystemClockCounter.SetMin( 0 );
#ifdef OSMAXALLOWEDVALUE_Rte_TickCounter
   VECU_SystemClockCounter.SetMax( OSMAXALLOWEDVALUE_Rte_TickCounter );
#else
   VECU_SystemClockCounter.SetMax( 0 );
#endif

   /*** Set Adaptive Mode ***/
   if( rtos_conf_get_value_int( "adaptive-time", 0 ) > 0 )
   {
      SetAdaptiveClock();
      rtos_log( LOG_NOTICE, "VECU System Clock is running in Adaptive Time mode." );
   }
   else
   {
      SetRealTimeClock();
   }
}

/**
 * Start VECU System Clock.
 *
 * This function is called TargetInit.
 */
extern "C" void VECU_SystemClock_Start()
{
   VECU_SystemClockPeriodicCounter.Stop();
   VECU_SystemClockPeriodicCounter.SetVal( 0 );
   VECU_SystemClock.Start();
   VECU_SystemClockCounter.Start();
   VECU_SystemClockPeriodicCounter.Start();
}

/**
 * Get System Clock Value.
 *
 * This function returns the value of the VECU System Clock counter.
 *
 * @return Number of System Counter Ticks
 */
extern "C" vrtaUInt VECU_SystemClock_GetValue()
{
    return VECU_SystemClockCounter.Value();
}

/**
 * Perform Adaptive Time Tick.
 *
 * This function is called by an OS Hook (see OS_Hooks.c) when the idle task
 * is active.
 *
 * @return	0: Clock mode is REALTIME, 1: Clock mode is ADAPTIVE
 */
extern "C" vrtaBoolean AdaptiveTick(void)
{
   return VECU_SystemClock.InjectTick();
}

/**
 * Set REALTIME clock mode
 *
 * This functions sends a vrtaAction to the VECU_SystemClock that switches
 * the operation mode to REALTIME.
 */
void SetRealTimeClock(void)
{
    vrtaAction act;
    act.devID         = VECU_SystemClock.GetID();
    act.devActionLen  = 0;
    act.devActionData = NULL;
    act.devAction = 6;
    vrtaSendAction(VECU_SystemClock.GetID(),&act);
}

/**
 * Set ADAPTIVE clock mode
 *
 * This functions sends a vrtaAction to the VECU_SystemClock that switches
 * the operation mode to ADAPTIVE.
 */
void SetAdaptiveClock(void){
    vrtaAction action;
    action.devID         = VECU_SystemClock.GetID();
    action.devActionLen  = 0;
    action.devActionData = NULL;
    action.devAction = 5;
    vrtaSendAction(VECU_SystemClock.GetID(),&action);
}
