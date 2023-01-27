import 'dart:convert';

List<UserModel> userModelFromJson(String str) =>
    List<UserModel>.from(json.decode(str).map((x)=>UserModel.fromJson(x)));

String userModelToJson(List<UserModel> data) =>
    json.encode(List<dynamic>.from(data.map((x) => x.toJson())));

class UserModel {
  UserModel({
    required this.id,
    required this.username,
    required this.password,
    required this.role,
    required this.email,
    required this.adresse,
});
  int id;
  String username;
  String password;
  String role;
  String email;
  String adresse;

  factory UserModel.fromJson(Map<String, dynamic> json) => UserModel(
    id: json["id"],
    username: json["username"],
    password: json["password"],
    role: json["role"],
    email: json["email"],
    adresse: json["Adresse"],
  );

  Map<String, dynamic> toJson() => {
    "id": id,
    "username": username,
  };

}

