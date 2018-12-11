var table = $('#scheduleTable');
table.on("click", "td", cellClick); //attaches the handler on the whole table, but filter the events by the "td" selector

isOrganizer = true;

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
        txt = "The entire schedule is cancled";
    } else {
        txt = "Input not recognized, failed to cancle schedule";
    }
    alert(txt);
}

function cancelMeeting(){
    alert("add cancel meeting functionality");
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
