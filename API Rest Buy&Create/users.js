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



async function register(username, password, email, Adresse){

    var connection = connexion();
    const query = util.promisify(connection.query).bind(connection);
    var sql = await query("use API1");
    var id = await numberOfUser() +1;

    const saltRounds = 10;
    const salt = bcrypt.genSaltSync(saltRounds);
    const pass = bcrypt.hashSync(password, salt);
    
    
    //console.log(pass);
    //var values = [[id,username,pass,'user',email, Adresse]];
    //console.log(`karim : ${id}`);
    var sql2 = await query(`INSERT INTO Users  VALUES (id,'${username}','${pass}','user','${email}','${Adresse}');`);
    connection.end();
}

async function findUser(username){
  var connection = connexion();
  const query = util.promisify(connection.query).bind(connection);
  
  var sql = await query("use API1");
  
  var res = true;
  var values = [[username]];
  var sql1 = await query("SELECT * FROM Users WHERE username = ?", values);
  if(sql1.length === 0){
    res = false;
  }
  connection.end();
  return res;
}

async function findUserAndPassword(username, password){
  var connection = connexion();
  const query = util.promisify(connection.query).bind(connection);
  
  var sql = await query("use API1");
  
  
  var values = [[username]];

  var sql1 = await query(`SELECT password FROM Users WHERE username = '${username}'`);
  connection.end();
  if(sql1.length === 0){
    return false;
  }
  var obj = JSON.stringify(sql1);
  var p = JSON.parse(obj);
  //console.log(sql1[0].password);
  const resultat = await bcrypt.compare(password, sql1[0].password)
  //console.log(resultat);
  return resultat;
}

async function numberOfUser(){
  var connection = connexion();
  const query = util.promisify(connection.query).bind(connection);  
  var sql = await query("use API1");

  var sql1 = await query("SELECT Max(Id) AS COUNT FROM Users");

  connection.end();
  var obj = JSON.stringify(sql1);
  var p = JSON.parse(obj);

  var res = p[0].COUNT;
  return p[0].COUNT;
}

async function getId(username){
  var connection = connexion();
  const query = util.promisify(connection.query).bind(connection);  
  var sql = await query("use API1");

  var sql1 = await query(`SELECT Id FROM Users WHERE username = '${username}'`);

  connection.end();
  var obj = JSON.stringify(sql1);
  var p = JSON.parse(obj);
  //console.log(sql1);
  return p[0].Id;
}


async function getRole(username){
  var connection = connexion();
  const query = util.promisify(connection.query).bind(connection);  
  var sql = await query("use API1");

  var sql1 = await query(`SELECT role FROM Users WHERE username = '${username}'`);

  connection.end();
  var obj = JSON.stringify(sql1);
  var p = JSON.parse(obj);
  //console.log(sql1);
  return p[0].role;
}
//getId('root');

//numberOfUser();
//findUserAndPassword('riles','riles');
 module.exports = {findUser, register, findUserAndPassword, getId, connexion, getRole}; 

function test(){
  bcrypt.compare('riles', '$2b$10$BOK9bDwaPHyg1DqeilkPAeIoINIV5PnRkDnZkyu26FnS5nCOqKzoq', (err, res) => {
    if (err) {
      console.error(err)
      return
    }
    console.log(res) //true or false
  })
}

//test();