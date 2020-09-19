package tacs.wololo;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import tacs.wololo.model.DTOs.GameInfoDto;
import tacs.wololo.services.implementations.GameService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameServiceTests
{
    @Autowired
    GameService gameService;

    GameInfoDto gameInfoDto;

    List<String> provinces = Arrays.asList(
            "Buenos Aires", "Catamarca", "Chaco", "Chubut", "Córdoba", "Corrientes", "Formosa"
            , "Jujuy", "La Pampa", "La Rioja", "Mendoza", "Misiones", "Neuquén", "Río Negro", "Salta", "San Juan"
            , "San Luis", "Santa Fe", "Tierra del Fuego, Antártida e Isla del Atlántico Sur", "Tucumán");

    List<String> usernames = Arrays.asList("usuarioTest", "usuarioTest2", "usuarioTest3", "usuarioTest4");

    Random rand = new Random();
}
