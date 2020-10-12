package tacs.wololo;

public class GameRepositoryTests {

    /*
    @Autowired
    private GameRepository gameRepository;
    private Game funnyGame;
    private Game boringGame;
    private Game mehGame;
    private Game finishedGame;

    @Before
    public void init() throws ParseException {

        Queue<String> playersFunnyGame =new LinkedList<String>();
        Queue<String> playersBoringGame =new LinkedList<String>();
        Queue<String> playersMehGame =new LinkedList<String>();

        playersFunnyGame.add("Lucia"); playersFunnyGame.add("Carolina");
        playersFunnyGame.add("Cristian"); playersFunnyGame.add("Sebastian");

        playersBoringGame.add("Lucia"); playersBoringGame.add("Cristian");

        playersMehGame.add("Lucia"); playersMehGame.add("Carolina");
        playersMehGame.add("Sebastian");

        funnyGame = new Game();
        boringGame = new Game();
        mehGame = new Game();
        finishedGame = new Game();

        funnyGame.setPlayers(playersFunnyGame);
        boringGame.setPlayers(playersBoringGame);
        mehGame.setPlayers(playersMehGame);
        finishedGame.setPlayers(playersBoringGame);

        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");

        Date funnyGameDate = objSDF.parse("2-09-2020");
        Date boringGameDate = objSDF.parse("12-09-2020");
        Date mehGameDate = objSDF.parse("15-09-2020");


        gameRepository = new GameRepository();
        gameRepository.addGame((long) 1, funnyGame);
        gameRepository.addGame((long)2, boringGame);
        gameRepository.addGame((long)3, mehGame);
        gameRepository.addGame((long)4, finishedGame);
    }

    @Test
    public void getGamesUserName()
    {
        List<Game> gamesBelongsToSebastian = new ArrayList<Game>();
        gamesBelongsToSebastian.add(funnyGame);
        gamesBelongsToSebastian.add(mehGame);

        Assert.assertEquals(gamesBelongsToSebastian, gameRepository.getGamesFor("Sebastian"));
    }

    // Proveer estadísticas de cantidad de partidas creadas, en curso, terminadas y canceladas
    // permitiendo seleccionar el rango de fechas
    @Test
    public void getGamesByStateInProgress() throws ParseException {
        List<Game> gamesBelongsToDates = new ArrayList<Game>();
        gamesBelongsToDates.add(boringGame);
        gamesBelongsToDates.add(mehGame);

        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");

        Date minDate = objSDF.parse("10-09-2020");
        Date maxDate = objSDF.parse("20-09-2020");

        Assert.assertEquals(gamesBelongsToDates,
                gameRepository.getGamesByDates(minDate, maxDate));

    }
    */
}
