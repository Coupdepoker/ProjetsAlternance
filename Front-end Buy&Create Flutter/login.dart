import 'package:flutter/material.dart';
import 'package:frontfluttc2wk/screen/products.dart';
import 'package:frontfluttc2wk/screen/profile.dart';
import 'package:frontfluttc2wk/screen/register.dart';
import 'package:frontfluttc2wk/screen/users.dart';
import 'package:frontfluttc2wk/service/api_service.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key, required this.title});
  final String title;
  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  int _counter = 0;
  final usernameController = TextEditingController();
  final passwordController = TextEditingController();

  void connexion() async {
    if(await ApiService().Auth(usernameController.text.toString(), passwordController.text.toString())){
    Navigator.push(context,
    MaterialPageRoute(builder: (context) => const ProductPage(title: 'Products')),
    );
    }
  }

  void tryLogin() {
    connexion();
    setState(() async {
      _counter--;
      //ApiService().getUsers();

    });
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
        title: Text(widget.title),
        automaticallyImplyLeading: false,
        actions: <Widget>[
          PopupMenuButton<String>(
            onSelected: handleClickPopMenu,
            itemBuilder: (BuildContext context) {
              return {'Products', 'Users','Logout'}.map((String choice) {
                return
                  PopupMenuItem<String>(
                  value: choice,
                  child: Text(choice),
                );
              }).where((i) => !(i.value == 'Logout' && ApiService().readToken() != null)).toList();
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
                obscureText: true,
              ),
            ),
            SizedBox(height: 40,),
            Text(
              "Not a member?",
              style: TextStyle(color: Colors.grey),
            ),
            new GestureDetector(
              onTap: () {
                Navigator.push(context,
                  MaterialPageRoute(builder: (context) => const RegisterPage(title: 'Register')),
                );
              },
              child: new Text("Register"),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: tryLogin,
        tooltip: 'Login',
        child: const Icon(Icons.login),
      ),
    );
  }
}