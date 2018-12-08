var table = $('#scheduleTable');
table.on("click", "td", cellClick); //attaches the handler on the whole table, but filter the events by the "td" selector

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
