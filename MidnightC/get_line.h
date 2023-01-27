#ifndef GET_LINE_H_
#define GET_LINE_H_

int gameover();
void callSonGoku(void* x,char* type);
void defend(void* x, Player_t* p,char* type);
void heal(int max_hp, Player_t* p);
void playerAttack(void* x, Player_t* p,char* type);
void enemyAttack(void* x, Player_t* p,char* type);
int interaction();
char *my_readline(void);

#endif /* !GET_LINE_H */
