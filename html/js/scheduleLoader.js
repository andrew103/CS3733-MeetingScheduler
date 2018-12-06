
schedule = {
  "startDate": "2017-04-17",
  "endDate": "2017-04-21",
  "meetingDuration": 15,
  "name": "I wanna be tracer",
  "days": [
    {
      "date":"2017-04-17",
      "dayStart": 1200,
      "dayEnd": 1300,
      "timeslots": [
        {
          "isClosed": true,
          "startTime": 1200,
          "participantInfo":""
        },
        {
          "isClosed": false,
          "startTime": 1215,
          "participantInfo":""
        },
        {
          "isClosed": true,
          "startTime": 1230,
          "participantInfo":"Hit or miss"
        },
        {
          "isClosed": false,
          "startTime": 1245,
          "participantInfo":""
        }
      ]
    },
    {
      "date":"2017-04-18",
      "dayStart": 1200,
      "dayEnd": 1300,
      "timeslots": [
        {
          "isClosed": true,
          "startTime": 1200,
          "participantInfo":""
        },
        {
          "isClosed": false,
          "startTime": 1215,
          "participantInfo":""
        },
        {
          "isClosed": true,
          "startTime": 1230,
          "participantInfo":"I guess they never hiss HUH!"
        },
        {
          "isClosed": false,
          "startTime": 1245,
          "participantInfo":""
        }
      ]
    },
    {
      "date":"2017-04-19",
      "dayStart": 1200,
      "dayEnd": 1300,
      "timeslots": [
        {
          "isClosed": true,
          "startTime": 1200,
          "participantInfo":""
        },
        {
          "isClosed": false,
          "startTime": 1215,
          "participantInfo":""
        },
        {
          "isClosed": true,
          "startTime": 1230,
          "participantInfo":"You got a boyfriend?"
        },
        {
          "isClosed": false,
          "startTime": 1245,
          "participantInfo":""
        }
      ]
    },
    {
      "date":"2017-04-20",
      "dayStart": 1200,
      "dayEnd": 1300,
      "timeslots": [
        {
          "isClosed": true,
          "startTime": 1200,
          "participantInfo":""
        },
        {
          "isClosed": false,
          "startTime": 1215,
          "participantInfo":""
        },
        {
          "isClosed": true,
          "startTime": 1230,
          "participantInfo":"I bet he doesnt kiss yah MWAH"
        },
        {
          "isClosed": false,
          "startTime": 1245,
          "participantInfo":""
        }
      ]
    },
    {
      "date":"2017-04-21",
      "dayStart": 1200,
      "dayEnd": 1300,
      "timeslots": [
        {
          "isClosed": true,
          "startTime": 1200,
          "participantInfo":""
        },
        {
          "isClosed": false,
          "startTime": 1215,
          "participantInfo":""
        },
        {
          "isClosed": true,
          "startTime": 1230,
          "participantInfo":"He gonna find another girl and he wont miss YAH"
        },
        {
          "isClosed": false,
          "startTime": 1245,
          "participantInfo":""
        }
      ]
    }
  ]
}

schedule = {
  "endDate": "2017-05-21",
  "name": "Sample Schedule",
  "meetingDurating": 15,
  "days": [
    {
      "date": "2017-05-17",
      "dayStart": 1200,
      "dayEnd": 1300,
      "timeslots": [
        {
          "": "",
          "isClosed": true,
          "startTime": 1200
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1215
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1230
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1245
        }
      ]
    },
    {
      "date": "2017-05-18",
      "dayStart": 1200,
      "dayEnd": 1300,
      "timeslots": [
        {
          "": "",
          "isClosed": true,
          "startTime": 1200
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1215
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1230
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1245
        }
      ]
    },
    {
      "date": "2017-05-19",
      "dayStart": 1200,
      "dayEnd": 1300,
      "timeslots": [
        {
          "": "",
          "isClosed": true,
          "startTime": 1200
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1215
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1230
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1245
        }
      ]
    },
    {
      "date": "2017-05-20",
      "dayStart": 1200,
      "dayEnd": 1300,
      "timeslots": [
        {
          "": "",
          "isClosed": true,
          "startTime": 1200
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1215
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1230
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1245
        }
      ]
    },
    {
      "date": "2017-05-21",
      "dayStart": 1200,
      "dayEnd": 1300,
      "timeslots": [
        {
          "": "",
          "isClosed": true,
          "startTime": 1200
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1215
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1230
        },
        {
          "": "",
          "isClosed": true,
          "startTime": 1245
        }
      ]
    }
  ],
  "startDate": "2017-05-17"
}
//Fetches the url paramaters and sets them to the variable
var urlParams;
(window.onpopstate = function () {
    var match,
        pl     = /\+/g,  // Regex for replacing addition symbol with a space
        search = /([^&=]+)=?([^&]*)/g,
        decode = function (s) { return decodeURIComponent(s.replace(pl, " ")); },
        query  = window.location.search.substring(1);

    urlParams = {};
    while (match = search.exec(query))
       urlParams[decode(match[1])] = decode(match[2]);
})();




//TODO, add check for valid id
if(urlParams["id"]){

    //TODO update schedule to be from id
    document.getElementById("name").innerText = schedule["name"];

    document.getElementById("weekDate").value = schedule["startDate"];
    //Populating an empty schedule so the updateSchedule function can fill it in
   
    var scheduleTable = document.getElementById("scheduleTable").getElementsByTagName('tbody')[0];

    var firstDay = schedule["days"][0]
    var duration = schedule["meetingDuration"];
    for(i = 0; i < firstDay["timeslots"].length; i++)
    {
        var row = scheduleTable.insertRow(i);
        timeCell = row.insertCell(0)
        
        timeCell.innerHTML = firstDay["timeslots"][i]["startTime"]
        
        for(j = 0; j < 5; j++){
            row.insertCell(j+1) 
        }
    }

    console.log(scheduleTable)

    updateSchedule(schedule["startDate"])
}
else{
    //TODO: redirect to homepage and delete this alert
    alert("Invalid id")

    //TODO, make this the pre-signed url
    //window.location.href = "index.html";
    
}

function dateSelect(){
    updateSchedule(document.getElementById("weekDate").value);
}

//takes in a start date, finds the appropriate sunday and populates the schedule
function updateSchedule(startDate){

    
    startDate = new Date(startDate);

    console.log(startDate);

    var days = schedule["days"]
    var prevSunday = new Date(startDate.setHours(-24 * startDate.getDay()));
    console.log(startDate.getDay());
    console.log(prevSunday);

    var scheduleTable = document.getElementById("scheduleTable").getElementsByTagName('tbody')[0];
    //checking if each day exists in the schedule, if it doesn't we place a closed day
    //TODO refactor code, this is super ineffecient
    for(x = 1; x < 6; x++){
        var found = false;
        var index;
        for(y = 0; y < days.length; y++){
            
            var difference = Math.ceil((new Date(days[y]["date"]) - prevSunday)/(1000*60*60*24));
            console.log(difference)
            if(difference == x){
                index = y
                found = true;
                break;
            }
        }

        //scheduleTable.rows[y].cells[x].innerHTML = days[y]["date"];            
        if(found){
           
            var timeslots = days[index]["timeslots"]
            for(y = 0; y < timeslots.length; y++){
                if(timeslots[y]["isClosed"]){
                    if(timeslots[y]["participantInfo"]){
                        scheduleTable.rows[y].cells[x].innerHTML = 'Booked by:' + timeslots[y]["participantInfo"];
                    }
                    else{
                        scheduleTable.rows[y].cells[x].innerHTML = 'Closed';
                    }
                
                }

                else{
                    scheduleTable.rows[y].cells[x].innerHTML = 'Open';
                }
            }
            
        }
        else{
            for(y = 0; y < days[0]["timeslots"].length; y++){
                scheduleTable.rows[y].cells[x].innerHTML = 'TOMAKEGREY';            
            }
        }
    }

}

