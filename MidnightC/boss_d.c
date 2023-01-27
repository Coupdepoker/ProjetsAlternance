#include <stdio.h>
#include <stdlib.h>
#include "displayFunctions.h"
#include "rpg.h"
#include "Boss.h"

void quest(){
    my_putstr("Who do you want to do?");
}

void wrong_input(){
    my_putstr("Wrong input.\n");
}

void question2(){
    my_putstr("Do you want to restart? (y/n)");
}




Boss_t **init_Boss (void){
    int size = 7;
    Boss_t** res= malloc(size*sizeof(Boss_t*));
  for(int i =0; i < size; i++){
    res[i] = (Boss_t*)malloc(sizeof(Boss_t));
    res[i]->name = Boss_name[i];
    res[i]->hp = Boss_hp[i];
    res[i]->mp = Boss_mp[i];
    res[i]->str = Boss_str[i];
    res[i]->inte = Boss_inte[i];
    res[i]->def = Boss_def[i];
    res[i]->res = Boss_res[i];
    res[i]->spd = Boss_spd[i];
    res[i]->luck = Boss_luck[i];
  }
  return res;
    
}

void display_character(Boss_t* e){
  printf("name: %s\n",e->name);
  printf("mp: %d\n",e->mp);
}



