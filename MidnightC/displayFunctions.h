//
// Created by timsit_e on 10/28/2022.
//
#ifndef PROJET_C_DISPLAYFUNCTIONS_H
#define PROJET_C_DISPLAYFUNCTIONS_H
#include "rpg.h"

void my_putchar(char c);
void my_putnbr(int n);
void my_putstr(const char *str);
void display_options();
void display_player(Player_t* e);
void display_enemy(Enemy_t* e);
void display_boss(Boss_t* e);
#endif //PROJET_C_DISPLAYFUNCTIONS_H
