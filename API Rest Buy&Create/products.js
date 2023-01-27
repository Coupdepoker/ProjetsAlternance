const util = require('util');
const bcrypt = require('bcrypt');

function connexion(){
    var mysql      = require('mysql');
    var connection = mysql.createConnection({ 
        host     : 'localhost',
        user     : 'root',
        password : 'root'
    });
    return connection;
}

async function findProduct(name){
    var connection = connexion();
    const query = util.promisify(connection.query).bind(connection);
    
    var sql = await query("use API1");
    
    var res = true;
    var values = [[name]];
    var sql1 = await query("SELECT * FROM Products WHERE name = ?", values);
    //console.log(sql1.length);
    if(sql1.length === 0){
      res = false;
    }
    connection.end();
    //console.log(res);
    return res;
  }

  async function numberOfProduct(){
    var connection = connexion();
    const query = util.promisify(connection.query).bind(connection);  
    var sql = await query("use API1");
  
    var sql1 = await query("SELECT Max(Id) AS COUNT FROM Products");
  
    connection.end();
    var obj = JSON.stringify(sql1);
    var p = JSON.parse(obj);
  
    var res = p[0].COUNT;
    return p[0].COUNT;
  }

  async function getUserId(name){
    var connection = connexion();
    const query = util.promisify(connection.query).bind(connection);  
    var sql = await query("use API1");
    //console.log(name);
    var sql1 = await query(`SELECT UserId FROM Products WHERE name = '${name}'`);
  
    connection.end();
    var obj = JSON.stringify(sql1);
    var p = JSON.parse(obj);
    //console.log(p[0].UserId);
    return p[0].UserId;
  }

  //findProduct('coco');
  //getUserId('pancakes');

  module.exports = {findProduct, numberOfProduct, getUserId}; 