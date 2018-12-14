

var base_url = "https://dpl5p656f1.execute-api.us-east-2.amazonaws.com/Alpha";

var organizer_url = base_url + "/organizer";

var participant_url = base_url + "/participant";

var organizer_getSchedule = organizer_url + "/getschedule";

var organizer_openCloseTimeSlot = organizer_url + "/openclosetimeslot";

var organizer_createSchedule = organizer_url + "/createschedule";

var participant_scheduleMeeting = participant_url + "/createmeeting";

var participant_cancelMeeting = participant_url + "/cancelmeeting";

var organizer_deleteSchedule = organizer_url + "/deleteschedule";

var organizer_cancelMeeting = organizer_url + "/cancelmeeting";

var organizer_extendStartDate = organizer_url + "/extendstartdate";

var organizer_extendEndDate = organizer_url + "/extendenddate";

var organizer_openByDate = organizer_url + "/openallslotsday";

var organizer_closeByDate = organizer_url + "/closeallslotsday";

var organizer_openByTime = organizer_url + "/openallslotstime";

var organizer_closeByTime = organizer_url + "/closeallslotstime";

var admin_url = base_url + "/admin"

var admin_retrieveOld = admin_url + "/retrieveoldschedules"

var admin_report = admin_url + "/reportactivity"

var admin_deleteOld= admin_url + "/deleteoldschedules"
