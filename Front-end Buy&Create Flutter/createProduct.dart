import 'package:flutter/material.dart';
import 'package:frontfluttc2wk/service/api_service.dart';

class CreateProductPage extends StatefulWidget {
  const CreateProductPage({super.key, required this.title});
  final String title;
  @override
  State<CreateProductPage> createState() => _CreateProductPageState();
}

class _CreateProductPageState extends State<CreateProductPage> {

  final nameController = TextEditingController();
  final priceController = TextEditingController();
  final imageController = TextEditingController();

  void createProduct() {
    ApiService().createProduct(nameController.text.toString(), int.parse(priceController.text.toString()),
    imageController.text.toString());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Create Product"),
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
                controller: nameController,
                decoration: InputDecoration(
                    hintText: "Enter a name",
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
                controller: priceController,
                decoration: InputDecoration(
                    hintText: "Enter a price",
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
                controller: imageController,
                decoration: InputDecoration(
                    hintText: "Enter image url",
                    hintStyle: TextStyle(color: Colors.grey),
                    border: InputBorder.none
                ),
              ),
            ),

          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: createProduct,
        tooltip: 'Create Product',
        child: const Icon(Icons.app_registration),
      ),
    );
  }
}