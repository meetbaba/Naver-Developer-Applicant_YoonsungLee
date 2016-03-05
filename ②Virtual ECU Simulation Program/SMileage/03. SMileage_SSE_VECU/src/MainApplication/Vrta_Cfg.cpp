/*
 * Vrta_Cfg.cpp
 *
 *  Created on: Feb 26, 2013
 *      Author: duv9yok
 */

#include "vrtaDevice.h"
#include "Vrta_Cfg.h"


static void( *flashhandler)(void) = NULL;
static void( *Porthandler)(void) = NULL;
static void( *Adchandler)(void) = NULL;
static void( *Pwmhandler)(void) = NULL;
static void( *Icuhandler)(void) = NULL;
static void( *Canhandler)(void) = NULL;
static void( *CanTrcvhandler)(void) = NULL;

void vrtaActionHandler(void){
	if(flashhandler){
		(*flashhandler)();
	}
	if(Porthandler){
		(*Porthandler)();
	}
	if(Adchandler){
		(*Adchandler)();
	}
	if(Pwmhandler){
		(*Pwmhandler)();
	}
	if(Canhandler){
		(*Canhandler)();
	}
	if(Icuhandler){
		(*Icuhandler)();
	}
	if(CanTrcvhandler){
		(*CanTrcvhandler)();
	}
}

void EnableVrtaFlash(void(*f)(void)){
	flashhandler = f;
}

void EnableVrtaPort(void(*f)(void)){
	Porthandler = f;
}

void EnableVrtaAdc(void(*f)(void)){
	Adchandler = f;
}

void EnableVrtaPwm(void(*f)(void)){
	Pwmhandler = f;
}

void EnableVrtaIcu(void(*f)(void)){
	Icuhandler = f;
}

void EnableVrtaCan(void(*f)(void)){
	Canhandler = f;
}

void EnableVrtaCanTrcv(void(*f)(void)){
	CanTrcvhandler = f;
}