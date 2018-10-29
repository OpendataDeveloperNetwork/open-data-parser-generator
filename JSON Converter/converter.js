let fileInput = document.getElementById("csv");

let readFile = function () {
  let reader = new FileReader();
  reader.onload = function () {
    parse(reader.result);
  };
  reader.readAsText(fileInput.files[0], 'utf8');
};
fileInput.addEventListener('change', readFile);

function CSVToArray(strData, strDelimiter) {
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
  let arrMatches = null;
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
      strMatchedValue = arrMatches[2].replace(
        new RegExp("\"\"", "g"),
        "\""
      );
    } else {
      strMatchedValue = arrMatches[3];
    }
    arrData.push(strMatchedValue);
  }

  return (arrData);
}

function parse(csv) {
  let allTextLines = csv.split(/\r\n|\n/);
  let data = [];

  // TODO: SANITIZE DATA - IF THE DATA HAS AN EMPTY CELL, THIS FOR LOOP CRASHES THIS PROGRAM.
  for (let i = 0; i < allTextLines.length; i++) {
    if (allTextLines[i].length !== 0) {
      data[i] = CSVToArray(allTextLines[i]);
    }
  }
  arrayToJSON(data);
  return data;
}

function arrayToJSON(arr) {
  let converted = {};
  converted.name = "PUBLIC_ART";
  converted.type = "FeatureCollection";
  converted.features = [];

  console.log(arr);

  for (let i = 0; i < arr.length - 1; i++) {
    let row = arr[i + 1]; // TODO: CONDITIONAL STATEMENT for header rows in CSV data
    let feature = {};

    feature.type = "Feature";

    let geometry = {};
    geometry.type = "Point";
    geometry.coordinates = [row[18], row[19]];

    feature.geometry = geometry;

    let properties = {};

    properties.email = row[0];
    properties.phone = row[1];
    properties.Name = row[2];
    properties.Address = row[3];
    properties.Descriptn = row[4];
    properties.id = row[5];
    properties.category = row[6];
    properties.company = row[7];
    properties.address2 = row[8];
    properties.city = row[9];
    properties.prov = row[10];
    properties.pcode = row[11];
    properties.fax = row[12];
    properties.website = row[13];
    properties.social_networks = row[14];
    properties.summary = row[15];
    properties.catname = row[16];
    properties.kml_name = row[17];
    properties.X = row[18];
    properties.Y = row[19];

    feature.properties = properties;
    converted.features.push(feature);
  }

  downloadObjectAsJson(converted, "Sampledata-json-public-art");
  return JSON.stringify(converted, null, 4);
}


function downloadObjectAsJson(exportObj, exportName) {
  let dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(exportObj));
  let downloadAnchorNode = document.createElement('a');
  downloadAnchorNode.setAttribute("href", dataStr);
  downloadAnchorNode.setAttribute("download", exportName + ".json");
  document.body.appendChild(downloadAnchorNode); // required for firefox
  downloadAnchorNode.click();
  downloadAnchorNode.remove();
}
