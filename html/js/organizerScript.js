isOrganizer = true;

function loadSelect(){
  select = $(document.getElementById("closeByTime"));
  timeslots = schedule["days"][0]["timeSlots"];
  for (i=0;i<timeslots.length;i++){
    time = timeslots[i]["startTime"]
    select.append($('<option></option>').text(time).attr('value',time));
  }
}

function processCell(cellText, cellIndex, rowIndex){//organizer version
    switch(cellText){
        case "Open":
            stringDisp = "Time slot open on "+showDayTime(cellIndex, rowIndex);
            document.getElementById("closeSlotText").innerHTML=stringDisp;
            $("#closeSlot").modal();
            break;
        case "Closed":
            stringDisp = "Time slot closed on "+showDayTime(cellIndex, rowIndex);
            document.getElementById("openSlotText").innerHTML=stringDisp;
            $("#openSlot").modal();
            break;
        case "Not on schedule":
            break;
        default: //there is a meeting scheduled
            stringDisp = "Meeting scheduled on "+showDayTime(cellIndex, rowIndex)+". "+cellText;
            document.getElementById("cancelMeetingText").innerHTML=stringDisp;
            $("#cancelMeeting").modal();
    }
}

function deleteSchedule(){
    var msg = prompt("Confirm deleting the entire schedule by typing 'YES'", "Are you sure about that?");
    var txt;
    if (msg == "YES" || msg == "yes" || msg == "Yes") {
        var postReq = {}
        postReq["scheduleCode"] = schedule["shareCode"];
        postReq["secretCode"] = urlParams["secretCode"];
        console.log("JS of req:" + JSON.stringify(postReq))
        var xhr = new XMLHttpRequest();
        xhr.open("POST",organizer_deleteSchedule,true);
        console.log("PostReq:" + JSON.stringify(postReq));
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
                    alert("The entire schedule is cancled");
                    window.location.href = indexWebsite;
                }
                else {
                    console.log("could not retrieve schedule, got stats" + ret["httpCode"])
                    alert("Could not delete schedule, try again later?")
                }
            } else {
                console.log("Could not get req")
                alert("Server seems to be down, try again later?")
            }
        }
    } else {
      alert("Input not recognized, failed to cancle schedule");
    }
}

function cancelMeeting(){
    var postReq = {}
    postReq["scheduleCode"] = schedule["shareCode"];
    postReq["secretCode"] = urlParams["secretCode"];
    postReq["time"] = timeOfClickedSlot;
    postReq["day"] = dayOfClickedSlot;


        console.log("JS of req:" + JSON.stringify(postReq))
        var xhr = new XMLHttpRequest();
        xhr.open("POST",organizer_cancelMeeting,true);

        console.log("PostReq:" + JSON.stringify(postReq));

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
                    console.log("Canceled Meeting")
                    loadSchedule(false)
                }
                else {
                    console.log("could not retrieve schedule, got stats" + ret["httpCode"])
                    alert("Could not cancel, try again later?")
                }
            } else {
                console.log("Could not get req")
                alert("Server seems to be down, try again later?")
            }
        }

}


function toggleTimeSlot(){
    var postReq = {}
    postReq["scheduleCode"] = schedule["shareCode"];
    postReq["secretCode"] = urlParams["secretCode"];
    postReq["time"] = timeOfClickedSlot;
    postReq["day"] = dayOfClickedSlot;

    console.log("JS of req:" + JSON.stringify(postReq))
    var xhr = new XMLHttpRequest();
    xhr.open("POST",organizer_openCloseTimeSlot,true);

    console.log("PostReq:" + JSON.stringify(postReq));

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
                console.log("Toggled Timeslot")
                loadSchedule(false)
            }
            else {
                console.log("could not retrieve schedule, got stats" + ret["httpCode"])
                alert("Could not close time slot, try again later?")
            }
        } else {
            console.log("Could not get req")
            alert("Server seems to be down, try again later?")
        }
    }
}

function openByTime(){
  console.log(open);
  var postReq = {}
  postReq["scheduleCode"] = schedule["shareCode"];
  postReq["secretCode"] = urlParams["secretCode"];
  postReq["time"] = document.getElementById("closeByTime").value;

  console.log("JS of req:" + JSON.stringify(postReq))
  var xhr = new XMLHttpRequest();
  xhr.open("POST",organizer_openByTime,true);
  console.log("PostReq:" + JSON.stringify(postReq));
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
            console.log("opened all Timeslots");
            loadSchedule(false);
          }
          else {
              console.log("could not retrieve schedule, got stats" + ret["httpCode"])
              alert("Could not open time slot, try again later?");

          }
      } else {
          console.log("Could not get req")
          alert("Server seems to be down, try again later?")
      }
  }
}

function closeByTime(){
  console.log(open);
  var postReq = {}
  postReq["scheduleCode"] = schedule["shareCode"];
  postReq["secretCode"] = urlParams["secretCode"];
  postReq["time"] = document.getElementById("closeByTime").value;

  console.log("JS of req:" + JSON.stringify(postReq))
  var xhr = new XMLHttpRequest();
  xhr.open("POST",organizer_closeByTime,true);
  console.log("PostReq:" + JSON.stringify(postReq));
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
            console.log("closed all Timeslots");
            loadSchedule(false);
          }
          else {
              console.log("could not retrieve schedule, got stats" + ret["httpCode"])
              alert("Could not close time slot, try again later?");
          }
      } else {
          console.log("Could not get req")
          alert("Server seems to be down, try again later?")
      }
  }
}

function openByDate(){
  console.log("by date"+open);
  var postReq = {}
  postReq["scheduleCode"] = schedule["shareCode"];
  postReq["secretCode"] = urlParams["secretCode"];
  postReq["date"] = document.getElementById("closeByDate").value;

  console.log("JS of req:" + JSON.stringify(postReq))
  var xhr = new XMLHttpRequest();
  xhr.open("POST",organizer_openByDate,true);
  console.log("PostReq:" + JSON.stringify(postReq));
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
            console.log("opened all Timeslots");
            loadSchedule(false);
          }
          else {
              console.log("could not retrieve schedule, got stats" + ret["httpCode"])
              alert("Could not open time slot, try again later?");

          }
      } else {
          console.log("Could not get req")
          alert("Server seems to be down, try again later?")
      }
  }
}

function closeByDate(){
  console.log("by date"+open);
  var postReq = {}
  postReq["scheduleCode"] = schedule["shareCode"];
  postReq["secretCode"] = urlParams["secretCode"];
  postReq["date"] = document.getElementById("closeByDate").value;

  console.log("JS of req:" + JSON.stringify(postReq))
  var xhr = new XMLHttpRequest();
  xhr.open("POST",organizer_closeByDate,true);
  console.log("PostReq:" + JSON.stringify(postReq));
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
            console.log("closed all Timeslots");
            loadSchedule(false);
          }
          else {
            console.log("could not retrieve schedule, got stats" + ret["httpCode"])
            alert("Could not close time slot, try again later?");

          }
      } else {
          console.log("Could not get req")
          alert("Server seems to be down, try again later?")
      }
  }
}


function extendStartDate() {
    var oldStartDate = schedule["startDateStr"];
    var newStartDate = document.getElementById("extensionDate").value;

    if (newStartDate === "" || newStartDate == null) {
        alert("Not a full date");
    }
    else {
        var oldStartComponents = oldStartDate.split("-");
        var newStartComponents = newStartDate.split("-");
        oldStart = new Date(oldStartComponents[0], oldStartComponents[1]-1, oldStartComponents[2]);
        newStart = new Date(newStartComponents[0], newStartComponents[1]-1, newStartComponents[2]);

        if (newStart >= oldStart) {
            alert("Invalid input date");
        }
        else {
            console.log("yep");

            var postReq = {};
            postReq["shareCode"] = schedule["shareCode"];
            postReq["organizerCode"] = urlParams["secretCode"];
            postReq["newStartDate"] = newStartDate;

            var xhr = new XMLHttpRequest();
            xhr.open("POST",organizer_extendStartDate,true);
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
                        console.log("Extended Start Date")
                        loadSchedule(false)
                    }
                    else {
                        console.log("could not extend start date, got stats" + ret["httpCode"])
                        alert("Could not extend start date, try again later?")
                    }
                } else {
                    console.log("Could not get req")
                    alert("Server seems to be down, try again later?")
                }
            }
        }
    }
}


function extendEndDate() {
    var oldEndDate = schedule["endDateStr"];
    var newEndDate = document.getElementById("extensionDate").value;

    if (newEndDate === "" || newEndDate == null) {
        alert("Invalid input data");
    }
    else {
        var oldEndComponents = oldEndDate.split("-");
        var newEndComponents = newEndDate.split("-");
        oldEnd = new Date(oldEndComponents[0], oldEndComponents[1]-1, oldEndComponents[2]);
        newEnd = new Date(newEndComponents[0], newEndComponents[1]-1, newEndComponents[2]);

        if (newEnd <= oldEnd) {
            alert("Invalid input date");
        }
        else {
            console.log("yep");

            var postReq = {};
            postReq["shareCode"] = schedule["shareCode"];
            postReq["organizerCode"] = urlParams["secretCode"];
            postReq["newEndDate"] = newEndDate;

            var xhr = new XMLHttpRequest();
            xhr.open("POST",organizer_extendEndDate,true);
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
                        console.log("Extended End Date")
                        loadSchedule(false)
                    }
                    else {
                        console.log("could not extend end date, got stats" + ret["httpCode"])
                        alert("Could not extend end date, try again later?")
                    }
                } else {
                    console.log("Could not get req")
                    alert("Server seems to be down, try again later?")
                }
            }
        }
    }
}
