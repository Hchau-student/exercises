#include "header.h"

/*
 *
 */

int     main(int ac, char **av)
{
    int     i = 0;
    void    (**res)(char);

    res = NULL;
    if (ac != 2)
    {
        write (1, "usage: ./array_of_pointers_to_functions [write a line]\n", 56);
    }
    if (ac == 2)
    {
        this_malloc((av[1]), &res);
            while (av[1][i])
            {
                res[i](av[1][i]);
                i++;
            }
    }
    i = 0;
    free(res);
    return (0);
}