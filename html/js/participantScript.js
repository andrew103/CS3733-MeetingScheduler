var table = $('#scheduleTable');
table.on("click", "td", cellClick); //attaches the handler on the whole table, but filter the events by the "td" selector

function processCell(cellText, cellIndex, rowIndex){//organizer version
  switch(cellText){
    case "Open":
      stringDisp = "Time slot open on "+showDayTime(cellIndex, rowIndex);
      document.getElementById("scheduleMeetingText").innerHTML=stringDisp;
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
      $("#cancelMeeting").modal();
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
      if (current[i]["date"].substring(0,4) != year){
        current.splice(i,1); //remove unmatched TS from current list
        i --; //list shortens by 1
      }
    }
  }
  month = document.getElementById("month").value;
  if (month != null && month != ""){
    //if month input is not empty, do third search
    for(i=0;i<current.length;i++){
      if (current[i]["date"].substring(5,7) != month){
        current.splice(i,1); //remove unmatched TS from current list
        i --; //list shortens by 1
      }
    }
  }
  dayOfMonth = document.getElementById("dayOfMonth").value;
  if (dayOfMonth != null && dayOfMonth != ""){
    //if dayOfMonth input is not empty, do forth search
    for(i=0;i<current.length;i++){
      if (current[i]["date"].substring(8) != dayOfMonth){
        current.splice(i,1); //remove unmatched TS from current list
        i --; //list shortens by 1
      }
    }
  }
  timeSlot = document.getElementById("timeSlot").value;
  if (timeSlot != null && timeSlot != ""){
    //if timeSlot input is not empty, do last search
    for(i=0;i<current.length;i++){
      for(j=0;j<current[i]["timeslots"].length;j++){
        if (current[i]["timeslots"][j]["startTime"] != timeSlot || current[i]["timeslots"][j]["isClosed"] == true){
          current[i]["timeslots"].splice(j,1); //remove unmatched TS from current list
          j --; //list shortens by 1
        }
      }
      if (current[i]["timeslots"].length ==0){
        current.splice(i,1); //if all timeslots in a day are removed, delete day
        i --;
      }
    }
  }
  console.log(current);

  var sr = $(searchReturn);
  var txt;
  for(i=0;i<current.length;i++){
    for(j=0;j<current[i]["timeslots"].length;j++){
      txt = (current[i]["date"]+" at "+current[i]["timeslots"][j]["startTime"]);
      //sr.append($('<br>'));
      sr.append($('<h5></h5>').text(txt));
      sr.append($('<button></button>')
                .attr('class',"btn btn-success")
                .text("schedule"));
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


function populateTS(selector) {
    var select = $(selector);
    var time;
    timeslots = schedule["days"][0]["timeslots"];
    for (var i = 0; i < timeslots.length; i ++) {
        time = timeslots[i]["startTime"];
        //add the value to dropdownlist
        select.append($('<option></option>').attr('value', time).text(time));
    }
}
