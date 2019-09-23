FILES = main.c functions.c malloc.c

OBJ = main.o functions.o malloc.o

FLAGS = -Wall -Werror -Wextra

INCLUDES = header.h

NAME = array_of_pointers_to_functions

$(NAME):
		gcc -c $(FLAGS) -I $(INCLUDES) $(FILES)
		gcc $(OBJ) -o $(NAME)
		rm -rf header.h.gch

all: $(NAME)

clean:
		/bin/rm -Rf $(OBJ)

fclean:	clean
		/bin/rm -Rf $(NAME)

re:	fclean all
