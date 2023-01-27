//import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:frontfluttc2wk/model/user_model.dart';
import 'package:frontfluttc2wk/screen/createProduct.dart';
import 'package:frontfluttc2wk/screen/login.dart';
import 'package:frontfluttc2wk/screen/products.dart';
import 'package:frontfluttc2wk/screen/users.dart';

import '../service/api_service.dart';

class ProfilePage extends StatefulWidget {
  const ProfilePage({super.key, required this.title});
  final String title;
  @override
  State<ProfilePage> createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {

  final usernameController = TextEditingController();
  final passwordController = TextEditingController();
  final emailController = TextEditingController();
  final adresseController = TextEditingController();
  final usernameForAdminController = TextEditingController();

  String usernameStart = "";
  bool isAdmin = false;

  Future<UserModel> getUser() async {
    return await ApiService().getInfoUser();
  }

  @override
  void initState() {
    super.initState();
    getUser().then((value)  {
    usernameController.text = value.username;
    //passwordController.text = value.password;
    emailController.text = value.email;
    adresseController.text = value.adresse;
    usernameStart = value.username;
    setState(() {
      if(value.role != 'user'){
        isAdmin = true;
      }
    });

    //print(isAdmin);
    });
  }

  void  _getInfoUserAdmin() async{
    UserModel u = await ApiService().getInfoUserAdmin(usernameForAdminController.text.toString());
    setState(() {
      usernameController.text = u.username;
      emailController.text = u.email;
      adresseController.text = u.adresse;
    });
  }


  Future<void> _modify() async {
    if(isAdmin){
      await ApiService().modifyForAdmin(usernameForAdminController.text.toString(),
          usernameController.text.toString(),
          passwordController.text.toString(),
          emailController.text.toString(),
          adresseController.text.toString());
    }else{
      await ApiService().modify(usernameController.text.toString(),
          passwordController.text.toString(),
          emailController.text.toString(),
          adresseController.text.toString());
    }


  }

  Future<void> handleClickPopMenu(String value) async {
    switch (value) {
      case 'Products':
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const ProductPage(title: 'Products')),
        );
        break;
      case 'Users':
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const UserPage(title: 'Users')),
        );
        //print(await ApiService().readToken());
        break;
      case 'Create Products':
        Navigator.push(context,
          MaterialPageRoute(builder: (context) => const CreateProductPage(title: 'Create Products')),
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


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Profile"),
        automaticallyImplyLeading: false,
        actions: <Widget>[
          PopupMenuButton<String>(
            onSelected: handleClickPopMenu,
            itemBuilder: (BuildContext context) {
              return {'Products', 'Users','Create Products','Logout'}.map((String choice) {
                return PopupMenuItem<String>(
                  value: choice,
                  child: Text(choice),
                );
              }).toList();
            },
          ),
        ],
      ),
      body: Center(
        child: Column(
          children: [
            if (isAdmin)
              Container(
                padding: EdgeInsets.all(10),
                decoration: BoxDecoration(
                    border: Border(
                        bottom: BorderSide(color: Colors.grey)
                    )
                ),
                child: TextField(
                  controller: usernameForAdminController,
                  decoration: InputDecoration(
                      hintText: "Which user?",
                      hintStyle: TextStyle(color: Colors.grey),
                      border: InputBorder.none
                  ),
                ),
              ) ,
            Container(
              padding: EdgeInsets.all(10),
              decoration: BoxDecoration(
                  border: Border(
                      bottom: BorderSide(color: Colors.grey)
                  )
              ),
              child:
              TextField(
                controller: usernameController,

                decoration: InputDecoration(
                    hintText: "Enter your username",
                    hintStyle: TextStyle(color: Colors.grey),
                    border: InputBorder.none
                ),
              ),
            ),
            Container(
              padding: EdgeInsets.all(10),
              decoration: BoxDecoration(
                  border: Border(
                      bottom: BorderSide(color: Colors.grey)
                  )
              ),
              child: TextField(
                controller: emailController,
                decoration: InputDecoration(
                    hintText: "Enter your email",
                    hintStyle: TextStyle(color: Colors.grey),
                    border: InputBorder.none
                ),
              ),
            ),
            Container(
              padding: EdgeInsets.all(10),
              decoration: BoxDecoration(
                  border: Border(
                      bottom: BorderSide(color: Colors.grey)
                  )
              ),
              child: TextField(
                controller: adresseController,
                decoration: InputDecoration(
                    hintText: "Enter your adresse",
                    hintStyle: TextStyle(color: Colors.grey),
                    border: InputBorder.none
                ),
              ),
            ),
            Container(
              padding: EdgeInsets.all(10),
              decoration: BoxDecoration(
                  border: Border(
                      bottom: BorderSide(color: Colors.grey)
                  )
              ),
              child: TextField(
                controller: passwordController,
                decoration: InputDecoration(
                    hintText: "Enter your password",
                    hintStyle: TextStyle(color: Colors.grey),
                    border: InputBorder.none
                ),
              ),
            ),
            SizedBox(height: 40,),

          ],
        ),
      ),
      floatingActionButton : Column(
      mainAxisAlignment: MainAxisAlignment.end,
      children: [
        if(isAdmin)
          FloatingActionButton(
            onPressed: _getInfoUserAdmin,
            tooltip: 'Get Info User',
            child: const Icon(Icons.info),
          ),
        FloatingActionButton(
          onPressed: _modify,
          tooltip: 'Apply Modification',
          child: const Icon(Icons.app_registration),
        ),
        FloatingActionButton(
          tooltip: 'Delete',
          child: Icon(
              Icons.delete
          ),
          onPressed: () async {
            if(isAdmin){
              usernameStart = usernameForAdminController.text.toString();
            }
            int res = await ApiService().deleteUser(usernameStart);
            if(res == 200 && !isAdmin){
              Navigator.push(context,
                MaterialPageRoute(builder: (context) => const LoginPage(title: 'Login')),
              );
            }
          },
          heroTag: null,
        ),
      ]
      )
    );
  }
}