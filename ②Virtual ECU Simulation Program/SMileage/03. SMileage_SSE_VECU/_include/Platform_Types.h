/** \file         Platform_Types.h
  *
  * \brief        Platform type definitions for RTPC-EVE ( target platform: VRTA-ux/RTPC )
  *
  * [$crn:2007:dox
  * \copyright Copyright 2012 ETAS GmbH
  * $]
  *
  * \note         PLATFORM DEPENDANT [yes/no]: yes
  *
  * \note         TO BE CHANGED BY USER [yes/no]: no
  *
  * $Id: Platform_Types.h 2379 2012-08-10 12:55:57Z pin9fe $
  */

#ifndef PLATFORM_TYPES_H 
#define PLATFORM_TYPES_H  
/*
  File version information
*/
#define PLATFORM_VENDOR_ID        (11U)
#define PLATFORM_AR_MAJOR_VERSION (4U)
#define PLATFORM_AR_MINOR_VERSION (0U)
#define PLATFORM_AR_PATCH_VERSION (0U)
#define PLATFORM_SW_MAJOR_VERSION (5U)
#define PLATFORM_SW_MINOR_VERSION (0U)
#define PLATFORM_SW_PATCH_VERSION (92U)

/* 
  CPU register type width
*/
#define CPU_TYPE_8  8                        
#define CPU_TYPE_16 16                        
#define CPU_TYPE_32 32  

/* 
  Bit order definition
*/
#define MSB_FIRST 0                 /* Big endian bit ordering        */
#define LSB_FIRST 1                 /* Little endian bit ordering     */

/* 
  Byte order definition
*/
#define HIGH_BYTE_FIRST 0           /* Big endian byte ordering       */
#define LOW_BYTE_FIRST  1           /* Little endian byte ordering    */

/*
  Platform type and endianess definitions
*/
#define CPU_TYPE CPU_TYPE_32
#define CPU_BIT_ORDER LSB_FIRST
#define CPU_BYTE_ORDER LOW_BYTE_FIRST

/*
  AUTOSAR integer data types
  signed integer types are implemented using 2 complement arithmetic.
*/
//
typedef unsigned char uint8;   /*           0 .. 255             */
typedef unsigned short uint16; /*           0 .. 65535           */
typedef unsigned long uint32;  /*           0 .. 4294967295      */
typedef signed char sint8;     /*        -128 .. +127            */
typedef signed short sint16;   /*      -32768 .. +32767          */
typedef signed long sint32;    /* -2147483648 .. +2147483647     */
typedef float float32;
typedef double float64;

/* chosen for best performance */
/* these must only be used with a local scope inside a module.
   They are not allowed to be used within the API of a module. */
typedef unsigned char uint8_least; /* At least 8 bit */
typedef unsigned short uint16_least; /* At least 16 bit */
typedef unsigned long uint32_least; /* At least 32 bit */
typedef signed char sint8_least; /* At least 7 bit + 1 bit sign */
typedef signed short sint16_least; /* At least 15 bit + 1 bit sign */
typedef signed long sint32_least; /* At least 31 bit + 1 bit sign */

/* Only to be used with TRUE and FALSE */
typedef unsigned char boolean; /* Addressable 8 bits for use with TRUE/FALSE */

/* Only use in conjunction with the boolean type */
#ifndef TRUE
 #define TRUE      1U
#endif

#ifndef FALSE
 #define FALSE     0U
#endif

#endif /* PLATFORM_TYPES_H */
