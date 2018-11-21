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

// Returns true if the CSV file is in a valid format, otherwise returns false.
// Validates only format, not contents.
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

// Note: This function should be different for each schema.
let generateJSON = (data) => {
var root = {};
var rootArray = [];
  for (let i = 1; i < data.length; i++) {
    let row = data[i];  
let root = {};
let rootitems = {};
let rootitemsproperties = {};
let rootitemspropertiesArtType = {};
let rootitemspropertiesArtist = {};
let rootitemspropertiesArtistproperties = {};
let rootitemspropertiesArtistpropertiesCountry = {};
let rootitemspropertiesArtistpropertiesEmail = {};
let rootitemspropertiesArtistpropertiesFirstName = {};
let rootitemspropertiesArtistpropertiesLastName = {};
let rootitemspropertiesArtistpropertiesWebsite = {};
let rootitemspropertiesCity = {};
let rootitemspropertiesDescription = {};
let rootitemspropertiesDimensions = {};
let rootitemspropertiesImages = {};
let rootitemspropertiesImagesitems2 = {};
let rootitemspropertiesImagesitems2properties = {};
let rootitemspropertiesImagesitems2propertiesURL = {};
let rootitemspropertiesImagesitems2propertiesphotoCredit = {};
let rootitemspropertiesInstallDate = {};
let rootitemspropertiesLatitude = {};
let rootitemspropertiesLongitude = {};
let rootitemspropertiesMedium = {};
let rootitemspropertiesOwnership = {};
let rootitemspropertiesPhoneNumber = {};
let rootitemspropertiesPostalCode = {};
let rootitemspropertiesProvince = {};
let rootitemspropertiesSiteName = {};
let rootitemspropertiesStatus = {};
let rootitemspropertiesStreetAddress = {};
let rootitemspropertiesTitleOfWork = {};
let rootitemspropertiesUnit = {};
merge(rootitems, "Artist",rootitemspropertiesArtist);
merge(rootitemspropertiesImagesitems2, "URL", row[6]);
merge(rootitemsproperties, "items2",rootitemspropertiesImagesitems2);
merge(rootitems, "Images",rootitemspropertiesImages);
merge(rootitems, "Latitude", parseNumber(row[18]));
merge(rootitems, "Longitude", parseNumber(row[19]));
merge(root, "items",rootitems);
rootArray.push(root);
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
      && (typeof value !== "object" || (typeof value === "object" && Object.getOwnPropertyNames(value).length !== 0))
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
