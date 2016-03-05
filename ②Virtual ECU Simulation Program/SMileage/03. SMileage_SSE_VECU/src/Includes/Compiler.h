/** \file         Compiler.h
  *
  * \brief        System-wide compiler settings for RTPC-EVE ( target platform: VRTA-ux/RTPC )
  *
  * [$crn:2007:dox
  * \copyright Copyright 2012 ETAS GmbH
  * $]
  *
  * \note         PLATFORM DEPENDANT [yes/no]: yes
  *
  * \note         TO BE CHANGED BY USER [yes/no]: no
  *
  * $Id: Compiler.h 2379 2012-08-10 12:55:57Z pin9fe $
  */

#ifndef COMPILER_H
#define COMPILER_H

#define _GCC_C_VRTA_UX_
#define COMPILER_VENDOR_ID (11U)
#define COMPILER_AR_MAJOR_VERSION (4U)
#define COMPILER_AR_MINOR_VERSION (0U)
#define COMPILER_AR_PATCH_VERSION (0U)
#define COMPILER_SW_MAJOR_VERSION (0U)
#define COMPILER_SW_MINOR_VERSION (0U)
#define COMPILER_SW_PATCH_VERSION (0U)

#include <Compiler_Cfg.h>

#define AUTOMATIC  /* Used for local non static variables */
#define TYPEDEF  /* Type definitions where no memory qualifier can be specified. */
#define STATIC static
#define NULL_PTR ((void *)0)
#define INLINE

#define FUNC(rettype, memclass) rettype
/*#define FUNC(rettype, memclass) rettype memclass*/ /* Definition of FUNC for special keywords: memclass has to be considered*/
#define P2VAR(ptrtype, memclass, ptrclass) ptrclass ptrtype * memclass
#define P2CONST(ptrtype, memclass, ptrclass) const ptrclass ptrtype * memclass
#define CONSTP2VAR(ptrtype, memclass, ptrclass) ptrclass ptrtype * const memclass
#define CONSTP2CONST(ptrtype, memclass, ptrclass) const memclass ptrtype * const ptrclass
#define P2FUNC(rettype, ptrclass, fctname) ptrclass rettype (*fctname)
#define CONST(ctype, memclass) memclass const ctype
#define VAR(vtype, memclass) memclass vtype
#define FUNC_P2CONST(ptrtype, memclass, ptrclass) const ptrclass ptrtype * memclass



#endif /* COMPILER_H */
