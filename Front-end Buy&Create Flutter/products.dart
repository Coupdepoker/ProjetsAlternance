//import 'dart:html';

import 'package:flutter/material.dart';
import 'package:frontfluttc2wk/model/product_model.dart';
import 'package:frontfluttc2wk/model/user_model.dart';
import 'package:frontfluttc2wk/screen/createProduct.dart';
import 'package:frontfluttc2wk/screen/login.dart';
import 'package:frontfluttc2wk/screen/modifyProduct.dart';
import 'package:frontfluttc2wk/screen/profile.dart';
import 'package:frontfluttc2wk/screen/register.dart';
import 'package:frontfluttc2wk/service/api_service.dart';
import 'package:frontfluttc2wk/screen/users.dart';


import 'package:fluttertoast/fluttertoast.dart';

class ProductPage extends StatefulWidget {
  const ProductPage({super.key, required this.title});
  final String title;
  @override
  State<ProductPage> createState() => _ProductPageState();
}

class _ProductPageState extends State<ProductPage> {
  int _counter = 0;
  int _selectedIndex = 0;
  List<ProductModel> items =  [];
  final usernameController = TextEditingController();
  bool isConnected = false;
  String path = 'C:\Users\ramia\etna\group-994508\Front\frontc2wk\src\app\assets\peche.jpeg';


  Future<String?> getToken() async {
    return await ApiService().readToken();
  }

  @override
  void initState() {
    super.initState();
    getToken().then((value)  {
      setState(() {
        if(value != null){
          isConnected = true;
        }
      });
      //print(isConnected);
    });
  }

  void tryLogin() {
    setState(() {
      _counter--;
      //ApiService().getUsers();
    });
  }

  Future<void> handleClickPopMenu(String value) async {
    switch (value) {
      case 'Users':
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const UserPage(title: 'Users')),
        );
        break;
      case 'Logout':
        await ApiService().removeToken();
        print(await ApiService().readToken());
        Navigator.push(context,
        MaterialPageRoute(builder: (context) => const LoginPage(title: 'LoginPage')),
        );
        break;
      case 'Profile':
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const ProfilePage(title: 'Profile')),
        );
        break;
      case 'Login':
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const LoginPage(title: 'Login')),
        );
        break;
      case 'Create Product':
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const CreateProductPage(title: 'Create Product')),
        );
        break;
      case 'Logout':
        await ApiService().removeToken();
        print(await ApiService().readToken());
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const LoginPage(title: 'LoginPage')),
        );
        break;
    }
  }

  Future<void> _onItemTapped(int index) async {
    if(index == 1){
      items = await ApiService().getAllProducts();
    }else if(index == 0){
      items = await ApiService().getProductsOfUser(usernameController.text.toString());
    }else if(index == 2){
      if(await ApiService().readToken() != null){
        UserModel user = await ApiService().getInfoUser();
        items = await ApiService().getProductsOfUser(user.username);
      }else{
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const LoginPage(title: 'Login')),
        );
      }
    }
    setState(()  {
      _selectedIndex = index;
    });

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
        automaticallyImplyLeading: false,
        actions: <Widget>[
          PopupMenuButton<String>(
            onSelected: handleClickPopMenu,
            itemBuilder: (BuildContext context) {
              return { 'Users', 'Logout','Profile','Login','Create Product'}.map((String choice) {
                return PopupMenuItem<String>(
                  value: choice,
                  child: Text(choice),
                );
              }).where((element) => (element.value == 'Profile' && isConnected)||
                  (element.value == 'Users')||
                  (element.value == 'Login' && !isConnected) ||
                  (element.value == 'Create Product' && isConnected) ||
                  (element.value == 'Logout' && isConnected)
              )
                  .toList();
            },
          ),
        ],
      ),
      body: Center(
        child: Column(
          children: [
            Container(
              padding: EdgeInsets.all(10),
              decoration: BoxDecoration(
                  border: Border(
                      bottom: BorderSide(color: Colors.grey)
                  )
              ),
              child: TextField(
                controller: usernameController,
                decoration: InputDecoration(
                    hintText: "Enter your username",
                    hintStyle: TextStyle(color: Colors.grey),
                    border: InputBorder.none
                ),
              ),
            ),
            Expanded(
              //height: double.infinity,
              child: ListView.builder(
                itemCount: items.length,
                itemBuilder: (context, index) {
                  if(items.length == 0){
                    return ListTile(
                      title: Text(""),
                    );
                  }else {
                    return ListTile(
                      title: Text(items[index].name),
                      subtitle: Column(
                        children: <Widget>[
                          Image.network(
                            items[index].image,
                            width: 200,
                            height: 200,
                          ),
                          Text(items[index].price.toString() + " €"),
                          ButtonBar(
                            children: [
                              ElevatedButton(
                                child: Text("Buy"),
                                onPressed: (){},
                              ),
                              ElevatedButton(
                                child: Text("Product Infos"),
                                onPressed: () async{
                                  if(await ApiService().readToken() != null){
                                    Navigator.push(context,
                                      MaterialPageRoute(builder: (context) =>  ModifyProductPage(title: 'Product Infos', name : items[index].name as String,price : items[index].price, id : items[index].id, image : items[index].image)),
                                    );
                                  }else{
                                    Navigator.push(context,
                                    MaterialPageRoute(builder: (context) => const LoginPage(title: 'Login')),
                                    );
                                  }
                                },
                              ),
                            ],
                          )
                        ],
                      ),
                    );
                  }

                },
              ),
            )
          ],
        ),
      ),

      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Products of this user',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.business),
            label: 'All Products',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.school),
            label: 'My Products',
          ),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.amber[800],
        onTap: _onItemTapped,
      ),
      /*
      floatingActionButton: FloatingActionButton(
        onPressed: tryLogin,
        tooltip: 'Increment',
        child: const Icon(Icons.login),
      ),*/
    );
  }
}