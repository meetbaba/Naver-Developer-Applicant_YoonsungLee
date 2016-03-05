#include "vrtaCore.h"
#include "vrtaSampleDevices.h"
/* --------------------------------------------------------------------------- */
/*   vrtaSynchronizableClock                                                           */
/* --------------------------------------------------------------------------- */
class vrtaSyncClock : public vrtaClock {

private:
  bool m_FreeRunning;
  bool m_TickLimitActionReceived;
  vrtaUInt m_TickLimit;
  vrtaUInt m_TicksRemaining;
  vrtaUInt m_TicksCount;
  void TickClockWithLimit(void);

protected:
	vrtaTextPtr ActionInfo();
	vrtaTextPtr EventInfo();
  
public:
  vrtaSyncClock(const vrtaTextPtr name, vrtaUInt interval, bool timer_thread= true);
  vrtaSyncClock(const vrtaTextPtr name, unsigned interval, const vrtaOptStringlistPtr info,
				const vrtaOptStringlistPtr events,const vrtaOptStringlistPtr actions,bool timer_thread = true);
  // This method gets called from the real free-running timer. Ticks are ignored
  // unless we are free-running.
  void Tick(void);
  // This method gets called from software as often as it likes. It only
  // has an effect when we not free-running.
  // Typically it will be called repeatedly in the idle loop, so the clock 
  // will run as fast as it can.
  // This clock does not get scaled.
  vrtaBoolean InjectTick(void);
  vrtaErrType OnAction(const vrtaAction *action);
  vrtaErrType AsyncGetState(vrtaEvent *event);
};
