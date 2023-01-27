var mysql      = require('mysql');
var connection = mysql.createConnection({ 
    host     : 'localhost',
    user     : 'root',
    password : 'root'
});

connection.connect();

var sql = "use API1";
  connection.query(sql, function (err, result) {
    if (err) throw err;
  });

var sql1 = "CREATE TABLE IF NOT EXISTS Users (\
Id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY ,\
username VARCHAR(255) UNIQUE, password VARCHAR(255), role VARCHAR(255),\
email VARCHAR(255), Adresse VARCHAR(255))";
  connection.query(sql1, function (err, result) {
    if (err) throw err;
  });

  var sql2 = "CREATE TABLE IF NOT EXISTS Products (\
    Id INTEGER NOT NULL PRIMARY KEY, name VARCHAR(255), price INTEGER, \
    UserId INTEGER,image VARCHAR(255), \
    FOREIGN KEY (UserID) REFERENCES Users(Id) ON DELETE CASCADE)";
  connection.query(sql2, function (err, result) {
    if (err) throw err;
  });


connection.end();

