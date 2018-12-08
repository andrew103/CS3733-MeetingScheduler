function createSchedule(){
    var Name = document.getElementById("name").value;
    var Duration = document.getElementById("duration").value;
    var StartTime = document.getElementById("st").value;
    var EndTime = document.getElementById("et").value;
    var StartDate = document.getElementById("sd").value;
    var EndDate = document.getElementById("ed").value;

    if(Name && Duration && StartTime && EndTime && StartDate && EndDate)
    {
        id = "test"
        alert("created schedule, now redirecting")
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
