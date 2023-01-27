#include <stdlib.h>
#include "Enemy.h"
#include "my_enemies.h"
#include "get_line.h"
#include "boss_d.h"
#include "displayFunctions.h"
#include "player.h"
#include "quiz.h"

void display_begin_fight(int i){
  my_putstr("=== FIGHT ");
  my_putnbr(i);
  my_putstr(" ===\n");
}

void display_num_calls(int x){
  my_putstr("You have ");
  my_putnbr(x);
  my_putstr(" calls left.\n");
}

void freePlayers(Player_t** tabPlayer){
  for(int i=0; i<5; i++){
    free(tabPlayer[i]);
  }
  free(tabPlayer);
}

void freeBoss(Boss_t** tabBoss){
  for(int i=0; i<7; i++){
    free(tabBoss[i]);
  }
  free(tabBoss);
}

void freeEnemies(Enemy_t** tabEnemies){
  for(int i=0; i<10; i++){
    free(tabEnemies[i]);
  }
  free(tabEnemies);
}

int main(){
  while(1==1){
    Player_t** tabPlayer = init_player();
    Enemy_t** tabEnemies = init_enemy();
    Boss_t** tabBoss = init_Boss();
    Player_t* player = tabPlayer[rand()%5];
    int maxHP = player->hp;
    int game = 2;
    int gokuCall = 3;
    int quizz = 1;
    for(int i = 1; i <= 9; i++){
      Enemy_t* enemy = tabEnemies[rand() % 10];
      quizz = 1;
      system("clear");
      display_begin_fight(i);
      do{
	display_player(player);
	display_enemy(enemy);
	display_options();
	display_num_calls(gokuCall);
	switch(interaction()){
	  case 1:
	    my_putstr("Player attacks.\n");
	    playerAttack((void*)enemy,player,"enemy");
	    break;
	  case 2:
	    my_putstr("Player heals.\n");
	    heal(maxHP, player);
	    break;
	  case 3:
	    my_putstr("Game over.\n");
	    game = gameover();
	    break;
	  case 4:
	    my_putstr("Shield Activated.\n");
	    defend((void*)enemy,player,"enemy");
	    break;
	  case 5:
	    if(gokuCall > 0){
	      my_putstr("Son Goku arrives.\n");
	      my_putstr("Ka-me-ha-me-ha! Your Enemy loses 10000 points.\n");
	      callSonGoku((void*)enemy,"enemy");
	      gokuCall--;
	    }else{
	      my_putstr("No more calls left.\n");
	    }
	    break;
	  case 6:
	    if(quizz == 1){
	      quizz--;
	      generate_quiz(i,player);
	      my_putstr("Shield Activated during the quizzzzzz.\n");
	      defend((void*)enemy,player,"enemy");
	    }else{
	      my_putstr("You already have done the quizz.\n");
	    }
	    break;
	  }
	if(enemy->hp > 0)
	     enemyAttack((void *)enemy, player, "enemy");
       }while(player->hp > 0 && enemy->hp > 0 && game == 2);
      freeEnemies(tabEnemies);
      tabEnemies = init_enemy();
      if(game == 1 || player->hp <= 0)
	break;
    }
    
    Boss_t* boss = tabBoss[rand() % 7];
    quizz = 1;
    if(game == 2 && player->hp > 0){
      system("clear");
      my_putstr("=== FIGHT THE BOSS ===\n");
      do{
	display_player(player);
	display_boss(boss);
	display_options();
	display_num_calls(gokuCall);
	switch(interaction()){
	case 1:
	  my_putstr("Player attacks.\n");
	  playerAttack((void*)boss,player,"boss");
	  break;
	case 2:
	  my_putstr("Player heals.\n");
	  heal(maxHP, player);
	  break;
	case 3:
	  my_putstr("Game over.\n");
	  game = gameover();
	  break;
	case 4:
	  my_putstr("Shield Activated.\n");
	  defend((void*)boss,player,"boss");
	  break;
	case 5:
	  if(gokuCall > 0){
	    my_putstr("Son Goku arrives.\n");
	    my_putstr("Ka-me-ha-me-ha! Your Enemy loses 10000 points.\n");
	    callSonGoku((void*)boss,"boss");
	    gokuCall--;
	  }else{
	    my_putstr("No more calls left.\n");
	  }
	  break;
	 case 6:
	    if(quizz == 1){
	      quizz--;
	      generate_quiz(10,player);
	      my_putstr("Shield Activated during the quizzzzzz.\n");
	      defend((void*)boss,player,"boss");
	    }else{
	      my_putstr("You already have done the quizz.\n");
	    }
	    break;
	  }
	  if(boss->hp > 0)
	    enemyAttack((void *)boss, player, "boss");
      }while(player->hp > 0 && boss->hp > 0 && game == 2);
    }
    
    if(game == 1){
      //do nothing
      }else if(player->hp <= 0){
	my_putstr("Looooser!\n");
	freeEnemies(tabEnemies);
	freeBoss(tabBoss);
	freePlayers(tabPlayer);
	break;
      }else if(boss->hp <= 0){
	my_putstr("You Win!\n");
	freePlayers(tabPlayer);
	freeEnemies(tabEnemies);
	freeBoss(tabBoss);
	break;
      }else{
	my_putstr("error\n");
	freePlayers(tabPlayer);
	freeEnemies(tabEnemies);
	freeBoss(tabBoss);
	break;
      }
    
    
  }
  
}
