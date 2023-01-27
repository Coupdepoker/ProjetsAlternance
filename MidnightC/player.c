//
// Created by timsit_e on 10/28/2022.
//

#include <stdio.h>
#include "Players.h"
#include "rpg.h"
/*
void my_putchar(char c)
{
    write(1, &c, 1);
}
void my_putstr(const char *str) {
    int c = 0;
    while (str[c] != '\0') {
        my_putchar(str[c]);
        c++;
    }
}
    void my_putnbr(int n) {
        if(n < 0) {
            my_putchar('-');
        }
        int length = 2;
        char* res=malloc(length*sizeof(int));
        while(n > 10 || n < -10) {
            int tmp = n%10;
            if(tmp < 0)
                res[length-1] = -n%10;
            else
                res[length-1] = n%10;
            res = realloc(res,(++length)*sizeof(int));
            n /= 10;
        }
        if(n < 0)
            res[length-1] =-n;
        else
            res[length-1] =n;
        for(int i = 1; i <length; i++){
            char c =res[length -i]+'0';
            if(c == '(')
                c = '8';
            my_putchar(c);
        }
    }
*/
Player_t** init_player(){
    int size = 5;
    Player_t** res= malloc(size*sizeof(Player_t*));
    for(int i =0; i < size; i++){
        res[i] = (Player_t*)malloc(sizeof(Player_t));
        res[i]->name = Player_name[i];
        res[i]->hp = Player_hp[i];
        res[i]->mp = Player_mp[i];
        res[i]->str = Player_str[i];
        res[i]->inte = Player_inte[i];
        res[i]->def = Player_def[i];
        res[i]->res = Player_res[i];
        res[i]->spd = Player_spd[i];
        res[i]->luck = Player_luck[i];
    }
    return res;
}
/*
void display_player(Player_t* e){
    my_putstr("name: ");
    my_putstr(e->name);
    my_putstr("    |     ");
    my_putstr("strength: ");
    my_putnbr(e->str);
    my_putstr("    |     ");
    my_putstr("hp:  ");
    for (int i = 1; i < e->hp; ++i)
    {
        my_putstr("I");
    }
    my_putstr(" ");
    my_putnbr(e->hp);
    my_putstr("\n");
}

int main(){
    Player_t** tabPlayers = init_player();
    display_player(tabPlayers[1]);

}
*/


