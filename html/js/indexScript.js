function createSchedule(){
    var Name = document.getElementById("name").value;
    var Duration = document.getElementById("duration").value;
    var StartTime = document.getElementById("st").value;
    var EndTime = document.getElementById("et").value;
    var StartDate = document.getElementById("sd").value;
    var EndDate = document.getElementById("ed").value;
    console.log(Name)
    console.log(Duration)
    console.log(StartTime)
    console.log(EndTime)
    console.log(StartDate)
    console.log(EndDate)
    if(Name && Duration && StartTime && EndTime && StartDate && EndDate)
    {
        if(StartTime > EndTime)
        {
            alert("Start time is greater than end time.")
        }
        else if(StartDate > EndDate){
            alert("Start date cannot be after end date")
        }
        else {
            var postReq = {}
            postReq["name"] = Name;
            postReq["meetingDuration"] = Duration;
            postReq["sd"] = StartDate;
            postReq["ed"] = EndDate;
            postReq["startTime"] = StartTime;
            postReq["endTime"] = EndTime;

            console.log("JS of req:" + JSON.stringify(postReq));
            var xhr = new XMLHttpRequest();
            xhr.open("POST",organizer_createSchedule,true);
            console.log(organizer_createSchedule)

            xhr.send(JSON.stringify(postReq));
            xhr.onloadend=function() {
                console.log(xhr);
                if (xhr.readyState == XMLHttpRequest.DONE) {
                    console.log ("XHR:" + xhr.responseText);
                    ret = JSON.parse(xhr.responseText)
                    console.log(ret)
                    if(ret["httpCode"] == 200){
                        console.log(ret["secretCode"]);
                        alert("Succesfully creates schedule. \nYour secret code is:  " + ret["secretCode"] + " (don't lose it)\n" + "Press OK to view your schedule")
                        window.location.href = organizerWebsite + "?secretCode=" + ret["secretCode"]
                    }
                    else {
                        alert("could not create schedule, got status" + status)
                    }
                } else {
                    alert("Did not get request")
                }
            }

        }
        // TODO: add redirection with database stuff, also display serect code
    }
    else{
        alert("Fields cannot be blank")
    }
}

function populate(selector) {
    var select = $(selector);
    var hours;
    for (var i = 0; i <= 1450; i += 60) {
        hours = Math.floor(i / 60);
        if (hours < 10) {
            hours = '0' + hours; // adding leading zero to hours portion
        }
        //add the value to dropdownlist
        select.append($('<option></option>')
            .attr('value', hours+ "00")
            .text(hours + "00"));
    }
}
//Calling the function on pageload
window.onload = function (e) {
    populate('#et');
    populate('#st');
}
