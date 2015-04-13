function getTop10IMSI() {
	var url = '../rest/query/topTenIMSIsWithFailures';

	var fromDateTime = moment($("#fromDateTime").data("DateTimePicker").date())
			.format("YYYY-MM-DD HH:mm");
	var toDateTime = moment($("#toDateTime").data("DateTimePicker").date())
			.format("YYYY-MM-DD HH:mm");
	
	if(fromDateTime == "Invalid date"){
		alert("The 'From' date field cannot be empty.");
		$("#fromDateTime").focus();
		return;
	}
	if(toDateTime == "Invalid date"){
		alert("The 'To' date field cannot be empty.");
		$("#toDateTime").focus();
		return;
	}

	var JSONObject = {
		"DateOne" : fromDateTime,
		"DateTwo" : toDateTime
	};
	JSONObject = JSON.stringify(JSONObject);

	$.ajax({
		url : url,
		type : "POST",
		data : JSONObject,
		contentType : "application/json",
		dataType : 'json',
		success : populateTable
	});
}

function populateTable(data) {
	var t = $('#datatable-1').DataTable();
	t.clear();
	
	if (data.length == 0) {
		alert("No results for lookup.");
	}

	var i;
	for (i = 0; i < data.length; i++) {
		t.row.add([ data[i][0], data[i][1] ]);
	}
	t.draw();
}