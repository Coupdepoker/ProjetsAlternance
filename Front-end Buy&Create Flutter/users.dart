import 'package:flutter/material.dart';
import 'package:frontfluttc2wk/screen/login.dart';
import 'package:frontfluttc2wk/screen/products.dart';
import 'package:frontfluttc2wk/screen/profile.dart';
import 'package:frontfluttc2wk/screen/register.dart';
import 'package:frontfluttc2wk/service/api_service.dart';
import 'dart:developer';
import '../model/user_model.dart';
import 'createProduct.dart';

class UserPage extends StatefulWidget {
  const UserPage({super.key, required this.title});
  final String title;
  @override
  State<UserPage> createState() => _UserPageState();
}

class _UserPageState extends State<UserPage> {
  int _counter = 0;
  //Future<List<UserModel>> tmp = (await ApiService().getUsers()) as Future<List<UserModel>>;
  List<UserModel> items = [];

  bool isConnected = false;

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

  Future<void> searchUsers() async{
    var tmp = await ApiService().getUsers();
    setState(() {
      items = tmp;

    });

  }

  Future<void> handleClickPopMenu(String value) async {
    switch (value) {
      case 'Products':
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const ProductPage(title: 'Products')),
        );
        break;
      case 'Login':
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const LoginPage(title: 'Login')),
        );
        break;
      case 'Profile':
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const ProfilePage(title: 'Profile')),
        );
        break;
      case 'Create Product':
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const CreateProductPage(title: 'Create Product')),
        );
        break;
      case 'Logout':
        await ApiService().removeToken();
        Navigator.push(context,
        MaterialPageRoute(builder: (context) => const LoginPage(title: 'LoginPage')),
        );
        break;
    }
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
              return {'Products', 'Logout','Profile','Login','Create Product'}.map((String choice) {
                return PopupMenuItem<String>(
                  value: choice,
                  child: Text(choice),
                );
              }).where((element) => (element.value == 'Profile' && isConnected)||
                  (element.value == 'Products')||
                  (element.value == 'Login' && !isConnected)||
                  (element.value == 'Logout' && isConnected)||
                  (element.value == 'Create Product' && isConnected)
              )
                  .toList();
            },
          ),
        ],
      ),
      body: Center(
        child: Column(
          children: [
            /*
            Expanded(child:
                ListView.builder(itemBuilder: (context,index){
                  print(items.length);
                  return ListTile(title: Text("toto"));
                })
            )*/

            Expanded(
              child :
              ListView.builder(
              itemCount: items.length,
              itemBuilder: (context, index) {
                if (items.length == 0) {
                  return ListTile(title: Text(""));
                } else {
                  return ListTile(title: Text(items[index].id.toString() + " " +items[index].username));
                }
              }
              ))
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: searchUsers,
        tooltip: 'View All Users',
        child: const Icon(Icons.search),
      ),
    );
  }
}