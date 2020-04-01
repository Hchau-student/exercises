/*
**	Эта программа отрисовывает фрейм; при первом запуске
** 	должны быть заданы любые аргументы, иначе поведение
**	непредсказуемо
*/

#include "draw_control.h"

void		get_terminal_size(int *w, int *h)
{
	struct winsize	ws;
	int				num_col;

	ioctl(STDOUT_FILENO, TIOCGWINSZ, &ws);
	*w = ws.ws_col;
	*h = ws.ws_row;
}

void		find_centre(int w, int h)
{
	while (h / 2 - 5 > 0)
	{
		write (1, "\n", 1);
		h--;
	}
}

void		get_next_start(int w, int h)
{
	while (h / 2 - 9 > 0)
	{
		write (1, "\n", 1);
		h--;
	}
}

int		main(int ac, char **av)
{
	int				term_w;
	int				term_h;
	int				i;

	if (ac < 1)
		return (0);
	g_count = 0;
	while (g_count <= MAX_FRAME)
	{
		i = 0;
		get_terminal_size(&term_w, &term_h);
		find_centre(term_w, term_h);
		draw_trace(term_w, term_h);
		get_next_start(term_w, term_h);
		while (i < 20000000)
			i++;
		i = 0;
		find_centre(term_w, term_h);
		draw_trace(term_w, term_h);
		get_next_start(term_w, term_h);
		while (i < 20000000)
			i++;
		g_count++;
	}
}