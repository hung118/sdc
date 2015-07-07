function selectSchoolType()
{
	var schoolType = document.searchForm.schoolType.value;
	if (schoolType != "") {
		document.getElementById("schoolSelectDIV").innerHTML = '<i>Retrieving Schools...</i>';
		document.getElementById("testerSelectDIV").innerHTML = '<i>Retrieving Road Testers...</i>';
		RoadTesterService.getSchoolLabelValueList(schoolType, populateSchoolsList);
		selectSchools();
	}else{
		document.getElementById("schoolSelectDIV").innerHTML = " ";
		document.getElementById("testerSelectDIV").innerHTML = " ";
	}
}

function selectSchoolType2() {
	var schoolType = document.searchForm.schoolType.value;
	if (schoolType != "") {
		document.getElementById("schoolSelectDIV").innerHTML = '<i>Retrieving Schools...</i>';
		RoadTesterService.getSchoolLabelValueList(schoolType, populateSchoolsList);
		selectSchools2();
	} else {
		document.getElementById("schoolSelectDIV").innerHTML = " ";
	}	
}

function populateSchoolsList(results)
{
	var length = 10;
	if (results.length < 10) {
		if (results.length > 0) {
			length = results.length;
		}else{
			length = 1;
		}
	}
	var htmlBlock = '<select name="school_fk" multiple="multiple" size="'+length+'" id="schoolSelectField" onChange="selectSchools();">';
    if (results.length > 0) {
        for (var i = 0; i < results.length; ++i){
            htmlBlock += '<option value="'+results[i].value+'">'+results[i].label+'</option>';
        }
    }
	htmlBlock += '</select>';
	document.getElementById("schoolSelectDIV").innerHTML = htmlBlock;
}

function selectSchools()
{
	var schoolType = document.searchForm.schoolType.value;
	var schools = document.getElementById("schoolSelectField");
	var schoolList = new Array();
	if (schools != null) {
		for (var i=0;i<schools.options.length;i++) {
			if (schools.options[i].selected) {
				schoolList[schoolList.length] = schools.options[i].value;
			}
		}
	}
	if (schoolList.length > 0) {
		RoadTesterService.getRoadTestersBySchools(schoolList, populateRoadTesters);
	}else{
		RoadTesterService.getRoadTestersBySchoolType(schoolType, populateRoadTesters);
	}
}

function selectSchools2() {
	var schoolType = document.searchForm.schoolType.value;
	var schools = document.getElementById("schoolSelectField");
	var schoolList = new Array();
	if (schools != null) {
		for (var i=0;i<schools.options.length;i++) {
			if (schools.options[i].selected) {
				schoolList[schoolList.length] = schools.options[i].value;
			}
		}
	}
}

function populateRoadTesters(results)
{
	var length = 10;
	if (results.length < 10) {
		if (results.length > 0) {
			length = results.length;
		}else{
			length = 1;
		}
	}
	var htmlBlock = '<select name="roadinstructor_fk" multiple="multiple" size="'+length+'" id="testerSelectField">';
    if (results.length > 0) {
        for (var i = 0; i < results.length; ++i){
            htmlBlock += '<option value="'+results[i].value+'">'+results[i].label+'</option>';
        }
    }
	htmlBlock += '</select>';
	document.getElementById("testerSelectDIV").innerHTML = htmlBlock;
}
