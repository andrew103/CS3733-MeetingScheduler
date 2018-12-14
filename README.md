#Team Cursa Scheduler:

##Introduction:
This is our final project for CS3733. It is a meeting scheduler (similiar to when2meet) hosted on AWS. It uses AWS's lambda funciton feature in order to effectively run a serverless website.

##Links:
(These aren't guaranteed to be up after 2018)
###Main view:
https://s3.us-east-2.amazonaws.com/scheduler-storage/html/index.html

###Adminstrator view:
https://s3.us-east-2.amazonaws.com/scheduler-storage/html/sysAdminPage.html

##Functionality:

Here is what the following users can do on our website.

###Organizers:

####Create a schedule.
Organizers can create a schedule through the the index page of our website by clicking on 'create new schedule' and filling out the form. Note: AWS can be slow so it may take a while to create a schedule. Once the schedule is created, the organizer is automatically brought to the organizer view for that schedule, which allows them to edit the schedule.

####View schedule.
Organizers can also view a their schedule by going to the index page and typing in their secret code, which is provided to them when they create the schedule. If they forget it, they can also find it in the url of the schedule when in the organizer view. The organizer can view more weeks by either selecting a date, or clicking on the next/previous week buttons.

####Close and open individal timeslots.
Organizers can toggle the avaliability of timeslots by clicking on the coresponding timeslot and confirming the toggle.

####Close and open all timeslots on a given day.
Organizers can close or open all timeslots in a given day by selecting a day at the bottom of the webpage and pressing the corresponding button.

####Close and open all timeslots at a given time.
Organizers can close or open all timeslots at a given time by selecting a time at the bottom of the webpage and pressing the corresponding button.

####Cancel meeting.
Organizers can cancel a meeting at a specific time by clicking on a booked meeting and confirming the cancelation. 

####Sharing the schedule.
Organizers can share the schedule with participants by sending them the participant code, which is shown in the organizer view.

####Extending the schedule.
Organizers can extend the start and end date of the schedule at the bottom of the webpage. Note: this may take a while if AWS is being slow.

####Deleting a schedule.
Organizers can delete their schedule by clicking on the delete schedule button, and typing "YES" into the confirmation box.

###Participants:

####View schedule.
Participants can view the schedule by going to the index page and submitting the participant code provided by the adminstrator.

####Create a meeting.
Participants can create a meeting by clicking on the corresponding timeslot and typing in their name or email. Once the meeting is created, they are given a meeting code that allows them to cancel the meeting later if need be.

####Cancel a meeting.
Participants can cancel a meeting by clicking on the corresponding meeting and typing in the meeting code that they were provided when they created the meeting.

####Search for open timeslot.
Participants can search for open timeslots by clicking 'Search for Open Timeslots' at the bottom of the page, typing in their name, and submitting their search critera. If they find a timeslot they like they can click 'Schedule' to schedule it.

###Adminstrators:

####Delete old schedules.
Adminstrators can delete schedules that are N days old by accessing the adminstrator view (see link above), selecting the number of days and clicking delete schedules. The schedules that would be deleted are displayed, and the adminstrator is asked to confirm the deletion of the schedules.

####Report Activity.
Adminstrators can see schedules create in the last N hours by accessing the adminstrator view (see link above), selecting the number of hours and clicking retrive schedules.



