import React, { useEffect, useState } from "react";
import './Styles.scss'
import {
  createNewGame,
  getGame, getStatistics,
  playRound,
  resetGame
} from "../../lib/ApiClient";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import GlobalStatisticsTab from "./GlobalStatisticsTab";
import Container from "react-bootstrap/Container";
import GameTab from "./GameTab";

const GameContainer = () => {

  const[gameInfo, setGameInfo] = useState({
    rounds: []
  })

  const [globalStatistics, setGlobalStatistics] = useState({
    'totalRounds': 0,
    'player1Wins': 0,
    'player2Wins': 0,
    'draws': 0,
  })

  const gameLocalData = () => {
    return JSON.parse(localStorage.getItem("gameData"))
  }

  const saveLocalData = (data) => {
    const localData = {
      ...gameLocalData(),
      ...data
    }
    localStorage.setItem("gameData", JSON.stringify(localData));
  }

  const createGame = () => {
    createNewGame().then(
        gameResponse => {
          saveLocalData(
              {
                'gameId': gameResponse.id,
                'shape': 'ROCK'
              }
          );
          setGameInfo(gameResponse)
        }
    );
  }

  const retrieveGameOrCreateIfMissing = (gameId) => {
    getGame(gameId)
    .then(gameResponse => setGameInfo(gameResponse))
    .catch((err) => {
        if(err.response.status === 404) {
          createGame()
        }
      }
    );
  }

  const selectShapeFunc = (shape) => {
    saveLocalData({
      'shape': shape
    });
  }

  const newRoundFunc = () => {
    const localData = gameLocalData()
    playRound(localData.gameId, localData.shape)
      .then(gameResponse => setGameInfo(gameResponse))
  }

  const resetGameFunc = () => {
    const localData = gameLocalData()
    resetGame(localData.gameId)
      .then(gameResponse => setGameInfo(gameResponse))
  }

  const updateGlobalStatistics = (eventKey) => {
    if(eventKey === 'statistics') {
      getStatistics().then(response => setGlobalStatistics(response))
    }
  }

  useEffect(() => {
    const localData = gameLocalData()
    if(!localData || !localData.gameId) {
      createGame()
    } else {
      retrieveGameOrCreateIfMissing(localData.gameId)
    }
    updateGlobalStatistics()
  }, []);


  return (
      <Container className="p-3">
        <div className="container my-4">
          <h1> Rock Paper Scissor Game</h1>
          <div
              className="row p-4 pb-0 pe-lg-0 pt-lg-5 align-items-center rounded-3 border shadow-lg">
            <div className="col-lg p-3 p-lg-5 pt-lg-3">
              <Tabs defaultActiveKey="game" className="mb-3" onSelect={(eventKey) => updateGlobalStatistics(eventKey) }>
                <Tab eventKey="game" title="Game">

                  <GameTab
                      selectShapeFunc={ selectShapeFunc }
                      newRoundFunc={ newRoundFunc }
                      resetGameFunc={ resetGameFunc }
                      rounds={ gameInfo.rounds }
                  />
                </Tab>

                <Tab eventKey="statistics" title="Global Statistics">
                  <GlobalStatisticsTab
                      globalStatistics={ globalStatistics }
                  />
                </Tab>
              </Tabs>
            </div>
          </div>
        </div>
      </Container>
  );
}

export default GameContainer;