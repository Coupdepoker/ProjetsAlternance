import 'dart:convert';
//import 'package:ffi/ffi.dart';
import 'dart:io';
//import 'dart:ffi';

import 'package:frontfluttc2wk/model/product_model.dart';
import 'package:frontfluttc2wk/model/user_model.dart';
import 'package:http/http.dart' as http;
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:fluttertoast/fluttertoast.dart';


class ApiService{
  final storage = new FlutterSecureStorage();

  Future<List<UserModel>> getUsers() async {
    var url = Uri.parse("http://192.168.56.101:3000/users");
    var response = await http.get(url);
    List<UserModel> users = [];
    if(response.statusCode == 200){
        //print(response.body);
        var data = json.decode(response.body);
        for (var p in data["sql1"]) {
          UserModel newUser = UserModel(id: p["Id"], username: p["username"] as String, password: '', role: '', email: '', adresse: '');
          users.add(newUser);
        }
    }
    return users;
  }

  Future<bool> Auth(String username, String password) async{
    //print(username);
    final response = await http.post(
      Uri.parse("http://192.168.56.101:3000/login"),
      headers: <String,String>{
        'Content-Type' : 'application/json; charset=UTF-8',
       //  'Authorization' : token,
      },
      body: jsonEncode(<String, String>{
        'username': username,
        'password' : password,
      })
    );
    if(response.statusCode == 200){
      var data = jsonDecode(response.body);
      var token = data["access_token"] as String;
      await storage.write(key: 'jwt', value: token);
      //print(token);
      return true;
    }
    return false;
  }

  Future<void> register(String username, String password,String email, String adresse)async {
    final response = await http.post(
        Uri.parse("http://192.168.56.101:3000/register"),
        headers: <String,String>{
          'Content-Type' : 'application/json; charset=UTF-8',
          //  'Authorization' : token,
        },
        body: jsonEncode(<String, String>{
          'username': username,
          'password' : password,
          'email' : email,
          'Adresse' : adresse
        })
    );
    if(response.statusCode == 201){
      Fluttertoast.showToast(
          msg: "Registered",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.CENTER,
          timeInSecForIosWeb: 1,
          //textColor: Colors.white,
          fontSize: 16.0
      );
    }
    //return false;
  }

  Future<void> createProduct(String name, int price, String image) async {
    var token = 'Bearer ' + (await storage.read(key: 'jwt') as String);
    final response = await http.post(
        Uri.parse("http://192.168.56.101:3000/product/create"),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
          'Authorization': token
        },
        body: jsonEncode({
          'name': name,
          'price': price,
          'image' : image,
        })
    );
    if (response.statusCode == 201) {
      Fluttertoast.showToast(
          msg: "Product Created",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.CENTER,
          timeInSecForIosWeb: 1,
          //textColor: Colors.white,
          fontSize: 16.0
      );
    }
  }

  Future<void> modify(String username, String password,String email, String adresse)async {
    var token  = 'Bearer ' + (await storage.read(key: 'jwt') as String);
    final response = await http.put(
        Uri.parse("http://192.168.56.101:3000/user/id"),
        headers: <String,String>{
          'Content-Type' : 'application/json; charset=UTF-8',
          'Authorization' : token
        },
        body: jsonEncode(<String, String>{
          'username': username,
          'password' : password,
          'email' : email,
          'Adresse' : adresse
        })
    );
    //print(response.statusCode);
    if(response.statusCode == 200){
      Fluttertoast.showToast(
          msg: "Successful",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.CENTER,
          timeInSecForIosWeb: 1,
          //textColor: Colors.white,
          fontSize: 16.0
      );
    }
    //return false;
  }

  Future<void> modifyForAdmin(String usernameForAdmin, String username, String password,String email, String adresse)async {
    var token  = 'Bearer ' + (await storage.read(key: 'jwt') as String);
    final response = await http.put(
        Uri.parse("http://192.168.56.101:3000/user/id"),
        headers: <String,String>{
          'Content-Type' : 'application/json; charset=UTF-8',
          'Authorization' : token
        },
        body: jsonEncode(<String, String>{
          'username': username,
          'password' : password,
          'email' : email,
          'Adresse' : adresse,
          'usernameForAdmin' : usernameForAdmin
        })
    );
    //print(response.statusCode);
    if(response.statusCode == 200){
      Fluttertoast.showToast(
          msg: "Successful",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.CENTER,
          timeInSecForIosWeb: 1,
          //textColor: Colors.white,
          fontSize: 16.0
      );
    }
    //return false;
  }

  Future<UserModel> getInfoUser() async {
    var url = Uri.parse("http://192.168.56.101:3000/user/id");
    final headers = {HttpHeaders.contentTypeHeader: 'application/json'};
    var token  = 'Bearer ' + (await storage.read(key: 'jwt') as String);
    var response = await http.get(url,
        headers: <String,String>{
          'Content-Type' : 'application/json; charset=UTF-8',
          'Authorization' : token
        }
    );
    UserModel user = UserModel(id: 0, username: '', password: '', role: '', email: '', adresse: '');
    if(response.statusCode == 200){
      //print(response.body);

      var data = json.decode(response.body);
      for (var p in data["sql1"]) {
        user = UserModel(id: p["Id"], username: p["username"] as String, password: '', role: p["role"], email: p["email"], adresse: p["Adresse"]);
      }
      //print(data);
    }

    return user;
  }

  Future<UserModel> getInfoUserAdmin(String username) async {
    var token  = 'Bearer ' + (await storage.read(key: 'jwt') as String);
    final queryParameters = {
      'username': username,
    };
    final uri =
    Uri.http('192.168.56.101:3000', '/user/id', queryParameters);
    final http.Response response = await http.get(
      uri,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization' : token
      },
    );
    //final headers = {HttpHeaders.contentTypeHeader: 'application/json'};
    UserModel user = UserModel(id: 0, username: '', password: '', role: '', email: '', adresse: '');
    if(response.statusCode == 200){
      //print(response.body);
      var data = json.decode(response.body);
      for (var p in data["sql1"]) {
        user = UserModel(id: p["Id"], username: p["username"] as String, password: '', role: p["role"], email: p["email"], adresse: p["Adresse"]);
      }
      //print(data);
    }

    return user;
  }

  Future<void> deleteProduct(String name) async{
    var token  = 'Bearer ' + (await storage.read(key: 'jwt') as String);
    final queryParameters = {
      'name': name,
    };
    final uri =
    Uri.http('192.168.56.101:3000', '/product', queryParameters);
    final http.Response response = await http.delete(
      uri,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization' : token
      },
    );
    if(response.statusCode == 200){
      Fluttertoast.showToast(
          msg: "Product Deleted",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.CENTER,
          timeInSecForIosWeb: 1,
          //textColor: Colors.white,
          fontSize: 16.0
      );
    }
  }

  Future<int> deleteUser(String username) async{
    var token  = 'Bearer ' + (await storage.read(key: 'jwt') as String);
    final queryParameters = {
      'username': username,
    };
    final uri =
    Uri.http('192.168.56.101:3000', '/user/id', queryParameters);
    final http.Response response = await http.delete(
      uri,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization' : token
      },
    );
    if(response.statusCode == 200){
      Fluttertoast.showToast(
          msg: "User Deleted",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.CENTER,
          timeInSecForIosWeb: 1,
          //textColor: Colors.white,
          fontSize: 16.0
      );
    }
    return response.statusCode;
  }

  Future<void> modifyProduct(String nameStart, String name, int price, String image) async{
    var token  = 'Bearer ' + (await storage.read(key: 'jwt') as String);
    final response = await http.put(
        Uri.parse("http://192.168.56.101:3000/product"),
        headers: <String,String>{
          'Content-Type' : 'application/json; charset=UTF-8',
          'Authorization' : token
        },
        body: jsonEncode({
          'nameStart': nameStart,
          'name' : name,
          'price' : price,
          'image' : image
        })
    );
    //print(response.statusCode);
    if(response.statusCode == 200){
      Fluttertoast.showToast(
          msg: "Modification Successful",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.CENTER,
          timeInSecForIosWeb: 1,
          //textColor: Colors.white,
          fontSize: 16.0
      );
    }
  }

  Future<String?> readToken() async {
    return await storage.read(key: 'jwt');
  }

  removeToken() async {
    await storage.delete(key: 'jwt');
  }

  Future<List<ProductModel>> getAllProducts() async {
      final response = await http.get(Uri.parse("http://192.168.56.101:3000/products"));
      List<ProductModel> products = [];
      if(response.statusCode == 200){
        //print(response.body);
        var data = json.decode(response.body);
        //print("ok");
        //print(data["sql1"]);

        for (var p in data["sql1"]) {
          //print("ok2");
          //print(p["Id"]);
          var image = p["image"];
          if(image == null){
            image = 'https://aeroclub-issoire.fr/wp-content/uploads/2020/05/image-not-found.jpg';
          }
          //print(image);
          ProductModel newProduct = ProductModel(id: p["Id"], name: p["name"] as String, price: p["price"], userId: p["UserId"], image : image as String);
          //ProductModel(p["created_at"], joke["icon_url"], joke["id"],
              //joke["url"], joke["value"]);
          products.add(newProduct);
        }


      }
      //print(products[0].name);
      return products;
  }

  Future<List<ProductModel>> getProductsOfUser(String username) async{
    final queryParameters = {
      'username': username,
    };
    final uri = Uri.http('192.168.56.101:3000', '/products/id', queryParameters);
    final headers = {HttpHeaders.contentTypeHeader: 'application/json'};
    final response = await http.get(uri, headers: headers);
    List<ProductModel> products = [];
    if(response.statusCode == 200){
      var data = json.decode(response.body);
      for (var p in data["sql1"]) {
        var image = p["image"];
        if(image == null){
          image = 'https://aeroclub-issoire.fr/wp-content/uploads/2020/05/image-not-found.jpg';
        }
        //print(image);
        ProductModel newProduct = ProductModel(id: p["Id"], name: p["name"] as String, price: p["price"], userId: p["UserId"], image: image as String);
        products.add(newProduct);
      }
    }
    //print(products[0].name);
    return products;
  }

}