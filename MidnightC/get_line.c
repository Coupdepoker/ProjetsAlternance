#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include "Players.h"
#include "boss_d.h"
#include "displayFunctions.h"



char *my_readline(void)
{
  char    *buff;
  ssize_t size;

  buff = malloc(sizeof(*buff) * (50 + 1));
  if (buff == NULL)
    return NULL;
  size = read(0, buff, 50);
  if (size > 1) {
    buff[size - 1] = '\0';
    return buff;
  }
  free(buff);
  return NULL;
}

int interaction(){
  while(1==1){
    quest();
    char *s= my_readline();
    int i = 0;
    if(strcmp(s,"attack")==0 || strcmp(s,"1")==0)
      i= 1;
    else if(strcmp(s,"heal")==0 || strcmp(s,"2")==0)
      i= 2;
    else if(strcmp(s,"defend")==0 || strcmp(s,"4")==0)
      i= 4;
    else if(strcmp(s,"Run")==0 || strcmp(s,"3")==0)
      i= 3;
    else if(strcmp(s,"callSonGoku")==0 || strcmp(s,"5")==0)
      i= 5;
    else if(strcmp(s,"Quiz")==0 || strcmp(s,"6")==0)
      i= 6;
    else
      wrong_input();
    free(s);
    if(i!=0){
      return i;
    }
  }
}

void enemyAttack(void* x, Player_t* p,char* type){
  my_putstr("The Enemy Attacks!\n");
  if(strcmp("enemy",type)==0)
    p->hp -= ((Enemy_t *)x)->str;
  else if(strcmp("boss",type)==0)
    p->hp -= ((Boss_t *)x)->str;
  if(p->hp < 0)
    p->hp = 0;
}

void playerAttack(void* x, Player_t* p,char* type){
  if(strcmp("enemy",type)==0){
    ((Enemy_t *)x)->hp -= p->str;
    if(((Enemy_t *)x)->hp < 0)
      ((Enemy_t *)x)->hp = 0;
  }
  else if(strcmp("boss",type)==0){
    ((Boss_t *)x)->hp -= p->str;
    if(((Boss_t *)x)->hp < 0)
      ((Boss_t *)x)->hp = 0;
  }
}

void heal(int max_hp, Player_t* p){
  int new_hp = p->hp + (max_hp/2);
  if(new_hp > max_hp)
    p->hp = max_hp;
  else
    p->hp = new_hp;
}

void defend(void* x, Player_t* p,char* type){
  if(strcmp("enemy",type)==0)
    p->hp += ((Enemy_t *)x)->str;
  else if(strcmp("boss",type)==0)
    p->hp += ((Boss_t *)x)->str;
}

int gameover(){
  while(1==1){
    question2();
    char *s= my_readline();
    int i = 0;
    if(strcmp(s,"y")==0)
      i=1;
    else if(strcmp(s,"n")==0)
      i=2;
    else
      wrong_input();
    free(s);
    if(i!=0)
      return i;
  }
}

void callSonGoku(void* x,char* type){
  int attack = 10000;
  if(strcmp("enemy",type)==0){
    ((Enemy_t *)x)->hp -= attack;
    if(((Enemy_t *)x)->hp < 0)
      ((Enemy_t *)x)->hp = 0;
  }
  else if(strcmp("boss",type)==0){
    ((Boss_t *)x)->hp -= attack;
    if(((Boss_t *)x)->hp < 0)
      ((Boss_t *)x)->hp = 0;
  }
}

/*
void *selectCharacter(void** tab){
  int size = sizeof(tab)/sizeof(tab[0]);
  return tab[];
}

*/

		 
/*
int main(){
  int i = 0;
  while(1==1){
    char* s= my_readline();
    printf("%s : %d\n",s,i);
    i++;
  }
}
*/
