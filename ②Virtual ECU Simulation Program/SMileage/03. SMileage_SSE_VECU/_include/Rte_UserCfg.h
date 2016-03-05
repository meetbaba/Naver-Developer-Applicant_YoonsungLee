/** \file         Rte_UserCfg.h
  *
  * \brief        RTE user configuration header file for RTPC-EVE ( target platform: VRTA-ux/RTPC )
  *
  * [$crn:2007:dox
  * \copyright Copyright 2012 ETAS GmbH
  * $]
  *
  * \note         PLATFORM DEPENDANT [yes/no]: yes
  *
  * \note         TO BE CHANGED BY USER [yes/no]: yes
  *
  * $Id: Rte_UserCfg.h 2379 2012-08-10 12:55:57Z pin9fe $
  */

#ifndef RTE_USERCFG_H
#define RTE_USERCFG_H

/* define RTE types for the software components */
// #define Swc_Receiver_CODE
// #define Swc_Sender_CODE

/* general defines*/
#define RTE_LIBC_MEMCPY

#define RTE_CODE
#define RTE_DATA
#define RTE_CONST

#define RTE_APPL_CODE
#define RTE_APPL_DATA
#define RTE_APPL_CONST

#define RTE_OS_CODE
#define RTE_OS_CDATA

#define RTE_CODE
#define RTE_LIBCODE

#endif /* RTE_USERCFG_H */
