//
// Created by LENOVO on 10/28/2022.
//

#ifndef PROJET_C_QUIZ_H
#define PROJET_C_QUIZ_H
typedef struct Quiz{
    char* question;
    char* answer;
}Quiz_t;

void generate_quiz(int f,Player_t* player);
#endif //PROJET_C_QUIZ_H
