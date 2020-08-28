package com.bbtutorials.users.entity;

import java.util.Date;
import java.util.List;

public class Partida {
    Mapa mapa;
    String provincia; //TODO: averiguar de que tipo es esto
    Date fecha;
    List<Jugador> jugadores;
    EstadoPartida estado;
}
