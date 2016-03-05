/** \file         main.c
  *
  * \brief        Main loop for RTPC-EVE ( target platform: VRTA-ux/RTPC )
  *
  * [$crn:2007:dox
  * \copyright Copyright 2012 ETAS GmbH
  * $]
  *
  * \note         PLATFORM DEPENDANT [yes/no]: yes
  *
  * \note         TO BE CHANGED BY USER [yes/no]: no
  *
  * $Id: main.c 2379 2012-08-10 12:55:57Z pin9fe $
  */

#include "Std_Types.h"
#include "Main_Cfg.h"
#include "target.h"
#include "Os.h"

#ifdef USES_BSW
#   include "EcuM.h"
#endif

void StartAUTOSAR()
{
#ifdef USES_BSW
    /* Call Autosar EcuM_Init() */
    EcuM_Init();
#else
    StartOS(OSDEFAULTAPPMODE);
#endif
}

OS_MAIN()
{
    char *argv[] = { "VECU" };
    rtos_init(1, argv);

    TargetInit();
    StartAUTOSAR();
}
