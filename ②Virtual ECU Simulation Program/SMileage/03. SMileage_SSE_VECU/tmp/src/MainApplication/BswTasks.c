/** \file         BSWTasks.h
  *
  * \brief        Default task definitions for RTPC-EVE ( target platform: VRTA-ux/RTPC )
  *
  * [$crn:2007:dox
  * \copyright Copyright 2012 ETAS GmbH
  * $]
  *
  * \note         PLATFORM DEPENDANT [yes/no]: yes
  *
  * \note         TO BE CHANGED BY USER [yes/no]: no
  *
  * $Id: BswTasks.c 2379 2012-08-10 12:55:57Z pin9fe $
  */

#include "Os_VAP.h"
#include "Main_Cfg.h"

#ifdef BSW_WITH_CAN
#   include "Com.h"
#   include "CanIf.h"
#   include "ComM.h"
#endif

#ifdef USES_BSW
#   include "EcuM.h"
#endif

#ifdef BSW_WITH_COMM
#   include "ComM.h"
#endif

#ifdef BSW_WITH_NVM
#  include "NvM.h"
#  include "fee.h"
#  include "MemIf.h"
#endif

#ifdef ENABLE_XCP
#   include "rtpc-xcp-slave.h"
#endif

#ifdef DEFAULT_BSW_TASK
VAP_TASK(DEFAULT_BSW_TASK)
{
    /* Typical BSW module sequence for CAN.
       Modify depending on which modules have been configured.
     */
#ifdef BSW_WITH_CAN
    Can_MainFunction_Read();
#endif

    EcuM_MainFunction();

#ifdef BSW_WITH_CAN
    CanSM_MainFunction();
#endif

#ifdef BSW_WITH_COMM
    ComM_MainFunction_0();
#endif

#ifdef BSW_WITH_CAN
    Com_MainFunctionTx();
    Com_MainFunctionRx();
    Can_MainFunction_Write();
#endif

#ifdef BSW_WITH_NVM
    NvM_MainFunction();
    Fee_MainFunction();
#endif
    TerminateTask();
}
#endif

#ifdef DEFAULT_BSW_INIT
VAP_TASK(DEFAULT_BSW_INIT)
{
    /* Typical BSW module initialization sequence for COM.
       Modify depending on which modules etc. have been configured.
     */
#ifdef BSW_WITH_COMM
    (void)ComM_RequestComMode(VECU_COMMUSER, COMM_FULL_COMMUNICATION);
#endif
    TerminateTask();
}
#endif

#ifdef ENABLE_XCP
VAP_TASK(XCPTask)
{
    /* XCP related calls: */
    Xcp_DoDaqForEvent(0);
    TerminateTask();
}
#endif
