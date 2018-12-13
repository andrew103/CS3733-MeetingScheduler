var originalScheduleMeetingHTML = `
<div class="modal-dialog">
  <div class="modal-content">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">&times;</button>
      <h4 class="modal-title">Schedule meeting on an open timeslot</h4>
    </div>
    <div class="modal-body">
      <p id="scheduleMeetingText"></p>
      <form class="form-horizontal">
        <div class="form-group">
          <label class="control-label col-sm-2" for="name">Name: </label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="participantName" placeholder="YOUR NAME"></input>
          </div>
        </div>
      </form>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-success pull-left" id="scheduleMeetingButton" onclick="scheduleMeeting()">Schedule Meeting</button>
      <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    </div>
  </div>
</div>
`
var afterScheduleMeetingHTML = `
<div class="modal-dialog">
  <div class="modal-content">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">&times;</button>
      <h4 class="modal-title">Schedule meeting on an open timeslot</h4>
    </div>
    <div class="modal-body">
      <p id="scheduleMeetingCode"></p>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-default" data-dismiss="modal" id="closePopupButton" onclick="">Close</button>
    </div>
  </div>
</div>
`

isOrganizer = false;

function processCell(cellText, cellIndex, rowIndex){//organizer version
  document.getElementById("scheduleMeeting").innerHTML = originalScheduleMeetingHTML;
  switch(cellText){
    case "Open":
      stringDisp = "Time slot open on "+showDayTime(cellIndex, rowIndex);
      document.getElementById("scheduleMeetingText").innerHTML=stringDisp;
      document.getElementById("scheduleMeetingButton").onclick = function() {
        var shareCode = urlParams["shareCode"];
        var partInfo = document.getElementById("participantName").value;
        var date = getDate(cellIndex);
        var time;

        days = schedule["days"];
        for (i = 0; i < days.length; i++){
            if (days[i]["dateStr"] == date){
                time = days[i]["timeSlots"][rowIndex]["startTime"];
                break;
            }
        }
        console.log("successfully set all params");

        scheduleMeeting(shareCode, partInfo, time, date);
      };
      $("#scheduleMeeting").modal();
      break;
    case "Closed":
      stringDisp = "Time slot closed on "+showDayTime(cellIndex, rowIndex);
      alert(stringDisp);
      break;
    case "Not on schedule":
      break;
    default: //there is a meeting scheduled but not visiable to participant
      stringDisp = "Time slot has a scheduled meeting on "+showDayTime(cellIndex, rowIndex)+".";
      document.getElementById("cancelMeetingText").innerHTML=stringDisp;
      document.getElementById("cancelMeetingButton").onclick = function() {
        var shareCode = urlParams["shareCode"];
        var meetingCode = document.getElementById("meetingCode").value;
        console.log("successfully set all params");

        cancelMeeting(shareCode, meetingCode);
      };
      $("#cancelMeeting").modal();
  }
}

function populateTS(selector) {
    console.log(schedule);
    var select = $(selector);
    var time;

    timeslots = schedule["days"][0]["timeSlots"];

    for (var i = 0; i < timeslots.length; i ++) {
        time = timeslots[i]["startTime"];
        //add the value to dropdownlist
        select.append($('<option></option>').attr('value', time).text(time));
    }
}

function searchOpenTS(searchReturn){
  prevSearch = document.getElementById("searchReturn").getElementsByTagName("h5");
  btn = document.getElementById("searchReturn").getElementsByTagName("button")
  br = document.getElementById("searchReturn").getElementsByTagName("br")
  while (prevSearch.length){
    document.getElementById("searchReturn").removeChild(prevSearch[0]);
    document.getElementById("searchReturn").removeChild(btn[0]);
    document.getElementById("searchReturn").removeChild(br[0]);
  }

  var current=[];
  var deepCopy  = jQuery.extend(true,{},schedule)
  current = current.concat(deepCopy["days"]); //keeps track of current list of time slots after filter

  dayOfWeek = document.getElementById("dayOfWeek").value;
  if (dayOfWeek != null && dayOfWeek != ""){
    //if dayOfWeek input is not empty, do first search
    index = 0; //index tracks "current" list, which shortens when day gets removed
    for(i=0;i<schedule["days"].length;i++){//i tracks a static list "days"
      if (i%5 != dayOfWeek){//assume first day starts monday,
        current.splice(index,1); //remove unmatched TS from current list
      }
      else index ++;
    }
  }
  year = document.getElementById("year").value;
  if (year != null && year != ""){
    //if year input is not empty, do second search
    for(i=0;i<current.length;i++){
      if (current[i]["dateStr"].substring(0,4) != year){
        current.splice(i,1); //remove unmatched TS from current list
        i --; //list shortens by 1
      }
    }
  }
  month = document.getElementById("month").value;
  if (month != null && month != ""){
    //if month input is not empty, do third search
    for(i=0;i<current.length;i++){
      if (current[i]["dateStr"].substring(5,7) != month){
        current.splice(i,1); //remove unmatched TS from current list
        i --; //list shortens by 1
      }
    }
  }
  dayOfMonth = document.getElementById("dayOfMonth").value;
  if (dayOfMonth != null && dayOfMonth != ""){
    //if dayOfMonth input is not empty, do forth search
    for(i=0;i<current.length;i++){
      if (current[i]["dateStr"].substring(8) != dayOfMonth){
        current.splice(i,1); //remove unmatched TS from current list
        i --; //list shortens by 1
      }
    }
  }
  timeSlot = document.getElementById("timeSlot").value;
  if (timeSlot != null && timeSlot != ""){
    //if timeSlot input is not empty, do last search
    for(i=0;i<current.length;i++){
      for(j=0;j<current[i]["timeSlots"].length;j++){
        if (current[i]["timeSlots"][j]["startTime"] != timeSlot || current[i]["timeSlots"][j]["isClosed"] == true){
          current[i]["timeSlots"].splice(j,1); //remove unmatched TS from current list
          j --; //list shortens by 1
        }
      }
      if (current[i]["timeSlots"].length ==0){
        current.splice(i,1); //if all timeslots in a day are removed, delete day
        i --;
      }
    }
  }
  console.log(current);

  var sr = $(searchReturn);
  var txt;
  for(i=0;i<current.length;i++){
    for(j=0;j<current[i]["timeSlots"].length;j++){
      txt = (current[i]["dateStr"]+" at "+current[i]["timeSlots"][j]["startTime"]);
      sr.append($('<h5></h5>').text(txt));
      sr.append($('<input></input>')
                .attr('type',"button")
                .attr('value', txt)
                .attr('onclick', "scheduleOpenTS(this)")
                .attr('name',"schedule"));
      sr.append($('<br>'));
    }
  }
  if (current.length == 0){
    alert("no time slot matches search criteria");
  }
  else {
    $("#returnTimeSlot").modal();
  }
}

function scheduleOpenTS(btn){ //different from schedule meeting
  console.log("input txt:"+btn.innerHTML);
  text = btn.innerHTML;
  var shareCode = urlParams["shareCode"];
  var partInfo = document.getElementById("searchOpenTSName").value;
  var date = text.substring(0,10);
  console.log("date: "+date);
  var time = text.substring(14,4);
  console.log("time"+time);

  scheduleMeeting(shareCode, partInfo, time, date);
}

function scheduleMeeting(scheduleCode, participantInfo, time, day){
  var postReq = {};
  var meetingCode;
  postReq["scheduleCode"] = scheduleCode;
  postReq["participantInfo"] = participantInfo;
  postReq["time"] = time;
  postReq["day"] = day;

  var xhr = new XMLHttpRequest();
  xhr.open("POST",participant_scheduleMeeting,true);

  xhr.send(JSON.stringify(postReq));

  xhr.onloadend=function() {
      //console.log(xhr);
      var found;
      console.log(xhr.request);
      if (xhr.readyState == XMLHttpRequest.DONE) {
          console.log ("XHR:" + xhr.responseText);
          ret = JSON.parse(xhr.responseText)
          console.log(ret)
          if(ret["httpCode"] == 200){
              meetingCode = ret["meetingCode"];
              console.log(meetingCode);
              found = true;

              document.getElementById("scheduleMeeting").innerHTML = afterScheduleMeetingHTML;
              document.getElementById("scheduleMeetingCode").innerHTML = "Your meeting code is: " + meetingCode + ". Please save this to access your meeting.";
              document.getElementById("closePopupButton").onclick = function () {
                  loadSchedule(false);
              };
          }
          else {
              console.log("could not retrieve schedule, got status" + status)
              found = false;
          }
      } else {
          console.log("Could not get req")
          found = false;
      }
  }

  // alert("add schedule meeting functionality");
}

function cancelMeeting(scheduleCode, meetingCode){
    var postReq = {};
    postReq["scheduleCode"] = scheduleCode;
    postReq["meetingCode"] = meetingCode;

    var xhr = new XMLHttpRequest();
    xhr.open("POST",participant_cancelMeeting,true);

    xhr.send(JSON.stringify(postReq));
    console.log(participant_cancelMeeting);

    xhr.onloadend=function() {
        //console.log(xhr);
        var found;
        console.log(xhr.request);
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr.responseText);
            ret = JSON.parse(xhr.responseText)
            console.log(ret)
            if(ret["httpCode"] == 200){
                found = true;
                loadSchedule(false);
            }
            else {
                found = false;
            }
        } else {
            console.log("Could not get req")
            found = false;
        }
    }
    // alert("add cancel meeting functionality");
}
