const http = require('http');
const fs = require('fs');

const converter = require('./converter');

const hostname = '127.0.0.1';
const port = 3000;

const filePath = './' + (process.argv[2]);
const csvFile = fs.readFileSync(filePath, 'utf8');

const server = http.createServer((req, res) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/plain');
  res.end('Hello World\n');
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});



let execute = converter.doConvert(csvFile);

if (execute === undefined) {
  console.error("ERROR!");
} else {
  console.log("Worked.");
  fs.writeFileSync('result.json', JSON.stringify(execute));
}
