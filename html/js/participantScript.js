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
      stringDisp = "Time slot unavailable on "+showDayTime(cellIndex, rowIndex)+". "+cellText;
      alert(stringDisp);
  }
}