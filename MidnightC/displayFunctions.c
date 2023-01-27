/*
** C2W, 2022
**  	my_print_alpha/
** File description:
** 		1st task C Pool
*/

#include <unistd.h>
#include "rpg.h"
#include "displayFunctions.h"

///////////////////////////////////////////////////////////////////////////////////
void my_putchar(char c)
{
  write(1, &c, 1);
}

////////////////////////////////////////////////////////////////////////////////
void my_putstr(const char *str) {
    int c = 0;
    while (str[c] != '\0') {
        my_putchar(str[c]);
        c++;
    }
}

///////////////////////////////////////////////////////////////////////////////////
void my_putnbr(int nb) {
  int modulo;

  modulo = 0;
  if (nb <= 9 && nb >= 0)
    my_putchar(nb + '0');
  if (nb < 0)
    {
      my_putchar('-');
      nb = nb * (- 1);
      if (nb <= 9 && nb >=0)
	my_putnbr(nb);
    }
  if (nb > 9)
    {
      modulo = nb % 10;
      my_putnbr(nb / 10);
      my_putchar(modulo + '0');
    }
}

///////////////////////////////////////////////////////////////////////////////////
void display_options() {
    my_putstr("                     Please choose an option: \n [1 or attack]                                 [2 or heal]\n [3 or Run]                                    [4 or defend]\n"
              "[5 or callSonGoku]                                 [6 or Quiz]\n");
}

///////////////////////////////////////////////////////////////////////////////////
void display_player(Player_t* e){
    my_putstr("name: ");
    my_putstr(e->name);
    my_putstr("    |     ");
    my_putstr("strength: ");
    my_putnbr(e->str);
    my_putstr("    |     ");
    my_putstr("hp:  ");
    for (int i = 1; i <= e->hp; ++i)
    {
        my_putstr("I");
    }
    my_putstr(" ");
    my_putnbr(e->hp);
    my_putstr("\n");
}

///////////////////////////////////////////////////////////////////////////////////
void display_enemy(Enemy_t* e){
    my_putstr("name: ");
    my_putstr(e->name);
    my_putstr("    |     ");
    my_putstr("strength: ");
    my_putnbr(e->str);
    my_putstr("    |     ");
    my_putstr("hp:  ");
    for (int i = 1; i <= e->hp; ++i)
    {
        my_putstr("I");
    }
    my_putstr(" ");
    my_putnbr(e->hp);
    my_putstr("\n");
}

///////////////////////////////////////////////////////////////////////////////////
void display_boss(Boss_t* e){
    my_putstr("name: ");
    my_putstr(e->name);
    my_putstr("    |     ");
    my_putstr("strength: ");
    my_putnbr(e->str);
    my_putstr("    |     ");
    my_putstr("hp:  ");
    for (int i = 1; i <= e->hp; ++i)
    {
        my_putstr("I");
    }
    my_putstr(" ");
    my_putnbr(e->hp);
    my_putstr("\n");
}
