
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



//**INITIALIZATION CODE**
//TODO, add check for valid id
if(urlParams["id"]){

    //TODO update schedule to be from id
    console.log(document.getElementById("name"));
    document.getElementById("name").innerText = schedule["name"];
    console.log(document.getElementById("weekDate"));
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

    updateSchedule(schedule["startDate"], urlParams["view"])
}
else{
    //TODO: redirect to homepage and delete this alert
    alert("Invalid id")

    //TODO, make this the pre-signed url
    //window.location.href = "index.html";
}

function cellClick(x) {
  td = $(x.target).closest('td');
  cellText = td.text();
  cellIndex = td.index();
  rowIndex = td.parent().index();
  console.log("clicking row: "+rowIndex+" cell: "+cellIndex+" text: "+cellText);
  processCell(cellText, cellIndex, rowIndex);
}

function changeDate(value){
    console.log("input"+value)
    weekDate = document.getElementById("weekDate").value;
    date = new Date(weekDate);
    console.log(date);
    date.setDate(date.getDate()+parseInt(value,10)); //parse input text into int
    if (value != 0){
      date.setDate(date.getDate()+1); //need to add one for formatDate()
      document.getElementById("weekDate").value = formatDate(date);
    }
    updateSchedule(date, urlParams["view"]);
}

function returnDate(value){
  console.log("input"+value)
  weekDate = document.getElementById("weekDate").value;
  date = new Date(weekDate);
  console.log(date);
  date.setDate(date.getDate()+parseInt(value,10)+1); //parse input text into int
  console.log(formatDate(date));
  return formatDate(date);
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}

function showDayTime(cellIndex, rowIndex){
  //TODO get actual meeting time from JSON
  switch(cellIndex){
    case 1:
      day = "Monday";
      date = returnDate(0); //base reference
      break;
    case 2:
      day = "Tuesday";
      date = returnDate(1);
      break;
    case 3:
      day = "Wednesday";
      date = returnDate(2);
      break;
    case 4:
      day = "Thursday";
      date = returnDate(3);
      break;
    case 5:
      day = "Friday";
      date = returnDate(4);
      break;
    default:
      day = "Invalid day of week";
  }
  days = schedule["days"];
  for (i = 0; i < days.length; i++){
    if (days[i]["date"] == date){
      time = days[i]["timeslots"][rowIndex]["startTime"];
      break;
    }
  }
  return date+", "+day+" at "+time;
}

//takes in a start date, finds the appropriate sunday and populates the schedule
function updateSchedule(startDate, organizerView){

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
                        if (organizerView == 1){
                          scheduleTable.rows[y].cells[x].innerHTML = 'Booked by:' + timeslots[y]["participantInfo"];
                        }
                        else {
                          scheduleTable.rows[y].cells[x].innerHTML = 'Taken';
                        }
                        scheduleTable.rows[y].cells[x].style.backgroundColor = '#ffff99';
                    }
                    else{
                        scheduleTable.rows[y].cells[x].innerHTML = 'Closed';
                        scheduleTable.rows[y].cells[x].style.backgroundColor = '#ff4d4d';
                    }

                }

                else{
                    scheduleTable.rows[y].cells[x].innerHTML = 'Open';
                    scheduleTable.rows[y].cells[x].style.backgroundColor = '#66ff99';
                }
            }

        }
        else{
            for(y = 0; y < days[0]["timeslots"].length; y++){
                scheduleTable.rows[y].cells[x].innerHTML = 'Not on schedule';
                scheduleTable.rows[y].cells[x].style.backgroundColor = "gray";
            }
        }
    }

}
