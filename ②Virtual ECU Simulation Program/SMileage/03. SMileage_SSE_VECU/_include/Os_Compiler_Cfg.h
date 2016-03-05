/** \file         Os_Compiler_Cfg.h
  *
  * \brief        OS related Compiler configuration settings for RTPC-EVE ( target platform: VRTA-ux/RTPC )
  *
  * [$crn:2007:dox
  * \copyright Copyright 2012 ETAS GmbH
  * $]
  *
  * \note         PLATFORM DEPENDANT [yes/no]: yes
  *
  * \note         TO BE CHANGED BY USER [yes/no]: no
  *
  * $Id: Os_Compiler_Cfg.h 2379 2012-08-10 12:55:57Z pin9fe $
  */

#ifndef OS_COMPILER_CFG_H
#define OS_COMPILER_CFG_H
        
/* memclass and ptrclass macros */        
#define OS_CODE               /* not required */ /* Code */
#define OS_CONST              /* not required */ /* Global or static constants. */
#define OS_APPL_DATA          /* not required */ /* References on application data (expected to be in RAM or ROM) passed via API. */
#define OS_APPL_CONST         /* not required */ /* References on application constants (expected to be certainly in ROM, for instance pointer of Init-function) passed via API */
#define OS_APPL_CODE          /* not required */ /* References on application functions. (e.g. call back function pointers) */
#define OS_CALLOUT_CODE       /* not required */ /* References on application functions. (e.g. callout function pointers) */
#define OS_VAR_NOINIT         /* not required */ /* Globals or statics which are never initialized. */
#define OS_VAR_POWER_ON_INIT  /* not required */ /* Globals or statics which are initialized after PO reset. */
#define OS_VAR_FAST           /* not required */ /* Globals or statics which require fast or bitwise access. */
#define OS_VAR                /* not required */ /* Globals or statics which are initialized after every reset. */

#endif /* OS_COMPILER_CFG_H */
