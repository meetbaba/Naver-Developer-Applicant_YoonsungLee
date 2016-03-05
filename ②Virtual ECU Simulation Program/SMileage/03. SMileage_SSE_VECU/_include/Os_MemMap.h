/* 
 * This is Os_MemMap.h, auto-generated for:
 *   Project: os_XCP
 *   Target:  VRTA-ux
 *   Variant: RTPC
 *   It should be either copied into MemMap.h by the integrator OR #included into it.
 */
/* Generator info: generate_always */
/* This file defines the OS-specific MemMap.h [$UKS 652] [$UKS 653] [$UKS 655] [$UKS 656] [$UKS 657] */
#if defined (START_WITH_IF)
/* OS Uninitialized RAM: trace data */
#elif defined (OS_START_SEC_VAR_TRACE)
  #undef      OS_START_SEC_VAR_TRACE
  #define DEFAULT_START_SEC_VAR_NOINIT_UNSPECIFIED
#elif defined (OS_STOP_SEC_VAR_TRACE)
  #undef      OS_STOP_SEC_VAR_TRACE
  #define DEFAULT_STOP_SEC_VAR_NOINIT_UNSPECIFIED

/* OS Uninitialized RAM: boolean */
#elif defined (OS_START_SEC_VAR_NOINIT_BOOLEAN)
  #undef      OS_START_SEC_VAR_NOINIT_BOOLEAN
  #define DEFAULT_START_SEC_VAR_NOINIT_BOOLEAN
#elif defined (OS_STOP_SEC_VAR_NOINIT_BOOLEAN)
  #undef      OS_STOP_SEC_VAR_NOINIT_BOOLEAN
  #define DEFAULT_STOP_SEC_VAR_NOINIT_BOOLEAN

/* OS Uninitialized RAM: 8 bit values */
#elif defined (OS_START_SEC_VAR_NOINIT_8BIT)
  #undef      OS_START_SEC_VAR_NOINIT_8BIT
  #define DEFAULT_START_SEC_VAR_NOINIT_8BIT
#elif defined (OS_STOP_SEC_VAR_NOINIT_8BIT)
  #undef      OS_STOP_SEC_VAR_NOINIT_8BIT
  #define DEFAULT_STOP_SEC_VAR_NOINIT_8BIT

/* OS Uninitialized RAM: 16 bit values */
#elif defined (OS_START_SEC_VAR_NOINIT_16BIT)
  #undef      OS_START_SEC_VAR_NOINIT_16BIT
  #define DEFAULT_START_SEC_VAR_NOINIT_16BIT
#elif defined (OS_STOP_SEC_VAR_NOINIT_16BIT)
  #undef      OS_STOP_SEC_VAR_NOINIT_16BIT
  #define DEFAULT_STOP_SEC_VAR_NOINIT_16BIT

/* OS Uninitialized RAM: 32 bit values */
#elif defined (OS_START_SEC_VAR_NOINIT_32BIT)
  #undef      OS_START_SEC_VAR_NOINIT_32BIT
  #define DEFAULT_START_SEC_VAR_NOINIT_32BIT
#elif defined (OS_STOP_SEC_VAR_NOINIT_32BIT)
  #undef      OS_STOP_SEC_VAR_NOINIT_32BIT
  #define DEFAULT_STOP_SEC_VAR_NOINIT_32BIT

/* OS Uninitialized RAM: unspecified size */
#elif defined (OS_START_SEC_VAR_NOINIT_UNSPECIFIED)
  #undef      OS_START_SEC_VAR_NOINIT_UNSPECIFIED
  #define DEFAULT_START_SEC_VAR_NOINIT_UNSPECIFIED
#elif defined (OS_STOP_SEC_VAR_NOINIT_UNSPECIFIED)
  #undef      OS_STOP_SEC_VAR_NOINIT_UNSPECIFIED
  #define DEFAULT_STOP_SEC_VAR_NOINIT_UNSPECIFIED

/* OS Power-on initialized RAM: boolean */
#elif defined (OS_START_SEC_VAR_POWER_ON_INIT_BOOLEAN)
  #undef      OS_START_SEC_VAR_POWER_ON_INIT_BOOLEAN
  #define DEFAULT_START_SEC_VAR_POWER_ON_INIT_BOOLEAN
#elif defined (OS_STOP_SEC_VAR_POWER_ON_INIT_BOOLEAN)
  #undef      OS_STOP_SEC_VAR_POWER_ON_INIT_BOOLEAN
  #define DEFAULT_STOP_SEC_VAR_POWER_ON_INIT_BOOLEAN

/* OS Power-on initialized RAM: 8 bit values */
#elif defined (OS_START_SEC_VAR_POWER_ON_INIT_8BIT)
  #undef      OS_START_SEC_VAR_POWER_ON_INIT_8BIT
  #define DEFAULT_START_SEC_VAR_POWER_ON_INIT_8BIT
#elif defined (OS_STOP_SEC_VAR_POWER_ON_INIT_8BIT)
  #undef      OS_STOP_SEC_VAR_POWER_ON_INIT_8BIT
  #define DEFAULT_STOP_SEC_VAR_POWER_ON_INIT_8BIT

/* OS Power-on initialized RAM: 16 bit values */
#elif defined (OS_START_SEC_VAR_POWER_ON_INIT_16BIT)
  #undef      OS_START_SEC_VAR_POWER_ON_INIT_16BIT
  #define DEFAULT_START_SEC_VAR_POWER_ON_INIT_16BIT
#elif defined (OS_STOP_SEC_VAR_POWER_ON_INIT_16BIT)
  #undef      OS_STOP_SEC_VAR_POWER_ON_INIT_16BIT
  #define DEFAULT_STOP_SEC_VAR_POWER_ON_INIT_16BIT

/* OS Power-on initialized RAM: 32 bit values */
#elif defined (OS_START_SEC_VAR_POWER_ON_INIT_32BIT)
  #undef      OS_START_SEC_VAR_POWER_ON_INIT_32BIT
  #define DEFAULT_START_SEC_VAR_POWER_ON_INIT_32BIT
#elif defined (OS_STOP_SEC_VAR_POWER_ON_INIT_32BIT)
  #undef      OS_STOP_SEC_VAR_POWER_ON_INIT_32BIT
  #define DEFAULT_STOP_SEC_VAR_POWER_ON_INIT_32BIT

/* OS Power-on initialized RAM: unspecified size */
#elif defined (OS_START_SEC_VAR_POWER_ON_INIT_UNSPECIFIED)
  #undef      OS_START_SEC_VAR_POWER_ON_INIT_UNSPECIFIED
  #define DEFAULT_START_SEC_VAR_POWER_ON_INIT_UNSPECIFIED
#elif defined (OS_STOP_SEC_VAR_POWER_ON_INIT_UNSPECIFIED)
  #undef      OS_STOP_SEC_VAR_POWER_ON_INIT_UNSPECIFIED
  #define DEFAULT_STOP_SEC_VAR_POWER_ON_INIT_UNSPECIFIED

/* OS Initialized RAM: boolean */
#elif defined (OS_START_SEC_VAR_BOOLEAN)
  #undef      OS_START_SEC_VAR_BOOLEAN
  #define DEFAULT_START_SEC_VAR_BOOLEAN
#elif defined (OS_STOP_SEC_VAR_BOOLEAN)
  #undef      OS_STOP_SEC_VAR_BOOLEAN
  #define DEFAULT_STOP_SEC_VAR_BOOLEAN

/* OS Initialized RAM: 8 bit values */
#elif defined (OS_START_SEC_VAR_8BIT)
  #undef      OS_START_SEC_VAR_8BIT
  #define DEFAULT_START_SEC_VAR_8BIT
#elif defined (OS_STOP_SEC_VAR_8BIT)
  #undef      OS_STOP_SEC_VAR_8BIT
  #define DEFAULT_STOP_SEC_VAR_8BIT

/* OS Initialized RAM: 16 bit values */
#elif defined (OS_START_SEC_VAR_16BIT)
  #undef      OS_START_SEC_VAR_16BIT
  #define DEFAULT_START_SEC_VAR_16BIT
#elif defined (OS_STOP_SEC_VAR_16BIT)
  #undef      OS_STOP_SEC_VAR_16BIT
  #define DEFAULT_STOP_SEC_VAR_16BIT

/* OS Initialized RAM: 32 bit values */
#elif defined (OS_START_SEC_VAR_32BIT)
  #undef      OS_START_SEC_VAR_32BIT
  #define DEFAULT_START_SEC_VAR_32BIT
#elif defined (OS_STOP_SEC_VAR_32BIT)
  #undef      OS_STOP_SEC_VAR_32BIT
  #define DEFAULT_STOP_SEC_VAR_32BIT

/* OS Initialized RAM: unspecified size */
#elif defined (OS_START_SEC_VAR_UNSPECIFIED)
  #undef      OS_START_SEC_VAR_UNSPECIFIED
  #define DEFAULT_START_SEC_VAR_UNSPECIFIED
#elif defined (OS_STOP_SEC_VAR_UNSPECIFIED)
  #undef      OS_STOP_SEC_VAR_UNSPECIFIED
  #define DEFAULT_STOP_SEC_VAR_UNSPECIFIED

/* OS Initialized fast RAM: boolean */
#elif defined (OS_START_SEC_VAR_FAST_BOOLEAN)
  #undef      OS_START_SEC_VAR_FAST_BOOLEAN
  #define DEFAULT_START_SEC_VAR_FAST_BOOLEAN
#elif defined (OS_STOP_SEC_VAR_FAST_BOOLEAN)
  #undef      OS_STOP_SEC_VAR_FAST_BOOLEAN
  #define DEFAULT_STOP_SEC_VAR_FAST_BOOLEAN

/* OS Initialized fast RAM: 8 bit values */
#elif defined (OS_START_SEC_VAR_FAST_8BIT)
  #undef      OS_START_SEC_VAR_FAST_8BIT
  #define DEFAULT_START_SEC_VAR_FAST_8BIT
#elif defined (OS_STOP_SEC_VAR_FAST_8BIT)
  #undef      OS_STOP_SEC_VAR_FAST_8BIT
  #define DEFAULT_STOP_SEC_VAR_FAST_8BIT

/* OS Initialized fast RAM: 16 bit values */
#elif defined (OS_START_SEC_VAR_FAST_16BIT)
  #undef      OS_START_SEC_VAR_FAST_16BIT
  #define DEFAULT_START_SEC_VAR_FAST_16BIT
#elif defined (OS_STOP_SEC_VAR_FAST_16BIT)
  #undef      OS_STOP_SEC_VAR_FAST_16BIT
  #define DEFAULT_STOP_SEC_VAR_FAST_16BIT

/* OS Initialized fast RAM: 32 bit values */
#elif defined (OS_START_SEC_VAR_FAST_32BIT)
  #undef      OS_START_SEC_VAR_FAST_32BIT
  #define DEFAULT_START_SEC_VAR_FAST_32BIT
#elif defined (OS_STOP_SEC_VAR_FAST_32BIT)
  #undef      OS_STOP_SEC_VAR_FAST_32BIT
  #define DEFAULT_STOP_SEC_VAR_FAST_32BIT

/* OS Initialized fast RAM: unspecified size */
#elif defined (OS_START_SEC_VAR_FAST_UNSPECIFIED)
  #undef      OS_START_SEC_VAR_FAST_UNSPECIFIED
  #define DEFAULT_START_SEC_VAR_FAST_UNSPECIFIED
#elif defined (OS_STOP_SEC_VAR_FAST_UNSPECIFIED)
  #undef      OS_STOP_SEC_VAR_FAST_UNSPECIFIED
  #define DEFAULT_STOP_SEC_VAR_FAST_UNSPECIFIED

/* OS Const (ROM): boolean */
#elif defined (OS_START_SEC_CONST_BOOLEAN)
  #undef      OS_START_SEC_CONST_BOOLEAN
  #define DEFAULT_START_SEC_CONST_BOOLEAN
#elif defined (OS_STOP_SEC_CONST_BOOLEAN)
  #undef      OS_STOP_SEC_CONST_BOOLEAN
  #define DEFAULT_STOP_SEC_CONST_BOOLEAN

/* OS Const (ROM): 8 bit values */
#elif defined (OS_START_SEC_CONST_8BIT)
  #undef      OS_START_SEC_CONST_8BIT
  #define DEFAULT_START_SEC_CONST_8BIT
#elif defined (OS_STOP_SEC_CONST_8BIT)
  #undef      OS_STOP_SEC_CONST_8BIT
  #define DEFAULT_STOP_SEC_CONST_8BIT

/* OS Const (ROM): 16 bit values */
#elif defined (OS_START_SEC_CONST_16BIT)
  #undef      OS_START_SEC_CONST_16BIT
  #define DEFAULT_START_SEC_CONST_16BIT
#elif defined (OS_STOP_SEC_CONST_16BIT)
  #undef      OS_STOP_SEC_CONST_16BIT
  #define DEFAULT_STOP_SEC_CONST_16BIT

/* OS Const (ROM): 32 bit values */
#elif defined (OS_START_SEC_CONST_32BIT)
  #undef      OS_START_SEC_CONST_32BIT
  #define DEFAULT_START_SEC_CONST_32BIT
#elif defined (OS_STOP_SEC_CONST_32BIT)
  #undef      OS_STOP_SEC_CONST_32BIT
  #define DEFAULT_STOP_SEC_CONST_32BIT

/* OS Const (ROM): unspecified size */
#elif defined (OS_START_SEC_CONST_UNSPECIFIED)
  #undef      OS_START_SEC_CONST_UNSPECIFIED
  #define DEFAULT_START_SEC_CONST_UNSPECIFIED
#elif defined (OS_STOP_SEC_CONST_UNSPECIFIED)
  #undef      OS_STOP_SEC_CONST_UNSPECIFIED
  #define DEFAULT_STOP_SEC_CONST_UNSPECIFIED

/* OS Code */
#elif defined (OS_START_SEC_CODE)
  #undef      OS_START_SEC_CODE
  #define DEFAULT_START_SEC_CODE
#elif defined (OS_STOP_SEC_CODE)
  #undef      OS_STOP_SEC_CODE
  #define DEFAULT_STOP_SEC_CODE

/* OS Fast Code */
#elif defined (OS_START_SEC_CODE_FAST)
  #undef      OS_START_SEC_CODE_FAST
  #define DEFAULT_START_SEC_CODE_FAST
#elif defined (OS_STOP_SEC_CODE_FAST)
  #undef      OS_STOP_SEC_CODE_FAST
  #define DEFAULT_STOP_SEC_CODE_FAST

/* OS Slow Code */
#elif defined (OS_START_SEC_CODE_SLOW)
  #undef      OS_START_SEC_CODE_SLOW
  #define DEFAULT_START_SEC_CODE_SLOW
#elif defined (OS_STOP_SEC_CODE_SLOW)
  #undef      OS_STOP_SEC_CODE_SLOW
  #define DEFAULT_STOP_SEC_CODE_SLOW

/* OS Library Code */
#elif defined (OS_START_SEC_CODE_LIB)
  #undef      OS_START_SEC_CODE_LIB
  #define DEFAULT_START_SEC_CODE_LIB
#elif defined (OS_STOP_SEC_CODE_LIB)
  #undef      OS_STOP_SEC_CODE_LIB
  #define DEFAULT_STOP_SEC_CODE_LIB

/* OS Interrupt vectors */
#elif defined (OS_START_SEC_VECTORS)
  #undef      OS_START_SEC_VECTORS
  #define DEFAULT_START_SEC_VECTORS
#elif defined (OS_STOP_SEC_VECTORS)
  #undef      OS_STOP_SEC_VECTORS
  #define DEFAULT_STOP_SEC_VECTORS

/* Application code. Tasks, ISRs and Trusted Functions. */
#elif defined (OS_START_SEC_APPL_CODE)
  #undef      OS_START_SEC_APPL_CODE
  #define DEFAULT_START_SEC_APPL_CODE
#elif defined (OS_STOP_SEC_APPL_CODE)
  #undef      OS_STOP_SEC_APPL_CODE
  #define DEFAULT_STOP_SEC_APPL_CODE

/* Application code. Callbacks/callouts from the OS. */
#elif defined (OS_START_SEC_CALLOUT_CODE)
  #undef      OS_START_SEC_CALLOUT_CODE
  #define DEFAULT_START_SEC_CALLOUT_CODE
#elif defined (OS_STOP_SEC_CALLOUT_CODE)
  #undef      OS_STOP_SEC_CALLOUT_CODE
  #define DEFAULT_STOP_SEC_CALLOUT_CODE

#endif
