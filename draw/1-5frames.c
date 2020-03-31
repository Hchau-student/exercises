//
// Created by irka on 31.03.2020.
//

#include "draw_control.h"
#include <string.h>

void	draw_rainbow(int w, int h, int space)
{
	if (6 - space + 6 > g_count)
	{
		write(1, "\n", 1);
		return ;
	}
	if (space < 2)
		space = 2;
	while (space < 5)
	{
		write (1, "  ", 2);
		space++;
	}
	write (1, RED"/"ORANGE"/"YELLOW"/"GREEN"/"BLUE"/"PURPLE"/"EOC,
			strlen(RED ORANGE YELLOW GREEN BLUE PURPLE EOC) + 6);
	write(1, "\n", 1);
}

void	write_rest_triangle(int w, int h, int flag)
{
	if (flag)
	{
		write(1, "      \\/      \\"YELLOW"/"GREEN"/"BLUE"/"PURPLE"/"EOC"\n",
				strlen(YELLOW GREEN BLUE PURPLE EOC) + 20);
		write(1, "      / DOING  \\"BLUE"/"PURPLE"/"EOC"\n", strlen(BLUE PURPLE EOC) + 19);
		write(1, "     /__________\\\n", 18);
	}
	else
	{
		write(1, "       /      \\\n", 16);
		write(1, "      / DOING  \\\n", 17);
		write(1, "     /__________\\\n", 18);
	}
}

void	draw_triangle(int w, int h, int space, int flag)
{
	int		right_space;

	right_space = space;
//	if (space < 3)
//	{
//		write(1, "\n", 1);
//		return ;
//	}
	while (space < 5)
	{
		write (1, "  ", 2);
		space++;
	}
	space = right_space;
	if (space >= 3)
	{
		write(1, "/", 1);
		while (right_space > 3) {
			write(1, "  ", 2);
			right_space--;
		}
		write(1, "\\", 1);
	}
	draw_rainbow(w, h, space);
}

void	draw_trace(int w, int h)
{
	int		leight;
	int		arr;
	int		flag;

	arr = 0;
	while (arr < TRACE_SIZE)
	{
		flag = 0;
		leight = 0;
		while (leight < arr)
		{
			leight++;
			write (1, " ", 2);
		}
		if (arr < g_count)
		{
			write (1, "\\\\\\", 3);
			flag = 1;
		}
		else
			write (1, "   ", 3);
		draw_triangle(w, h, arr, flag);//перенос строки делаю здесь
		arr++;
	}
	write_rest_triangle(w, h, flag);
}
