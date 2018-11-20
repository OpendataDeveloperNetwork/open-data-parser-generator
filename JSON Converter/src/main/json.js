module.exports = {
		doConvert: function (csv) { // input: CSV file
			return convert(csv);      // output: JSON Object
		}
};

let convert = (csvFile) => {
	let validFormat = validateCSVFile(csvFile); // 1. validate CSV
	if (!validFormat) {
		return null; // TODO Through an exception?
	}

	let data, jsonObject;
	data = parseCSVFile(csvFile); // 2. parse CSV file into a string & parse CSV string into an array
	jsonObject = generateJSON(data);  // 3. Store the data into a JSON object

	return jsonObject;
};

//Returns true if the CSV file is in a valid format, otherwise returns false.
//Validates only format, not contents.
let validateCSVFile = (csvFile) => {
	return true;
};

let parseCSVFile = (csvText) => {
	let allTextLines = csvText.split(/\r\n|\n/);
	let data = [];

	// TODO: SANITIZE DATA - IF THE DATA HAS AN EMPTY CELL, THIS FOR LOOP CRASHES THIS PROGRAM.
	for (let i = 0; i < allTextLines.length; i++) {
		if (allTextLines[i].length !== 0) {
			data[i] = CSVToArray(allTextLines[i]);
		}
	}

	return data;
};

//Note: This function should be different for each schema.
let generateJSON = (data) => {
	var root = {};
	var rootArray = [];
	for (let i = 1; i < data.length; i++) {
		let row = data[i]; // TODO: CONDITIONAL STATEMENT for header rows in CSV data in JAVA
		// i should start from 1 if the CSV has a header row.
		let items = {};
		let itemsproperties = {};
		let itemspropertiesArtType = {};
		let itemspropertiesArtist = {};
		let itemspropertiesArtistproperties3 = {};
		let itemspropertiesArtistproperties3Country = {};
		let itemspropertiesArtistproperties3Email = {};
		let itemspropertiesArtistproperties3FirstName = {};
		let itemspropertiesArtistproperties3LastName = {};
		let itemspropertiesArtistproperties3Website = {};
		let itemspropertiesCity = {};
		let itemspropertiesDescription = {};
		let itemspropertiesDimensions = {};
		let itemspropertiesImages = {};
		let itemspropertiesImagesitems = {};
		let itemspropertiesImagesitemsproperties2 = {};
		let itemspropertiesImagesitemsproperties2URL = {};
		let itemspropertiesImagesitemsproperties2photoCredit = {};
		let itemspropertiesInstallDate = {};
		let itemspropertiesLatitude = {};
		let itemspropertiesLongitude = {};
		let itemspropertiesMedium = {};
		let itemspropertiesOwnership = {};
		let itemspropertiesPhoneNumber = {};
		let itemspropertiesPostalCode = {};
		let itemspropertiesProvince = {};
		let itemspropertiesSiteName = {};
		let itemspropertiesStatus = {};
		let itemspropertiesStreetAddress = {};
		let itemspropertiesTitleOfWork = {};
		let itemspropertiesUnit = {};
		merge(itemsproperties, "itemspropertiesArtType", row[4]);
		merge(itemspropertiesArtistproperties3, "itemspropertiesArtistproperties3Country", row[3]);
		merge(itemspropertiesArtistproperties3, "itemspropertiesArtistproperties3Email", row[-1]);
		merge(itemspropertiesArtistproperties3, "itemspropertiesArtistproperties3FirstName", row[-1]);
		merge(itemspropertiesArtistproperties3, "itemspropertiesArtistproperties3LastName", row[-1]);
		merge(itemspropertiesArtistproperties3, "itemspropertiesArtistproperties3Website", row[-1]);
		merge(itemspropertiesArtist, "itemspropertiesArtistproperties3",itemspropertiesArtistproperties3);
		merge(itemsproperties, "itemspropertiesArtist",itemspropertiesArtist);
		merge(itemsproperties, "itemspropertiesCity", row[3]);
		merge(itemsproperties, "itemspropertiesDescription", row[-1]);
		merge(itemsproperties, "itemspropertiesDimensions", row[-1]);
		merge(itemspropertiesImagesitemsproperties2, "itemspropertiesImagesitemsproperties2URL", row[-1]);
		merge(itemspropertiesImagesitemsproperties2, "itemspropertiesImagesitemsproperties2photoCredit", row[-1]);
		merge(itemspropertiesImagesitems, "itemspropertiesImagesitemsproperties2",itemspropertiesImagesitemsproperties2);
		merge(itemspropertiesImages, "itemspropertiesImagesitems",itemspropertiesImagesitems);
		merge(itemsproperties, "itemspropertiesImages",itemspropertiesImages);
		merge(itemsproperties, "itemspropertiesInstallDate", row[-1]);
		merge(itemsproperties, "itemspropertiesLatitude", row[-1]);
		merge(itemsproperties, "itemspropertiesLongitude", row[-1]);
		merge(itemsproperties, "itemspropertiesMedium", row[-1]);
		merge(itemsproperties, "itemspropertiesOwnership", row[-1]);
		merge(itemsproperties, "itemspropertiesPhoneNumber", row[-1]);
		merge(itemsproperties, "itemspropertiesPostalCode", row[-1]);
		merge(itemsproperties, "itemspropertiesProvince", row[-1]);
		merge(itemsproperties, "itemspropertiesSiteName", row[-1]);
		merge(itemsproperties, "itemspropertiesStatus", row[-1]);
		merge(itemsproperties, "itemspropertiesStreetAddress", row[-1]);
		merge(itemsproperties, "itemspropertiesTitleOfWork", row[-1]);
		merge(itemsproperties, "itemspropertiesUnit", row[-1]);
		merge(items, "itemsproperties",itemsproperties);
		rootArray.push(items);
	}
	merge(root, "rootArray", rootArray);
	return rootArray;
};

let CSVToArray = (strData, strDelimiter) => {
	strDelimiter = (strDelimiter || ",");

	if (strData.charAt(0) === ',') {
		strData = " " + strData;
	}

	let objPattern = new RegExp(
			(
					"(\\" + strDelimiter + "|\\r?\\n|\\r|^)" +
					"(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|" +
					"([^\"\\" + strDelimiter + "\\r\\n]*))"
			),
			"gi"
	);

	let arrData = [];
	let arrMatches;
	while (arrMatches = objPattern.exec(strData)) {
		let strMatchedValue = "";
		let strMatchedDelimiter = arrMatches[1];
		if (
				strMatchedDelimiter.length &&
				(strMatchedDelimiter !== strDelimiter)
		) {
			arrData.push([]);
		}
		if (arrMatches[2]) {
			strMatchedValue = arrMatches[2].replace(new RegExp("\"\"", "g"), "\"");
		} else {
			strMatchedValue = arrMatches[3];
		}
		arrData.push(strMatchedValue);
	}

	return (arrData);
};

let merge = (json, key, value) => { 
	if ((value !== null && value !== undefined)
			&& (typeof value !== "string" || (typeof value === "string" && value.trim() !== ""))) {
		let temp = {};
		temp[key] = value;
		Object.assign(json, temp);
	}
};

let parseBoolean = (value) => {
	return "true" === value.toLowerCase();
};

let parseNumber = (value) => {
	return Number(value);
};

let push = (arr, value) => {
	if ((value !== null && value !== undefined)
			&& (typeof value !== "string" || (typeof value === "string" && value.trim() !== ""))) {
		arr.push(value);
	}
};
