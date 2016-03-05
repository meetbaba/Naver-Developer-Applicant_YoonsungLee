/*
 * Vrta_Cfg.hpp
 *
 *  Created on: Feb 26, 2013
 *      Author: duv9yok
 */

#ifdef __cplusplus
extern "C"{
void vrtaActionHandler(void);
void EnableVrtaFlash ( void (*f) (void));
void EnableVrtaAdc ( void (*f) (void));
void EnableVrtaPwm ( void (*f) (void));
void EnableVrtaPort ( void (*f) (void));
void EnableVrtaIcu ( void (*f) (void));
void EnableVrtaCan ( void (*f) (void));
void EnableVrtaCanTrcv ( void (*f) (void));
}
#endif
