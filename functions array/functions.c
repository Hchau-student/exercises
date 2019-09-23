#include "header.h"

void     digit(char a)
{
    write (1, &a, 1);
    write (1, "\n", 1);
    write (1, "I WROTE A DIGIT!\n", 17);
}

void    alpha(char a)
{
    write (1, &a, 1);
    write (1, "\n", 1);
}
