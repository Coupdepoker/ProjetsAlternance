const express = require("express");
const router = express.Router();
const jwt = require('jsonwebtoken');
const cors = require('cors');
const util = require('util');
const bcrypt = require('bcrypt');
var multiparty = require('multiparty');

const user = require("./users.js");
const product = require("./products.js");
const middle = require("./middle.js");

const PORT = 3000;
const SECRET = 'STORMBREAKERS';

const http = require('http');
const url = require('url');

const app = express();

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({extended:true}));




app.post('/register', async (req, res) => {
    if(!req.body.username || !req.body.password || !req.body.email || !req.body.Adresse){
        return res.status(400).json({ message:'error.enter name and password'});
    }
    const userExisting = await user.findUser(req.body.username);
    //console.log(`userExisting : ${userExisting}`);
    if(userExisting){
        return res.status(400).json({message : 'Error. User already exist'});
    }

    user.register(req.body.username,req.body.password, req.body.email, req.body.Adresse);
    return res.status(201).json({message: `User ${req.body.username} created`});
})

app.post('/login', async (req, res) => {
    if(!req.body.username || !req.body.password){
        return res.status(400).json({message : 'Error : enter username and password'});
    }

    const userExisting = await user.findUserAndPassword(req.body.username, req.body.password);

    if(!userExisting){
        return res.status(400).json({message: 'Error. Wrong login or password'});
    }

    const userId = await user.getId(req.body.username);
    const userRole = await user.getRole(req.body.username);
    //console.log(userId);
    const token = jwt.sign({
        id: userId,
        username : req.body.username,
        role: userRole
    }, SECRET, {expiresIn : '3 hours'});

    return res.json({ access_token: token});
})

app.get('/me', middle.checkTokenMiddleware, (req,res) => {
    const token = req.headers.authorization && middle.extractBearerToken(req.headers.authorization);
    const decoded = jwt.decode(token, {complete:false});
    return res.json({content : decoded});
}
)

app.get('/users', async (req, res) => {
  var connection = user.connexion();
  const query = util.promisify(connection.query).bind(connection);  
  var sql = await query("use API1");

  var sql1 = await query(`SELECT Id,username FROM Users`);

  connection.end();
  return res.json({sql1});
  //console.log(sql1);
  //var obj = JSON.stringify(sql1);
  //var p = JSON.parse(obj);
  //console.log(sql1);
  //return p[0].Id;
})

app.put('/user/id', middle.checkTokenMiddleware, async (req, res) => {
    const token = req.headers.authorization && middle.extractBearerToken(req.headers.authorization);
    const decoded = jwt.decode(token, {complete:false});
    /*
    if(!(decoded.role == 'user' && decoded.username == req.body.username) && !(decoded.role == 'admin')){
        return res.status(401).json({message: 'Error. Not Allowed!'});
    }*/
    const userExisting = await user.findUser(req.body.usernameForAdmin);
    if(!userExisting && decoded.role== 'admin'){
        return res.status(400).json({message : 'Error. User does not exist'});
    }
    var connection = user.connexion();
    const query = util.promisify(connection.query).bind(connection);  
    var sql = await query("use API1");
    let lastUsername = decoded.username;
    if(req.body.username){
        lastUsername = req.body.username;
    }
    if(decoded.role == 'user'){
        var sql1 = await query(`UPDATE Users SET username='${req.body.username}' WHERE username='${decoded.username}'`);
    }
    else if(decoded.role == 'admin'){
        var sql1 = await query(`UPDATE Users SET username='${req.body.username}' WHERE username='${req.body.usernameForAdmin}'`);
    }
    if(req.body.password){
        const saltRounds = 10;
        const salt = bcrypt.genSaltSync(saltRounds);
        const pass = bcrypt.hashSync(req.body.password, salt);
        var sql1 = await query(`UPDATE Users SET password='${pass}' WHERE username='${lastUsername}'`);
    }
    if(req.body.email){
        var sql1 = await query(`UPDATE Users SET email='${req.body.email}' WHERE username='${lastUsername}'`);
    }
    if(req.body.Adresse){
        var sql1 = await query(`UPDATE Users SET Adresse='${req.body.Adresse}' WHERE username='${lastUsername}'`);
    }
    connection.end();
    return res.json({message: `User ${req.body.username} modified`});
})

app.get('/user/id', middle.checkTokenMiddleware, async (req, res) => {
    const token = req.headers.authorization && middle.extractBearerToken(req.headers.authorization);
    const decoded = jwt.decode(token, {complete:false});
    const queryObject = url.parse(req.url, true).query;
    //console.log(decoded);
    if(!queryObject.username && decoded.role == 'admin'){
        return res.status(400).json({message : 'Error : enter username'});
    }
    /**
    if(!(decoded.role == 'user' && decoded.username == req.body.username) && !(decoded.role == 'admin')){
        return res.status(401).json({message: 'Error. Not Allowed!'});
    }**/
    var connection = user.connexion();
    const query = util.promisify(connection.query).bind(connection);  
    var sql = await query("use API1");

    let this_username = decoded.username;
    if(decoded.role == 'admin'){
	    this_username = queryObject.username;
    }
  
    var sql1 = await query(`SELECT * FROM Users WHERE username='${this_username}'`);
  
    connection.end();
    //console.log(sql1.length);
    if(sql1.length == 0){
        return res.status(204).json({message : 'This user does not exist!'});
    }
    return res.json({sql1});
  })

  app.delete('/user/id', middle.checkTokenMiddleware, async (req, res) => {
    const token = req.headers.authorization && middle.extractBearerToken(req.headers.authorization);
    const decoded = jwt.decode(token, {complete:false});
    const queryObject = url.parse(req.url, true).query;
    if(!queryObject.username){
        return res.status(400).json({message : 'Error : enter username'});
    }
    if(!(decoded.role == 'user' && decoded.username == queryObject.username) && !(decoded.role == 'admin')){
        return res.status(401).json({message: 'Error. Not Allowed!'});
    }
    const userExisting = await user.findUser(queryObject.username);
    if(!userExisting){
        return res.status(400).json({message : 'Error. User does not exist'});
    }
    var connection = user.connexion();
    const query = util.promisify(connection.query).bind(connection);  
    var sql = await query("use API1");

    
    //console.log(`userExisting : ${userExisting}`);
    
  
    var sql1 = await query(`DELETE FROM Users WHERE username='${queryObject.username}'`);
  
    connection.end();
    //console.log(sql1);
    
    return res.json({message: `User ${queryObject.username} deleted`});
  })
/*
  app.put('/user/id', middle.checkTokenMiddleware, async (req, res) => {
    const token = req.headers.authorization && middle.extractBearerToken(req.headers.authorization);
    const decoded = jwt.decode(token, {complete:false});
    if(!(decoded.role == 'user' && decoded.username == req.body.username) && !(decoded.role == 'admin')){
        return res.status(401).json({message: 'Error. Not Allowed!'});
    }
    const userExisting = await user.findUser(req.body.username);
    if(!userExisting){
        return res.status(400).json({message : 'Error. User does not exist'});
    }
    let lastUsername = decoded.username;
    if(req.body.username){
        lastUsername = req.body.username;
    }
    if(req.body.password){

    }
  })
*/
app.post('/product/create', middle.checkTokenMiddleware, async (req, res) => {
    const token = req.headers.authorization && middle.extractBearerToken(req.headers.authorization);
    const decoded = jwt.decode(token, {complete:false});
    var connection = user.connexion();
    const query = util.promisify(connection.query).bind(connection);  
    var sql = await query("use API1");
    if(!req.body.name || !req.body.price || !req.body.image){
        return res.status(400).json({ message:'error.enter name, price and image'});
    }
    const productExist = await product.findProduct(req.body.name);
    if(productExist){
        return res.status(400).json({message : 'Error. Product already exist'});
    }
    const productId = await product.numberOfProduct() + 1;
    var sql1 = await query(`INSERT INTO Products(Id,name,price,UserId,image) 
    values (${productId},'${req.body.name}',${req.body.price},${decoded.id},'${req.body.image}') `);
    return res.status(201).json({message: `Product ${req.body.name} created`});
   /*});*/
   
    
    
})

app.get('/product/', middle.checkTokenMiddleware, async (req, res) => {
    const token = req.headers.authorization && middle.extractBearerToken(req.headers.authorization);
    const decoded = jwt.decode(token, {complete:false});
    if(!req.body.name){
        return res.status(400).json({ message:'error.enter name'});
    }
    var connection = user.connexion();
    const query = util.promisify(connection.query).bind(connection);  
    var sql = await query("use API1");
  
    var sql1 = await query(`SELECT * FROM Products WHERE name='${req.body.name}' AND UserId =${decoded.id}`);
  
    connection.end();
    //console.log(sql1.length);
    if(sql1.length == 0){
        return res.status(204).json({message : 'This product does not exist!'});
    }
    return res.json({sql1});
})
 app.delete('/product/', middle.checkTokenMiddleware, async (req, res) => {
    const token = req.headers.authorization && middle.extractBearerToken(req.headers.authorization);
    const decoded = jwt.decode(token, {complete:false});
    const queryObject = url.parse(req.url, true).query;
    //console.log(queryObject.username);
    if(!queryObject.name){
        return res.status(400).json({ message:'error.enter name'});
    }
    /*
    if(!req.body.name){
        return res.status(400).json({message : 'Error : enter a name'});
    }
    */
    const productExist = await product.findProduct(queryObject.name);
    //console.log(productExist);
    if(!productExist){
        return res.status(400).json({message : 'Error. Product does not exist'});
    }
    const hisUserId = await product.getUserId(queryObject.name);
    if(!(decoded.role == 'admin')&&!(decoded.role == 'user' && hisUserId== decoded.id)){
        return res.status(401).json({message: 'Error. Not Allowed!'});
    }
    var connection = user.connexion();
    const query = util.promisify(connection.query).bind(connection);  
    var sql = await query("use API1");
    
  
    var sql1 = await query(`DELETE FROM Products WHERE name='${queryObject.name}' and UserId=${hisUserId}`);
  
    connection.end();
    //console.log(sql1);
    
    return res.json({message: `Product ${queryObject.name} deleted`});
 })

 app.put('/product/', middle.checkTokenMiddleware, async (req, res) => {
    const token = req.headers.authorization && middle.extractBearerToken(req.headers.authorization);
    const decoded = jwt.decode(token, {complete:false});
    const userid = await product.getUserId(req.body.nameStart);
    //console.log(req.body.nameStart);
    //console.log(userid);
    if(!(decoded.role == 'admin')&&!(decoded.role == 'user' && userid == decoded.id)){
        return res.status(401).json({message: 'Error. Not Allowed!'});
    }
    var connection = user.connexion();
    const query = util.promisify(connection.query).bind(connection);  
    var sql = await query("use API1");
    if(req.body.name){
        var sql1 = await query(`UPDATE Products SET name='${req.body.name}' WHERE name='${req.body.nameStart}'`);
    }
    if(req.body.price){
        var sql1 = await query(`UPDATE Products SET price=${req.body.price} WHERE name='${req.body.name}'`);
    }
    if(req.body.image){
        var sql1 = await query(`UPDATE Products SET image='${req.body.image}' WHERE name='${req.body.name}'`);
    }
    connection.end();
    return res.json({message: `Product ${req.body.nameStart} modified`});
 })

 app.get('/products', async (req, res) => {
        var connection = user.connexion();
        const query = util.promisify(connection.query).bind(connection);  
        var sql = await query("use API1");
      
        var sql1 = await query(`SELECT * FROM Products`);
      
        connection.end();
        return res.json({sql1});
 })

app.get('/products/id', async (req,res) => {
    //const queryString = window.location.search;
    //console.log(queryString);

    const queryObject = url.parse(req.url, true).query;
    //console.log(queryObject.username);
    if(!queryObject.username){
        return res.status(400).json({ message:'error.enter username'});
    }
    const userExisting = await user.findUser(queryObject.username);
    if(!userExisting){
        return res.status(400).json({message : 'Error. User does not exist'});
    }

    var connection = user.connexion();
    const query = util.promisify(connection.query).bind(connection);  
    var sql = await query("use API1");
    const userId = await user.getId(queryObject.username);
      
    var sql1 = await query(`SELECT * FROM Products WHERE UserId = ${userId}`);
      
    connection.end();
    return res.json({sql1});
 })

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}.`);
})

