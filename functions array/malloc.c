#include "header.h"

void    this_malloc(char *av, void(***res)(char))
{
    int     i = 0;
    int     count_d = 0;
    int     count_ch = 0;

    while (av[i])
    {
        if (av[i] >= '0' && av[i] <= '9')
            count_d++;
        else
            count_ch++;
        i++;
    }
    (*res) = (void (**)(char))malloc(sizeof(void (*)(char)) * (i + 1)); //маллочу массив указателей
    (*res)[i] = NULL;
    i--;
    while (i >= 0)
    {
        if (av[i] >= '0' && av[i] <= '9')
        {
            count_ch--;
            (*res)[i] = (*digit);//привязываю функцию диджит
        }
        else
        {
            count_ch--;
            (*res)[i] = (*alpha);
        }
        i--;
    }
}