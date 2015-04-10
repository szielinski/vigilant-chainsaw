function getIMSIs() {
	$.ajax({
		get : 'GET',
		url : '../rest/query/getAllIMSIs',
		success : populateDropdown,
		contentType : 'application/json'
	});
}

function populateDropdown(data) {
	var selector = document.getElementById("imsi_dropdown");

	for (var i = 0; i < data.length; i++) {
		var imsi = data[i];
		var element = document.createElement("option");
		element.textContent = imsi;
		element.value = imsi;
		selector.appendChild(element);
	}
}

function getFailureCount(){
	var imsi = $("#imsi_dropdown").val();
	
	var url = '../rest/query/givenImsiByTimePeriod';

	var fromDateTime = moment($("#fromDateTime").data("DateTimePicker").date()).format("YYYY-DD-MM HH:mm");
	var toDateTime = moment($("#toDateTime").data("DateTimePicker").date()).format("YYYY-DD-MM HH:mm");
	
	var JSONObject = {
		"Date1" : fromDateTime,
		"Date2" : toDateTime,
		"Imsi" : imsi
	};
	JSONObject = JSON.stringify(JSONObject);
	
	$.ajax({
		url : url,
		type : "POST",
		data : JSONObject,
		contentType : "application/json",
		dataType : 'json',
		success : function(data) {
			if (data.length == 0) {
				alert("No results for lookup");
				$("#failureCount").val(0);
				$("#totalDuration").val(0);
				return;
			}
			$("#failureCount").val(data[0][0]);
			$("#totalDuration").val(data[0][1]);
		}
	});
}

getIMSIs();