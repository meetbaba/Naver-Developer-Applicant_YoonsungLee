/** \file         Std_Types.h
  *
  * \brief        Standard type definitions for RTPC-EVE ( target platform: VRTA-ux/RTPC )
  *
  * [$crn:2007:dox
  * \copyright Copyright 2012 ETAS GmbH
  * $]
  *
  * \note         PLATFORM DEPENDANT [yes/no]: yes
  *
  * \note         TO BE CHANGED BY USER [yes/no]: no
  *
  * $Id: Std_Types.h 2379 2012-08-10 12:55:57Z pin9fe $
  */
/*
 * This is .h for target platform RTPC-EVE
 */


#ifndef STD_TYPES_H
#define STD_TYPES_H

/*******************************************************************************
**                      Include Section                                       **
*******************************************************************************/
#include <Platform_Types.h>             /* platform specific type definitions */
#include <Compiler.h>                   /* mapping compiler specific keywords */

/* File version information */
#define STD_MAJOR_VERSION  (4U)
#define STD_MINOR_VERSION  (0U)
#define STD_PATCH_VERSION  (0U)
#define STD_TYPES_AR_RELEASE_MAJOR_VERSION 4u
#define STD_TYPES_AR_RELEASE_MINOR_VERSION 0

/*******************************************************************************
**                      Global Data Types                                     **
*******************************************************************************/
typedef uint8 Std_ReturnType;

typedef struct {
   uint16 vendorID;
   uint16 moduleID;
   uint8  sw_major_version;
   uint8  sw_minor_version;
   uint8  sw_patch_version;
} Std_VersionInfoType; /* [STD015] */

#ifndef STATUSTYPEDEFINED
  #define STATUSTYPEDEFINED
  #define E_OK 0x00U
  typedef unsigned char StatusType; /* OSEK compliance */
#endif
#define E_NOT_OK 0x01U

#define STD_HIGH    0x01  /* Physical state 5V or 3.3V */
#define STD_LOW     0x00  /* Physical state 0V */

#define STD_ACTIVE  0x01  /* Logical state active */
#define STD_IDLE    0x00  /* Logical state idle */

#define STD_ON      0x01
#define STD_OFF     0x00

#endif /* STD_TYPES_H */
