import 'package:flutter/material.dart';
import 'package:frontfluttc2wk/service/api_service.dart';

class ModifyProductPage extends StatefulWidget {
  const ModifyProductPage({super.key, required this.title, required this.name, required this.price,required this.id, required this.image});
  final String title;
  final String name;
  final int price;
  final int id;
  final String image;
  @override
  State<ModifyProductPage> createState() => _ModifyProductPageState(name,price,id,image);
}

class _ModifyProductPageState extends State<ModifyProductPage> {

  final nameController = TextEditingController();
  final priceController = TextEditingController();
  final imageController = TextEditingController();
  String nameStart = "";
  int id = -1;

  _ModifyProductPageState(String name, int price, int id, String image){
    nameController.text = name;
    priceController.text = price.toString();
    nameStart = name;
    imageController.text = image;
    id = id;
  }

  void modifyProduct() {
    ApiService().modifyProduct(nameStart,nameController.text.toString(), int.parse(priceController.text.toString())
    ,imageController.text.toString() );
    //print(th);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Product Infos"),
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
      /*
      floatingActionButton: FloatingActionButton(
        onPressed: modifyProduct,
        tooltip: 'Create Product',
        child: const Icon(Icons.app_registration),
      ),*/
        floatingActionButton : Column(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              FloatingActionButton(
                tooltip: 'Delete',
                child: Icon(
                    Icons.delete
                ),
                onPressed: () {
                  ApiService().deleteProduct(nameStart);
                },
                heroTag: null,
              ),
              SizedBox(
                height: 10,
              ),
              FloatingActionButton(
                tooltip: 'Modify',
                child: Icon(
                    Icons.app_registration
                ),
                onPressed: () => modifyProduct(),
                heroTag: null,
              )
            ]
        )
    );
  }
}