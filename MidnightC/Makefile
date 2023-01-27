CC=gcc
CFLAGS=-Wall -Wextra -Werror #-fsanitize=address -g
OBJ=main.o my_enemies.o get_line.o boss_d.o displayFunctions.o player.o Players.o
CFILES=main.c my_enemies.c get_line.c boss_d.c displayFunctions.c player.c Players.c Enemy.c quiz.c

all: game
	./game

game:$(OBJ)
	$(CC) $(CFLAGS)  $(CFILES) -o game
clean:
	rm -f *.h~
	rm -f *.o
	rm -f *.c~
	rm -f Makefile~
	rm -f *.c#
	rm -f game

