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
//1) нарисовать
//2) найти длину/ширину изображения
//3) использовать длину и ширину терминала

int		main(int ac, char **av)
{
	int				term_w;
	int				term_h;

	if (ac < 2)
		return (0);
	if (!(av[1][0]))
		return (0);
	g_count = av[1][0] - 'a';
	if (g_count > MAX_FRAME)
		return (0);
	get_terminal_size(&term_w, &term_h);
	draw_trace(term_w, term_h);
	g_count++;
	printf("%d", g_count);
}