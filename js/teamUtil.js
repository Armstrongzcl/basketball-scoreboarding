function saveToFile(index) {

	var oldTeams = "";
	plus.io.requestFileSystem(plus.io.PRIVATE_DOC, function(fs) {
		fs.root.getFile('datas/teams.json', {
			create: true
		}, function(fileEntry) {
			var reader = null;
			fileEntry.file(function(file) {
				reader = new plus.io.FileReader();
				reader.onloadend = function(e) {
					oldTeams = e.target.result;
					if(oldTeams != "") {
						teamsList = JSON.parse(oldTeams);
					}
					teamsList[index]={};
					writeTo(fileEntry, teamsList);
				};
				reader.readAsText(file);
			}, function(e) {
				alert(e.message);
			});

		})
	}, function(e) {
		alert("Request file system failed: " + e.message);
	});
}

function writeTo(fileEntry, teamsList) {
	fileEntry.createWriter(function(writer) {
		writer.write(JSON.stringify(teamsList));
		writer.onerror = function(e) {
			alert("失败" + JSON.stringify(e));
		};
		writer.onwrite = function(e) {}

	})
}