/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   render_rays.c                                      :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: cvernius <cvernius@student.42.fr>          +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2020/02/19 17:59:21 by cvernius          #+#    #+#             */
/*   Updated: 2020/03/06 22:31:33 by cvernius         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

#include "wolf3d.h"

int		check_overflow_map(t_wolf *w, t_vec2 len)
{
	if (!w->space_was_pressed && len.x < w->map.w
								&& len.y < w->map.h
								&& w->player.transform.x < WIN_W / 2
								&& w->player.transform.y < WIN_H
								&& w->player.transform.x >= 0
								&& w->player.transform.y >= 0)
		return (1);
	else
		return (0);
}

void	draw_rays(t_wolf *w, int pix)
{
	float	t;
	t_vec2	column_angle;
	t_vec2	len;

	t = 0.0f;
	column_angle.x = w->player.look_column_angle.x - FOV / 2 + FOV * pix /
														(float)(WIN_W / 2);
	column_angle.y = w->player.look_column_angle.y - FOV / 2 + FOV * pix /
															(float)(WIN_H / 2);
	while (t < 100)
	{
		len.x = cos(column_angle.x) * t + w->player.pos.x;
		len.y = sin(column_angle.x) * t + w->player.pos.y;
		w->player.transform.x = len.x * rect_w(w->map.w);
		w->player.transform.y = len.y * rect_h(w->map.h);
		if (check_overflow_map(w, len))
			w->layers->d_player.img[w->player.transform.x +
									w->player.transform.y * WIN_W] =
									get_color((t_color){255, 255, 255});
		if (w->map.line[(int)len.x + (int)len.y * w->map.w] != ' ')
			break ;
		t += 0.1;
	}
}

void	render_rays(t_wolf *w)
{
	int current_pix;

	current_pix = 0;
	while (current_pix < WIN_W / 2)
	{
		draw_rays(w, current_pix);
		current_pix++;
	}
}
