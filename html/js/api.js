

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
