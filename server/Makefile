
SRC=$(wildcard *.c)
DEP=$(SRC:.c=.o)
FLAGS= -g -Wall -Werror -I.

ifeq ($(CROSS_COMPILE),)
	CC=gcc
else
	CC=$(addprefix $(CROSS_COMPILE), gcc)
endif


all: $(DEP)  aesdsocket 

%: %.o
	$(CC) $(FLAG) -o $@ $(DEP) -pthread -Wl,-Map=mapfile.map


%.o: %.c %.h
	$(CC) $(FLAG) -c $< -o $@ -pthread


clean:
	rm -rf *.o aesdsocket *.map

