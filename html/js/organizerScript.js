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
