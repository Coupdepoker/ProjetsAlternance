import 'package:flutter/material.dart';

import '../service/api_service.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key, required this.title});
  final String title;
  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  final usernameController = TextEditingController();
  final passwordController = TextEditingController();
  final emailController = TextEditingController();
  final adresseController = TextEditingController();

  Future<void> _register() async {
    await ApiService().register(usernameController.text.toString(),
        passwordController.text.toString(),
        emailController.text.toString(),
        adresseController.text.toString());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Register"),
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
                obscureText: true,
              ),
            ),
            SizedBox(height: 40,),

          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _register,
        tooltip: 'Register',
        child: const Icon(Icons.app_registration),
      ),
    );
  }
}