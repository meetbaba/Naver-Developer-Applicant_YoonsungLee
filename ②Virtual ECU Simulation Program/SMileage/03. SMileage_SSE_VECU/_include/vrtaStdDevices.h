#ifndef vrtaStdDevicesH
#define vrtaStdDevicesH
/* --------------------------------------------------------------------------- */
/*  Fixed devices */
/* --------------------------------------------------------------------------- */
#define DM_DEVICE_ID  (0)               /*  [VP_ECUIF 1016] */
#define ICU_DEVICE_ID (1)               /*  [VP_ECUIF 1017] [VP_VECU 312] */
#define AM_DEVICE_ID  (2)               /*  [VP_ECUIF 1018] */

/* --------------------------------------------------------------------------- */
/*  IDs of the actions that the Device Manager supports. */
/* --------------------------------------------------------------------------- */
#define DM_ACTION_ID_EventRegister      (1)
#define DM_ACTION_ID_HookEvents         (2)
#define DM_ACTION_ID_ListAll            (3)
#define DM_ACTION_ID_GetDeviceActions   (4)
#define DM_ACTION_ID_GetDeviceEvents    (5)
#define DM_ACTION_ID_GetDeviceInfo      (6)

/* --------------------------------------------------------------------------- */
/*  IDs of the events that the Device Manager supports. */
/* --------------------------------------------------------------------------- */
#define DM_EVENT_ID_DeviceList          (1)
#define DM_EVENT_ID_DeviceActions       (2)
#define DM_EVENT_ID_DeviceEvents        (3)
#define DM_EVENT_ID_DeviceInfo          (4)

/* --------------------------------------------------------------------------- */
/*  IDs of the actions that the ICU supports. */
/* --------------------------------------------------------------------------- */

#define ICU_ACTION_ID_Raise             (1)     /*  [VP_VECU 314] [VP_ECUIF 1074] */
#define ICU_ACTION_ID_Clear             (2)     /*  [VP_VECU 315] [VP_ECUIF 1076] */
#define ICU_ACTION_ID_Mask              (3)     /*  [VP_VECU 316] [VP_ECUIF 1078] */
#define ICU_ACTION_ID_Unmask            (4)     /*  [VP_VECU 317] [VP_ECUIF 1080] */
#define ICU_ACTION_ID_GetPending        (5)     /*  [VP_VECU 318] [VP_ECUIF 1082] */
#define ICU_ACTION_ID_GetIPL            (6)     /*  [VP_VECU 319] [VP_ECUIF 1083] */
#define ICU_ACTION_ID_SetIPL            (7)     /*  [VP_VECU 320] */

/* --------------------------------------------------------------------------- */
/*  IDs of the events that the ICU supports. */
/* --------------------------------------------------------------------------- */

#define ICU_EVENT_ID_Pending            (1)     /*  [VP_VECU 321] [VP_ECUIF 1084] */
#define ICU_EVENT_ID_Start              (2)     /*  [VP_VECU 322] [VP_ECUIF 1086] */
#define ICU_EVENT_ID_Stop               (3)     /*  [VP_VECU 323] [VP_ECUIF 1088] */
#define ICU_EVENT_ID_IPL                (4)     /*  [VP_VECU 324] [VP_ECUIF 1090] */
#define ICU_EVENT_ID_MASKS              (5)     /*  [VP_VECU 792] [VP_ECUIF 1121] */

/* --------------------------------------------------------------------------- */
/*  IDs of the actions that the Application Manager supports. */
/* --------------------------------------------------------------------------- */
#define AM_ACTION_ID_Start              (1)
#define AM_ACTION_ID_Terminate          (2)
#define AM_ACTION_ID_Pause              (3)
#define AM_ACTION_ID_Restart            (4)
#define AM_ACTION_ID_Reset              (5)
#define AM_ACTION_ID_GetInfo            (6)
#define AM_ACTION_ID_TestOption         (7)
#define AM_ACTION_ID_ReadOption         (8)
#define AM_ACTION_ID_ReadParam          (9)
#define AM_ACTION_ID_Fair               (10)

/* --------------------------------------------------------------------------- */
/*  IDs of the events that the Application Manager supports. */
/* --------------------------------------------------------------------------- */
#define AM_EVENT_ID_Started             (1)
#define AM_EVENT_ID_Paused              (2)
#define AM_EVENT_ID_Restarted           (3)
#define AM_EVENT_ID_Reset               (4)
#define AM_EVENT_ID_Terminated          (5)
#define AM_EVENT_ID_Info                (6)
#define AM_EVENT_ID_Option              (7)
#define AM_EVENT_ID_OptionText          (8)
#define AM_EVENT_ID_ParamText           (9)
#define AM_EVENT_ID_State               (10)
#define AM_EVENT_ID_CoreCount           (11)
#define AM_EVENT_ID_Fair                (12)

#endif /* vrtaStdDevicesH */
