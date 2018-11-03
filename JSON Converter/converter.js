module.exports = {
  doConvert: function (csv) { 
    return convert(csv);      
  }
};

let convert = (csvFile) => {
  let validFormat = validateCSVFile(csvFile); 
  if (!validFormat) {
    return null; 
  }

  let data, jsonObject;
  data = parseCSVFile(csvFile); 
  jsonObject = generateJSON(data);  

  return jsonObject;
};

let validateCSVFile = (csvFile) => {
  return true;
};

let parseCSVFile = (csvFile) => {
  let csvText = csvFile;

  let allTextLines = csvText.split(/\r\n|\n/);
  let data = [];

  for (let i = 0; i < allTextLines.length; i++) {
    if (allTextLines[i].length !== 0) {
      data[i] = CSVToArray(allTextLines[i]);
    }
  }

  return data;
};

let generateJSON = (data) => {
  let converted = {};

  merge(converted, "name", "PUBLIC_ART");
  merge(converted, "type", "FeatureCollection");

  let features = [];

  for (let i = 1; i < data.length; i++) {
    let row = data[i]; 
    let feature = {};
    merge(feature, "type", "Feature");

    let geometry = {};
    merge(geometry, "type", "Point");

    let coordinates = [];
    if ((row[18] !== null && row[18] !== undefined)
      && (typeof value !== "string" || (typeof row[18] === "string" && row[18].trim() !== ""))) {
      coordinates.push(parseNumber(row[18]));
    }
    if ((row[19] !== null && row[19] !== undefined)
      && (typeof value !== "string" || (typeof row[19] === "string" && row[19].trim() !== ""))) {
      coordinates.push(parseNumber(row[19]));
    }

    merge(geometry, "coordinates", coordinates);
    merge(feature, "geometry", geometry);

    let properties = {};
    merge(properties, "email", row[0]);
    merge(properties, "phone", row[1]);
    merge(properties, "Name", row[2]);
    merge(properties, "Address", row[3]);
    merge(properties, "Descriptn", row[4]);
    merge(properties, "id", row[5]);
    merge(properties, "category", parseNumber(row[6]));
    merge(properties, "company", row[7]);
    merge(properties, "address2", row[8]);
    merge(properties, "city", row[9]);
    merge(properties, "prov", row[10]);
    merge(properties, "pcode", row[11]);
    merge(properties, "fax", row[12]);
    merge(properties, "website", row[13]);
    merge(properties, "social_networks", row[14]);
    merge(properties, "summary", row[15]);
    merge(properties, "catname", row[16]);
    merge(properties, "kml_name", row[17]);
    merge(properties, "X", row[18]);
    merge(properties, "Y", row[19]);

    merge(feature, "properties", properties);
    features.push(feature);
  }

  merge(converted, "features", features);
  return converted;
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
  if (key)

  if (key === "category") {
    console.log(value + " - " + parseInt(value) + ": " + typeof value);
  }

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
