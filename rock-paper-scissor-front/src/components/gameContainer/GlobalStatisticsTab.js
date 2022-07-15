import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

const GlobalStatisticsTab = ({
  globalStatistics
}) => {

  ChartJS.register(ArcElement, Tooltip, Legend);

  const data = {
    labels: ['Player1(Humans) wins', 'Player2(CPU) wins', 'Draws'],
    datasets: [
      {
        label: '# of wins',
        data: [globalStatistics.player1Wins, globalStatistics.player2Wins, globalStatistics.draws],
        backgroundColor: [
          'rgba(89,130,225,0.76)',
          'rgba(29,126,50,0.88)',
          'rgba(98,93,88,0.2)'
        ],
        borderWidth: 1,
      },
    ],
  };
  return (
      <div className="row global-statistics" >
        <div className="col-6">
          <h5>Total Rounds: {globalStatistics.totalRounds}</h5>
          <h5>Player1 wins: {globalStatistics.player1Wins}</h5>
          <h5>Player2 wins: {globalStatistics.player2Wins}</h5>
          <h5>Draws: {globalStatistics.draws}</h5>
        </div>
        <div className="col-6">
          <Doughnut data={data} />
        </div>
      </div>
  )
}

export default GlobalStatisticsTab;