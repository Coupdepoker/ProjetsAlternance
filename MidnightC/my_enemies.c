#include <stdio.h>
#include "Enemy.h"
#include "rpg.h"

Enemy_t **init_enemy(void){
  int size = 10;
  Enemy_t** res= malloc(size*sizeof(Enemy_t*));
  for(int i =0; i < size; i++){
    res[i] = (Enemy_t*)malloc(sizeof(Enemy_t));
    res[i]->name = Enemy_name[i];
    res[i]->hp = Enemy_hp[i];
    res[i]->mp = Enemy_mp[i];
    res[i]->str = Enemy_str[i];
    res[i]->inte = Enemy_inte[i];
    res[i]->def = Enemy_def[i];
    res[i]->res = Enemy_res[i];
    res[i]->spd = Enemy_spd[i];
    res[i]->luck = Enemy_luck[i];
  }
  return res;
}

/**
int main(){
  Enemy_t** tabEnemies = init_enemy();
  display_character(tabEnemies[9]);
}
**/
