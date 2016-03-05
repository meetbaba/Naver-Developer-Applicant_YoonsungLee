/* Copyrights 2012. All rights reserved by ETAS GmbH, STUTTGART.

 * @file        EveVrtaSyncClock.cpp
 *
 * @brief		This Synchronization Clock is derived from the vrtaClock and have a minimum resolution of 1ms.
				This Clock Can Run in Real Time and Adaptive Time.
				User can provide a Tick Limit(n). The clock will run till n ticks and passes an event back.Ticks are ignored until the next ClockLimit action is scheduled.
				If the Tick Limit is set to 0 then the clock will run continously
 *
 *
 *
 * @author		Vino Duraipandian
 *
 * @Description
 ************************************************************************************************************
 *  Edition History:                                                                                        *
 *  ================                                                                                        *
 * Rev      Date        Comment                                                                      By     *
 * -------- ----------  ------------------------------------------------------------------------    ----    *
 * 																											*
 ************************************************************************************************************/
 
#include "eveVrtaSyncClock.hpp"
#define VRTA_SYNCH_CLOCK_VERSION "0.1"

enum sync_clock_actions {ENABLE_ADAPTIVE_CLOCK = 5U, ENABLE_REALTIME_CLOCK, SET_CLOCK_LIMIT} ;
enum sync_clock_events {GET_TIME_REMAINING = 4U} ;
 
vrtaSyncClock::vrtaSyncClock(const vrtaTextPtr name, vrtaUInt interval, bool timer_thread)
/* --------------------------------------------------------------------------- */
:  vrtaClock(name, interval,
    (vrtaTextPtr)"Type=Clock.Synchronizable\nVersion="VRTA_SYNCH_CLOCK_VERSION"\n", // Info
    EventInfo(),  // Events
    ActionInfo(), // Actions
	timer_thread
  ) {
  m_FreeRunning = true;
  m_TickLimit = 0;
  m_TicksRemaining =0;
  m_TicksCount = 0;
  m_TickLimitActionReceived = false;
  
};

vrtaSyncClock::vrtaSyncClock(const vrtaTextPtr name, unsigned interval, const vrtaOptStringlistPtr info,
	const vrtaOptStringlistPtr events,const vrtaOptStringlistPtr actions,bool timer_thread)
: vrtaClock(name, interval, info, events, actions, timer_thread){
  m_FreeRunning = true;
  m_TickLimit = 0;
  m_TicksRemaining =0;
  m_TicksCount = 0;
  m_TickLimitActionReceived = false;
 
};

vrtaTextPtr vrtaSyncClock::ActionInfo() {
    return (vrtaTextPtr)"Interval=%u\nScale=%u,%u\nStart\nStop\nAdaptive\nFreeRunning\nTickLimit=%u:32\n";
}

vrtaTextPtr vrtaSyncClock::EventInfo() {
    return (vrtaTextPtr)"Interval=%u\nScale=%u,%u\nRunning=%u:;0;1\nTicksRemaining=%u:32\n";
}

/*
  This method uses the TickLimit provided by the user.
  The clock ticks until the limit and passes an event back after the limit is reached.
  Ticks are ignored until the next TickLimit
  The method is used only when TickLimit is greater than 0.
*/
 
void vrtaSyncClock::TickClockWithLimit(void){
	if(m_TicksRemaining > 0){
		vrtaClock::Tick();
		m_TicksRemaining --;
	}else if(m_TickLimitActionReceived){ 
		m_TickLimitActionReceived = false;
		vrtaEvent event;
		ReadState(&event, GET_TIME_REMAINING);
		RaiseEvent(event);
	}

}

// This method gets called from the real free-running timer. Ticks are ignored
// unless we are free-running.
void vrtaSyncClock::Tick(void) {
	if (m_FreeRunning) {
		if(m_TickLimit == 0){ 
			vrtaClock::Tick();
		}else{
			TickClockWithLimit();
		}
	}
}


// This method gets called from software as often as it likes. It only
// has an effect when we not free-running.
// Typically it will be called repeatedly in the idle loop, so the clock
// will run as fast as it can.
// This clock does not get scaled.
vrtaBoolean vrtaSyncClock::InjectTick(void) {
  if (!m_FreeRunning) {
    if (m_Running) {
		if(m_TickLimit == 0){
			vrtaClock::Tick();
		}else{
			TickClockWithLimit();
		}
    }
  }
  return !m_FreeRunning;
}

vrtaErrType vrtaSyncClock::OnAction(const vrtaAction *action) {
  if (action->devAction == ENABLE_ADAPTIVE_CLOCK) {
    m_FreeRunning = 0;
    return OKAction(action);
  } else if (action->devAction == ENABLE_REALTIME_CLOCK) {
    m_FreeRunning = 1;
    return OKAction(action);
  }else if(action->devAction == SET_CLOCK_LIMIT){
	m_TickLimit = uVal(action, 0);
	m_TicksRemaining = m_TickLimit;
	m_TickLimitActionReceived = true;
	return OKAction(action);
  }
  return vrtaClock::OnAction(action);
  
}

/* --------------------------------------------------------------------------- */
vrtaErrType vrtaSyncClock::AsyncGetState(vrtaEvent *event)
/* --------------------------------------------------------------------------- */ {
  /*  Called when a get state request arrives. */
  /*  Can be called from ANY thread, but is not reentrant */
  if (event->devEvent == GET_TIME_REMAINING) {
	Set_uVal(event, m_TicksRemaining);
	return OKState(event);
  } 
  return vrtaClock::AsyncGetState(event);
  
}
