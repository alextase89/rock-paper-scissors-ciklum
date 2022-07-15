import axios from 'axios';

const host = process.env.REACT_APP_API_ENDPOINT || 'localhost'
const port = process.env.REACT_APP_API_PORT || 8080
const baseURL = `http://${host}:${port}`;

export const createNewGame = async () => {
    const serviceResponse = await doPost("/v1/games");
    return serviceResponse.data;
}

export const getGame = async (gameId) => {
  const serviceResponse = await doGet(
      `/v1/games/${gameId}`);
  return serviceResponse.data;
}

export const playRound = async (gameId, shape) => {
  const serviceResponse = await doPost(
      `/v1/games/${gameId}/rounds`,
      {
        'player1Shape': shape
      });
  return serviceResponse.data;
}

export const resetGame = async (gameId) => {
  const serviceResponse = await doPut(
      `/v1/games/${gameId}/reset`);
  return serviceResponse.data;
}

export const getStatistics = async () => {
  const serviceResponse = await doGet(
      `/v1/games/statistics`);
  return serviceResponse.data;
}

const doGet = async (servicePath) => {
  return axios.get(`${baseURL}${servicePath}`);
}

const doPost = async (servicePath, body = {}) => {
  return axios.post(`${baseURL}${servicePath}`, {...body});
}

const doPut = async (servicePath, body = {}) => {
  return axios.put(`${baseURL}${servicePath}`, {...body});
}
