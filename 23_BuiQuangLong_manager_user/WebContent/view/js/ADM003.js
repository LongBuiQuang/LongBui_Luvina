function hideform() {
	displayCurrent = document.getElementById('link_Japanese_Level').style;
	if (displayCurrent.display == '') {
		displayCurrent.display = 'none';
	} else {
		displayCurrent.display = '';
	}
	console.log(displayCurrent.display);
}

