//
// Created by irka on 31.03.2020.
//

#ifndef DRAW_CONTROL_H
#define DRAW_CONTROL_H
# include <sys/ioctl.h>
# include <unistd.h>
# include <stdio.h>
int				g_count;

#define TRACE_SIZE		6
#define MAX_FRAME		18
#define RED				"\033[38;5;196;1m"
#define ORANGE			"\033[38;5;208;1m"
#define YELLOW			"\033[38;5;226;1m"
#define GREEN			"\033[38;5;76;1m"
#define BLUE			"\033[38;5;39;1m"
#define PURPLE			"\033[38;5;125;1m"
#define EOC				"\033[0m"
void	draw_trace(int w, int h);

#endif //DRAW_DRAW_CONTROL_H
