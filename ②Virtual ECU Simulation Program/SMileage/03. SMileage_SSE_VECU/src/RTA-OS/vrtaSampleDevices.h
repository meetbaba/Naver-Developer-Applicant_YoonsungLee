#ifndef vrtaSampleDevicesH
#define vrtaSampleDevicesH
/* --------------------------------------------------------------------------- */
/*  Defines the sample devices. */
/* --------------------------------------------------------------------------- */
#include /* no inline */ "vrtaDevice.h"
#include <signal.h>
#include <time.h>
#include <errno.h>

/* [VP_VECU  418] */
/* [VP_VECU  419] */
/* [VP_VECU  420] */

class vrtaCounter;
class vrtaCompare;
#define SAMPLE_DEVICES_VERSION "1.0.1"

/* --------------------------------------------------------------------------- */
/*  vrtaBaseClock - base class for all clocks */
/* --------------------------------------------------------------------------- */
class vrtaBaseClock : public vrtaDevice {
protected:
  static bool m_TimerThreadStarted;
  bool m_UsingTimerThread;
  vrtaCounter * m_NextCounter;

  struct s_persist {
    vrtaUInt Interval;
    vrtaUInt ScaleMult;
    vrtaUInt ScaleDiv;
  } m_Persist; /*  State that can be persisted over a reset */

  virtual void initialize_clock(void) = 0;
  virtual void ResetClock(void) = 0;

  vrtaUInt m_Interval; /* Units depend on concrete implementation */
  vrtaUInt m_ScaleMult; /*  Rate multiplier */
  vrtaUInt m_ScaleDiv; /*  Rate divisor */
  bool m_Running; /*  Running status */

  virtual void Starting(void);
  virtual void Stopping(void) {};
  virtual vrtaErrType OnAction(const vrtaAction *action);
  virtual vrtaErrType AsyncGetState(vrtaEvent *event);

  /*  Persistence */
  vrtaDataLen GetPersistentDataSize(void) {
    return sizeof (m_Persist);
  };

  vrtaByte * GetPersistentData(void) {
    m_Persist.Interval = m_Interval;
    m_Persist.ScaleMult = m_ScaleMult;
    m_Persist.ScaleDiv = m_ScaleDiv;
    return (vrtaByte *)&m_Persist;
  };
  void SetPersistentData(vrtaByte *addr, vrtaDataLen len);

public:
  /*  Constructors */
  vrtaBaseClock(const vrtaTextPtr name, vrtaUInt interval, bool timer_thread);
  vrtaBaseClock(
    const vrtaTextPtr name,
    vrtaUInt interval,
    const vrtaOptStringlistPtr info,
    const vrtaOptStringlistPtr events,
    const vrtaOptStringlistPtr actions,
    bool timer_thread
  );

  /*  Tick event */
  virtual void Tick(void);

  /*  Run-time control */
  void SetInterval(vrtaUInt interval);
  void SetScale(vrtaUInt mult, vrtaUInt div);
  virtual void Start(void);
  virtual void Stop(void) {};

  /*  Counter interface */
  vrtaCounter *Attach(vrtaCounter *counter) {
    vrtaCounter *last = m_NextCounter;
    m_NextCounter = counter;
    return last;
  };
};

/* --------------------------------------------------------------------------- */
/*  vrtaNanoClock - 1 ns intervals */
/* --------------------------------------------------------------------------- */
class vrtaNanoClock : public vrtaBaseClock {
protected:
  timer_t m_Timer;

  void initialize_clock(void);
  void ResetClock(void);
  virtual void Stopping(void);

public:
  /*  Constructors */
  vrtaNanoClock(const vrtaTextPtr name, vrtaUInt interval, bool timer_thread = true);
  vrtaNanoClock(
    const vrtaTextPtr name,
    vrtaUInt interval,
    const vrtaOptStringlistPtr info,
    const vrtaOptStringlistPtr events,
    const vrtaOptStringlistPtr actions,
    bool timer_thread
  );

  /*  Run-time control */
  virtual void Stop(void);
};

/* --------------------------------------------------------------------------- */
/*  [VP_VECU  473] vrtaClock - 1 ms intervals */
/* --------------------------------------------------------------------------- */
class vrtaClock : public vrtaNanoClock {
protected:
  void ResetClock(void);

public:
  /*  Constructors */
  vrtaClock(const vrtaTextPtr name, vrtaUInt interval, bool timer_thread = true);
  vrtaClock(
    const vrtaTextPtr name,
    vrtaUInt interval,
    const vrtaOptStringlistPtr info,
    const vrtaOptStringlistPtr events,
    const vrtaOptStringlistPtr actions,
    bool timer_thread = true
  );
};

/* --------------------------------------------------------------------------- */
/*   vrtaAdaptiveClock                                                         */
/* --------------------------------------------------------------------------- */
class vrtaAdaptiveClock : public vrtaClock {

private:
  bool m_FreeRunning;

public:
  vrtaAdaptiveClock(const vrtaTextPtr name, unsigned interval, bool timer_thread = true);

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
};

/* --------------------------------------------------------------------------- */
/*   vrtaAdaptiveNanoClock                                                     */
/* --------------------------------------------------------------------------- */
class vrtaAdaptiveNanoClock : public vrtaNanoClock {

private:
  bool m_FreeRunning;

public:
  vrtaAdaptiveNanoClock(const vrtaTextPtr name, unsigned interval, bool timer_thread = true);

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
};

/* --------------------------------------------------------------------------- */
/*                               vrtaComparable */
/* --------------------------------------------------------------------------- */

/*  Base class for all devices that can attach to one or more vrtaCompare devices */
class vrtaComparable : public vrtaDevice {
private:

  struct s_persist {
    vrtaUInt Value;
  } m_Persist; /*  State that can be persisted over a reset */

protected:
  vrtaUInt m_Value;
  vrtaCompare * m_NextCompare;
  void CheckComparators(void);

  /*  Persistence */
  vrtaDataLen GetPersistentDataSize(void) {
    return sizeof (m_Persist);
  };

  vrtaByte * GetPersistentData(void) {
    m_Persist.Value = m_Value;
    return (vrtaByte *)&m_Persist;
  };
  void SetPersistentData(vrtaByte *addr, vrtaDataLen len);

public:

  /*  Constructor */
  vrtaComparable(const vrtaTextPtr name,
    const vrtaTextPtr info,
    const vrtaOptStringlistPtr events,
    const vrtaOptStringlistPtr actions) : vrtaDevice(name, info, events, actions) {
    m_Value = 0;
    m_NextCompare = NULL;
  };

  /*  Compare interface */
  vrtaCompare * Attach(vrtaCompare *comp) {
    vrtaCompare *last = m_NextCompare;
    m_NextCompare = comp;
    return last;
  };

  virtual vrtaUInt Value(void) {
    return m_Value;
  };

  vrtaUInt GetValue(void) {
    return Value();
  };

  virtual void SetVal(vrtaUInt v) {
    m_Value = v;
  };

  void SetValue(vrtaUInt v) {
    SetVal(v);
  };
};

/* --------------------------------------------------------------------------- */
/*  [VP_VECU  482] [VP_VECU  490]        vrtaCounter, vrtaUpCounter, vrtaDownCounter */
/* --------------------------------------------------------------------------- */

class vrtaCounter : public vrtaComparable {
private:

  struct s_persist {
    vrtaUInt Value;
    vrtaUInt Min;
    vrtaUInt Max;
  } m_Persist; /*  State that can be persisted over a reset */

protected:
  vrtaUInt m_Min;
  vrtaUInt m_Max;
  bool m_Running;

  vrtaBaseClock & m_Clock;
  vrtaCounter * m_NextCounter;

  void Starting(void);
  void Stopping(void);
  vrtaErrType OnAction(const vrtaAction *action);
  vrtaErrType AsyncGetState(vrtaEvent *event);

  /*  Persistence */
  vrtaDataLen GetPersistentDataSize(void) {
    return sizeof (m_Persist);
  };

  vrtaByte * GetPersistentData(void) {
    m_Persist.Value = Value();
    m_Persist.Min = m_Min;
    m_Persist.Max = m_Max;
    return (vrtaByte *)&m_Persist;
  };
  void SetPersistentData(vrtaByte *addr, vrtaDataLen len);

public:
  /*  Constructor */
  vrtaCounter(const vrtaTextPtr name, const vrtaTextPtr info, vrtaBaseClock &clock);

  /*  Tick event */
  virtual void Tick(void) = 0;

  /*  Direct access */
  vrtaUInt Min(void) {
    return m_Min;
  };

  vrtaUInt Max(void) {
    return m_Max;
  };
  void SetMin(vrtaUInt v);
  void SetMax(vrtaUInt v);
  void SetVal(vrtaUInt v);
  void Start(void);
  void Stop(void);
};

/* --------------------------------------------------------------------------- */

/*  [VP_VECU  482] */
class vrtaUpCounter : public vrtaCounter {
public:

  /*  Constructor */
  vrtaUpCounter(const vrtaTextPtr name, vrtaBaseClock &clock) : vrtaCounter(name, (char*)"Type=Counter.Up\nVersion="SAMPLE_DEVICES_VERSION"\n", clock) {
  };

  /*  Up Tick */
  void Tick(void);
};

/* --------------------------------------------------------------------------- */

/*  [VP_VECU  490] */
class vrtaDownCounter : public vrtaCounter {
public:

  /*  Constructor */
  vrtaDownCounter(const vrtaTextPtr name, vrtaBaseClock &clock) : vrtaCounter(name, (char*)"Type=Counter.Down\nVersion="SAMPLE_DEVICES_VERSION"\n", clock) {
  };

  /*  Down Tick */
  void Tick(void);
};

/* --------------------------------------------------------------------------- */
/*  [VP_VECU  498]       vrtaCompare */
/* --------------------------------------------------------------------------- */

class vrtaCompare : public vrtaDevice {
private:

  struct s_persist {
    vrtaUInt Match;
  } m_Persist; /*  State that can be persisted over a reset */

protected:
  vrtaComparable &m_Source;
  vrtaCompare * m_NextCompare;
  vrtaUInt m_Match;
  vrtaUInt m_Vector;

  void Starting(void) {
  };

  void Stopping(void) {
  };
  vrtaErrType OnAction(const vrtaAction *action);
  vrtaErrType AsyncGetState(vrtaEvent *event);

  /*  Persistence */
  vrtaDataLen GetPersistentDataSize(void) {
    return sizeof (m_Persist);
  };

  vrtaByte * GetPersistentData(void) {
    m_Persist.Match = m_Match;
    return (vrtaByte *)&m_Persist;
  };
  void SetPersistentData(vrtaByte *addr, vrtaDataLen len);

public:
  /*  Constructor */
  vrtaCompare(const vrtaTextPtr name, vrtaComparable &source, vrtaUInt match, vrtaUInt vector);

  /*  Set */
  void SetMatch(vrtaUInt val);

  vrtaUInt IncrementMatch(vrtaUInt val) {
    return (m_Match += val);
  };
  void SetVector(vrtaUInt val);
  void NewValue(vrtaUInt val);

  /*  Get */
  vrtaUInt GetMatch(void) {
    return m_Match;
  };

};

/* --------------------------------------------------------------------------- */
/*  [VP_VECU  502]       vrtaSensor */
/* --------------------------------------------------------------------------- */

class vrtaSensor : public vrtaComparable {
private:

  struct s_persist {
    vrtaUInt Value;
    vrtaUInt Max;
  } m_Persist; /*  State that can be persisted over a reset */

protected:
  vrtaUInt m_Max;

  void Starting(void) {
  };

  void Stopping(void) {
  };
  vrtaErrType OnAction(const vrtaAction *action);
  vrtaErrType AsyncGetState(vrtaEvent *event);

  /*  Persistence */
  vrtaDataLen GetPersistentDataSize(void) {
    return sizeof (m_Persist);
  };

  vrtaByte * GetPersistentData(void) {
    m_Persist.Value = Value();
    m_Persist.Max = m_Max;
    return (vrtaByte *)&m_Persist;
  };
  void SetPersistentData(vrtaByte *addr, vrtaDataLen len);

public:
  /*  Constructor */
  vrtaSensor(const vrtaTextPtr name);
  vrtaSensor(const vrtaTextPtr name, const vrtaTextPtr info, const vrtaOptStringlistPtr events, const vrtaOptStringlistPtr actions);

  /*  Direct access */
  vrtaUInt GetMax(void) {
    return m_Max;
  };
  void SetMax(vrtaUInt v);
  void SetVal(vrtaUInt v);
};

/* --------------------------------------------------------------------------- */
/*  [VP_VECU  507]       vrtaSensorToggleSwitch */
/* --------------------------------------------------------------------------- */

class vrtaSensorToggleSwitch : public vrtaSensor {
private:

protected:

public:
  /*  Constructor */
  vrtaSensorToggleSwitch(const vrtaTextPtr name);
};

/* --------------------------------------------------------------------------- */
/*  [VP_VECU  510]       vrtaSensorMultiwaySwitch */
/* --------------------------------------------------------------------------- */

class vrtaSensorMultiwaySwitch : public vrtaSensor {
private:

protected:

public:
  /*  Constructor */
  vrtaSensorMultiwaySwitch(const vrtaTextPtr name, vrtaUInt ways);
};

/* --------------------------------------------------------------------------- */
/*  [VP_VECU  511]       vrtaActuator */
/* --------------------------------------------------------------------------- */

class vrtaActuator : public vrtaComparable {
private:

  struct s_persist {
    vrtaUInt Value;
    vrtaUInt Max;
  } m_Persist; /*  State that can be persisted over a reset */

protected:
  vrtaUInt m_Max;

  void Starting(void) {
  };

  void Stopping(void) {
  };
  vrtaErrType OnAction(const vrtaAction *action);
  vrtaErrType AsyncGetState(vrtaEvent *event);

  /*  Persistence */
  vrtaDataLen GetPersistentDataSize(void) {
    return sizeof (m_Persist);
  };

  vrtaByte * GetPersistentData(void) {
    m_Persist.Value = Value();
    m_Persist.Max = m_Max;
    return (vrtaByte *)&m_Persist;
  };
  void SetPersistentData(vrtaByte *addr, vrtaDataLen len);

public:
  /*  Constructor */
  vrtaActuator(const vrtaTextPtr name);
  vrtaActuator(const vrtaTextPtr name, const vrtaTextPtr info);

  /*  Direct access */
  vrtaUInt GetMax(void) {
    return m_Max;
  };
  void SetMax(vrtaUInt v);
  void SetVal(vrtaUInt v);
};

/* --------------------------------------------------------------------------- */
/*  [VP_VECU  516]       vrtaActuatorLight */
/* --------------------------------------------------------------------------- */

class vrtaActuatorLight : public vrtaActuator {
private:

protected:

public:
  /*  Constructor */
  vrtaActuatorLight(const vrtaTextPtr name);
};

/* --------------------------------------------------------------------------- */
/*  [VP_VECU  517]       vrtaActuatorDimmableLight */
/* --------------------------------------------------------------------------- */

class vrtaActuatorDimmableLight : public vrtaActuator {
private:

protected:

public:
  /*  Constructor */
  vrtaActuatorDimmableLight(const vrtaTextPtr name, vrtaUInt levels);
};

/* --------------------------------------------------------------------------- */
/*  [VP_VECU  518]       vrtaActuatorMultiColorLight */
/* --------------------------------------------------------------------------- */

class vrtaActuatorMultiColorLight : public vrtaActuator {
private:

protected:

public:
  /*  Constructor */
  vrtaActuatorMultiColorLight(const vrtaTextPtr name, vrtaUInt colors);
};

/* --------------------------------------------------------------------------- */
/*  [VP_VECU  519]               vrtaIO */
/* --------------------------------------------------------------------------- */
class vrtaIO : public vrtaDevice {
private:
  vrtaUInt * m_Elements;
  vrtaUInt m_Size;
protected:
  void Starting(void);
  void Stopping(void);
  vrtaErrType OnAction(const vrtaAction *action);
  vrtaErrType AsyncGetState(vrtaEvent *event);

  /*  Persistence */
  vrtaDataLen GetPersistentDataSize(void);
  vrtaByte * GetPersistentData(void);
  void SetPersistentData(vrtaByte *addr, vrtaDataLen len);

public:
  /*  Constructor */
  vrtaIO(const vrtaTextPtr name, vrtaUInt elements);

  void SetValue(vrtaUInt offset, vrtaUInt value);
  void SetValues(vrtaUInt offset, const vrtaUInt *values, vrtaUInt number);

  vrtaUInt GetValue(vrtaUInt offset) const;
  const vrtaUInt *GetValues(void) const;
};

/* --------------------------------------------------------------------------- */
#endif
