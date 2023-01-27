//
// Created by timsit_e on 10/28/2022.
//

#include <stdio.h>
#include "Players.h"
#include "rpg.h"
#include "quiz.h"
#include "displayFunctions.h"
#include "get_line.h"
#include "string.h"

/*************************************************************************************************************************/
char *Question[] = {
		     "Bill Gates is the founder of Amazon?",
		     "Kelvin is a measure of temperature?",
		     "Conductors have low resistance?",
		     "Yellow and blue are complementary colours?",
		     "In school, Albert Einstein failed most of the subjects, except for physics and math?",
		     "Tea contains more caffeine than coffee and soda?",
		     "The first product with a bar code was chewing gum?",
		     "The star is the most common symbol used in all national flags around the world?",
		     "Issac New is the scientist who developed the theory of relativity?",
		     "The first song ever sung in the space was “Happy Birthday?",


};

char *Answer[] = {
		  "no",
		  "yes",
		  "yes",
		  "yes",
		  "yes",
		  "no",
		  "yes",
		  "yes",
		  "no",
		          "yes"

};

void generate_quiz(int f,Player_t* player) {
    Quiz_t **quizes = malloc(10 * sizeof(Quiz_t *));
    for (int i = 0; i < 10; i++) {
        quizes[i] = (Quiz_t *) malloc(sizeof(Quiz_t));
        quizes[i]->question = Question[i];
        quizes[i]->answer = Answer[i];
    }
    my_putstr(
            "Here is a quiz for you. If you answer it correctly you will have *2 on strength and health!\n be careful you have only one try so make sure to answer exactly with yes or no...\n");
    my_putstr(quizes[f-1]->question);
    char *input = my_readline();
    if (strcmp(input,quizes[f-1]->answer)==0) {
      player->str = player->str * 2;
      player->hp = player->hp * 2;
        my_putstr("Good job! you've got a *");
	my_putnbr(2);
	my_putstr(" on strength and health.\n");
    } else {
        my_putstr("LOSER\n");
    }
    free(input);
    for(int i=0; i <10; i++)
      free(quizes[i]);
    free(quizes);
}


